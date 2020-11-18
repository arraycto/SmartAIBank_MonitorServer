package com.dcfs.smartaibank.manager.monitor.analyzer.process;

import com.dcfs.smartaibank.handler.exception.HandlerException;
import com.dcfs.smartaibank.handler.Status;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.AbstractMonitorHandler;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.ProcessHandler;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.executor.ExecuteDefine;
import com.dcfs.smartaibank.manager.monitor.core.executor.ExecutorManager;
import com.dcfs.smartaibank.manager.monitor.rule.domain.AlarmLevel;
import com.dcfs.smartaibank.manager.monitor.rule.domain.AlarmRule;
import com.dcfs.smartaibank.springboot.core.base.CodeDescEnumHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认预警处理器
 *
 * @author jiazw
 */
@Slf4j
public class DefaultAlarmProcessHandler extends AbstractMonitorHandler implements ProcessHandler, Constants {
	private ExecutorManager manager;

	@SuppressWarnings("unchecked")
	@Override
	protected Status doHandle(MonitorContext context) throws HandlerException {
		Boolean analyzerResult = context.get(ANALYZER_RESULT);
		if (analyzerResult != null && analyzerResult) {
			List<AlarmRule> rules = context.get(CURRENT_ALARM_RULES);
			List<Map<String, Object>> alarms = new ArrayList<>();
			if (rules != null) {
				for (AlarmRule rule : rules) {
					fireRule(context, rule, alarms);
				}
			}
			context.put(ALARM_LEVEL_LIST, alarms);
		}
		return Status.CONTINUE;
	}

	private void fireRule(MonitorContext context, AlarmRule rule, List<Map<String, Object>> alarms) {
		if (rule != null && rule.isActive()) {
			ExecuteDefine target = rule.getExecutor();
			if (target != null) {
				//设置当期的预警规则
				context.put(CURRENT_ALARM_RULE, rule);
				try {
					//调用规则中的执行器执行并返回结果
					Object result = manager.execute(context, rule.getExecutor());
					if (result != null) {
						if (log.isDebugEnabled()) {
							log.debug("[{}]默认预警处理器[ID={}，DESC={}]执行完成",
								context.getUUID(), rule.getId(), rule.getDescription(), result.toString());
						}

						if (result instanceof List) {
							alarms.addAll((List<Map<String, Object>>) result);
						} else if (result instanceof Map) {
							alarms.add((Map<String, Object>) result);
						} else if (result instanceof Boolean && (Boolean) result) {
							Map<String, Object> map = new HashMap<>(16);
							map.put(Constants.ALARM_LEVEL, rule.getLevel());
							map.put(Constants.ALARM_DESC, rule.getDescription());
							map.put(Constants.ALARM_RULE_ID, rule.getId());
							alarms.add(map);
						} else if (result instanceof Integer && (Integer) result > 0) {
							Map<String, Object> map = new HashMap<>(16);
							map.put(Constants.ALARM_LEVEL, CodeDescEnumHelper.getEnum(AlarmLevel.class, result));
							map.put(Constants.ALARM_DESC, rule.getDescription());
							map.put(Constants.ALARM_RULE_ID, rule.getId());
							alarms.add(map);
						}
					}
				} catch (Exception e) {
					throw new HandlerException("handler.error", e);
				}
			}
		}
	}

	public void setExecutorManager(ExecutorManager manager) {
		this.manager = manager;
	}

	@Override
	protected void doStart() {
		Assert.notNull(this.manager, "manager must be not null");
	}
}
