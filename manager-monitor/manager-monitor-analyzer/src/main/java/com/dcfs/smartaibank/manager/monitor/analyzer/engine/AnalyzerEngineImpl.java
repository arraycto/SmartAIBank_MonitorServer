package com.dcfs.smartaibank.manager.monitor.analyzer.engine;

import com.dcfs.smartaibank.core.factory.DefaultThreadFactory;
import com.dcfs.smartaibank.handler.context.Context;
import com.dcfs.smartaibank.handler.exception.HandlerException;
import com.dcfs.smartaibank.handler.Handler;
import com.dcfs.smartaibank.handler.HandlerWrapper;
import com.dcfs.smartaibank.handler.Status;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.AnalyzerEngine;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.rule.RuleCenterAccess;
import com.dcfs.smartaibank.manager.monitor.rule.domain.MonitorItem;
import com.dcfs.smartaibank.manager.monitor.rule.domain.RealRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.TimedRule;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 分析引擎默认实现
 *
 * @author jiazw
 */
@Slf4j
public class AnalyzerEngineImpl extends HandlerWrapper implements AnalyzerEngine, Constants {

	private static final String THREAD_POOL_GROUP_NAME = "engine";
	/**
	 * 线程池
	 */
	private ExecutorService threadPool;

	/**
	 * 规则中心
	 */
	private RuleCenterAccess ruleCenter;

	/**
	 * 过滤和预处理Handler
	 */
	private Handler filterAndPrepareHandler;


	/**
	 * 初始化线程池
	 *
	 * @param corePoolSize    核心线程数
	 * @param maximumPoolSize 最大线程数
	 * @param keepAliveTime   线程存活时间
	 * @param queueSize       队列大小
	 */
	public void initThreadPool(int corePoolSize, int maximumPoolSize,
							   long keepAliveTime, int queueSize) {
		int innerQueueSize = queueSize;
		//如果为-1表示无界，取值Integer.MAX_VALUE
		if (queueSize == -1) {
			innerQueueSize = Integer.MAX_VALUE;
		}
		threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
			new LinkedBlockingQueue<>(innerQueueSize), new DefaultThreadFactory(THREAD_POOL_GROUP_NAME));
	}

	/**
	 * 设置规则中心
	 *
	 * @param ruleCenter 规则中心
	 */
	public void setRuleCenter(RuleCenterAccess ruleCenter) {
		this.ruleCenter = ruleCenter;
	}

	/**
	 * 设置过滤和预处理器
	 *
	 * @param handler 过滤和预处理器
	 */
	public void setFilterAndPrepareHandler(Handler handler) {
		this.filterAndPrepareHandler = handler;
	}

	@Override
	protected void doStart() throws Exception {
		filterAndPrepareHandler.start();
		super.doStart();
	}

	@Override
	protected void doStop() throws Exception {
		filterAndPrepareHandler.stop();
		super.doStop();
	}

	@Override
	protected Status doHandle(Context context) throws HandlerException {
		MonitorContext mc = (MonitorContext) context;
		String uuid = getUUID();
		//统一添加UUID标识，用于在多线程下日志输出
		mc.put("UUID", uuid);

		//统一添加接收时间
		mc.put(Constants.RECEIVE_TIME, new Date());

		if (log.isDebugEnabled()) {
			log.debug(context.toString());
		}

		//生成任务实例并加入到线程池中执行
		threadPool.execute(new AnalyzerTask(mc));

		return Status.CONTINUE;
	}

	/**
	 * 分析任务线程
	 *
	 * @author jiazw
	 */
	private class AnalyzerTask implements Runnable {
		private MonitorContext context;

		AnalyzerTask(MonitorContext context) {
			this.context = context;
		}

		@Override
		public void run() {
			if (getHandler() != null) {
				long start = System.currentTimeMillis();
				String system = context.getHeader(SYSTEM);
				String type = context.getHeader(TYPE);
				String mode = context.get(MODE);

				// STEP1.设置信息--设置过滤规则集
				context.put(CURRENT_FILTER_RULES, ruleCenter.getFilterRules());
				context.put(CURRENT_PREPARE_RULES, ruleCenter.getPrepareRules(system, type));
				// 设置参数信息
				context.put(PARAMS, ruleCenter.getParamCodes());
				context.put(DICTS, ruleCenter.getDictCodes());

				if (context.getBody(Constants.MID) != null) {
					Map<String, String> deviceParams =
						ruleCenter.getDeviceParams(context.getBody(Constants.MID).toString());
					if (deviceParams == null) {
						deviceParams = ruleCenter.getDeviceParams("default");
					}
					//机具参数
					context.put("DEVICEPARAMS", deviceParams);
					if (log.isDebugEnabled()) {
						log.debug("[预处理器新增字段,DEVICEPARAMS={}]", deviceParams);
						log.debug("[预处理器,context={}]", context);
					}
				}

				// STEP2. 进行过滤和预处理
				Status status = executeFilterAndPrepare();

				// STEP3.判断返回值
				if (status == Status.CONTINUE) {
					if (MODE_REAL.equals(mode)) {
						executeRealRule(system, type);
					} else if (MODE_TIMED.equals(mode)) {
						executeTimedRule();
					} else {
						log.error("[{}]no supported mode->{}", context.get("UUID"), mode);
					}
				}

				long end = System.currentTimeMillis();
				log.info("[{}]analyzerEngine execute ：{} ms,system={},type={}",
					context.get("UUID"), (end - start), system, type);
			} else {
				log.error("[{}]规则引擎没有配置执行器！", (String) context.get("UUID"));
			}
		}

		private Status executeFilterAndPrepare() {
			Status status = Status.BREAK;
			try {
				status = filterAndPrepareHandler.handle(context);
			} catch (HandlerException e) {
				log.error("[" + context.get("UUID") + "]执行过滤和预处理时发生有异常！", e);
			}

			return status;
		}

		private void executeRealRule(String system, String type) {
			// 获取规则
			List<RealRule> realRules = ruleCenter.getRealRules(system, type);
			// 判断规则是否被激活
			for (RealRule realRule : realRules) {
				if (realRule.isActive()) {
					//设置当前分析规则
					context.put(CURRENT_ANALYZER_RULE, realRule);
					initContext(context, realRule.getMonitorItem());
					try {
						getHandler().handle(context);
					} catch (HandlerException e) {
						log.error("[" + context.get("UUID") + "]执行实时监控和预警分析时发生异常！", e);
					}
				}
			}
		}

		private void executeTimedRule() {
			TimedRule rule = context.get(CURRENT_ANALYZER_RULE);
			if (rule != null && rule.isActive()) {
				try {
					initContext(context, rule.getMonitorItem());
					getHandler().handle(context);
				} catch (HandlerException e) {
					log.error("[" + context.get("UUID") + "]执行定时监控和预警分析时发生异常！", e);
				}
			}
		}

		private void initContext(MonitorContext context, MonitorItem item) {
			//设置当前监控项
			context.put(CURRENT_MONITOR_ITEM, item);
			//设置预警规则集
			context.put(CURRENT_ALARM_RULES, ruleCenter.getAlarmRules(item.getId()));
			//设置通知规则集
			context.put(CURRENT_NOTIFY_RULES, ruleCenter.getNotifyRules(item.getId()));
		}
	}

	private String getUUID() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		return uuid;
	}
}
