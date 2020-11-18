package com.dcfs.smartaibank.manager.monitor.analyzer.analyzer;

import com.dcfs.smartaibank.handler.exception.HandlerException;
import com.dcfs.smartaibank.handler.Status;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.AbstractMonitorHandler;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.AnalyzerHandler;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.executor.ExecuteDefine;
import com.dcfs.smartaibank.manager.monitor.core.executor.ExecutorManager;
import com.dcfs.smartaibank.manager.monitor.rule.domain.Rule;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认分析器
 *
 * @author jiazw
 */
@Slf4j
public class DefaultAnalyzerHandler extends AbstractMonitorHandler implements AnalyzerHandler, Constants {
	private ExecutorManager manager;

	@Override
	protected Status doHandle(MonitorContext context) throws HandlerException {
		String uuid = context.get("UUID");
		//获取当前规则
		Rule rule = context.get(CURRENT_ANALYZER_RULE);
		if (rule != null) {
			if (rule.isActive()) {
				boolean result;
				ExecuteDefine target = rule.getExecutor();
				if (target != null && target.getCompiledExecutor() != null) {
					try {
						result = manager.execute(context, target);
						if (log.isDebugEnabled()) {
							log.debug("[{}]分析器[ID={}，DESC={}]执行结果为：{}",
								uuid, rule.getId(), rule.getDescription(), result);
						}
						String key = target.getKey();
						if (key != null && !"".equals(key)) {
							context.put(key, result);
							log.debug("[{}]分析器[ID={}，DESC={}]新增字段[{}={}]",
								uuid, rule.getId(), rule.getDescription(), key, result);
						}
					} catch (Exception e) {
						throw new HandlerException("handler.error", e);
					}
				} else {
					if (log.isDebugEnabled()) {
						log.debug("[{}]分析器[ID={}，DESC={}]默认结果为：true", uuid, rule.getId(), rule.getDescription());
					}
				}
			}
		}
		return Status.CONTINUE;
	}

	/**
	 * 设置执行器管理器
	 */
	public void setExecutorManager(ExecutorManager manager) {
		this.manager = manager;
	}
}
