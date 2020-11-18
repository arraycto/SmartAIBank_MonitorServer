package com.dcfs.smartaibank.manager.monitor.analyzer.executor;

import com.dcfs.smartaibank.manager.monitor.core.context.ContextHelper;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.executor.ExecuteDefine;
import com.dcfs.smartaibank.manager.monitor.core.executor.Executor;
import com.dcfs.smartaibank.manager.monitor.rule.domain.AlarmRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.TranCode;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 交易预警处理执行器
 *
 * @author jiazw
 */
@Component
public class TranAlarmProcessExecutor implements Executor, Constants {

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> execute(MonitorContext context, ExecuteDefine target) {
		// 是否为交易监控
		boolean cond = ContextHelper.isSameType(context, TYPE_TRAN);
		Map<String, Object> result = null;
		if (cond) {
			AlarmRule rule = context.get(CURRENT_ALARM_RULE);
			TranCode code = context.get(Constants.TRAN_CODE_PARAM);
			if (code != null && code.isActive()) {
				int alarmLevel = code.getAlarmLevel().getCode();
				//如果预警级别小于等于0表示不预警
				if (alarmLevel > 0) {
					result = new HashMap<>(16);
					result.put(Constants.ALARM_LEVEL, code.getAlarmLevel());
					result.put(Constants.ALARM_DESC, context.getBody("TRAN_RET_DESC"));
					result.put(Constants.ALARM_RULE_ID, rule.getId());
				}
			}
		}
		return result;
	}
}
