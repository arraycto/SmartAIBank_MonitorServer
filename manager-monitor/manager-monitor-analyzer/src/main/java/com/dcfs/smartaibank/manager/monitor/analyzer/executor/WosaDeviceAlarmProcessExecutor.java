package com.dcfs.smartaibank.manager.monitor.analyzer.executor;

import com.dcfs.smartaibank.manager.monitor.core.context.ContextHelper;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.executor.ExecuteDefine;
import com.dcfs.smartaibank.manager.monitor.core.executor.Executor;
import com.dcfs.smartaibank.manager.monitor.rule.domain.AlarmRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.DeviceModelCode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WOSA外设预警处理执行器
 *
 * @author jiazw
 */
@Component
public class WosaDeviceAlarmProcessExecutor implements Executor, Constants {

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> execute(MonitorContext context, ExecuteDefine target) {
		// 是否为外设监控数据
		boolean cond = ContextHelper.isSameType(context, TYPE_DEVICE);

		// 判断被监控外设是否符合WOSA标准（WOSA标准和非标准在数据格式上不同，需要区别对待）
		boolean isWosa = ContextHelper.isHaveAndEqualFromBody(context, "STANDARD", DEVICE_STANDARD_WOSA);

		if (cond && isWosa) {
			AlarmRule rule = context.get(CURRENT_ALARM_RULE);
			if (rule != null) {
				String ruleId = rule.getId();
				List<Map<String, Object>> list = new ArrayList<>();

				Map<String, DeviceModelCode> map = context.get(DEVICE_STATUS_LIST);
				for (DeviceModelCode code : map.values()) {
					String alarmRuleId = code.getAlarmRuleId();
					int alarmLevel = code.getAlarmLevel().getCode();
					//modify by zhaofy 2016-12-30
					int repairLevel = code.getRepairLevel();
					//modify by jiazw 2016-08-04
					//外设类的预警规则进行了拆分，所以外设参数配置中增加了预警规则ID字段，在进行规则预警判断时
					//还需要增加规则的匹配条件
					if (ruleId.equals(alarmRuleId) && alarmLevel > 0) {
						Map<String, Object> alarmMap = new HashMap<>(16);
						alarmMap.put(Constants.ALARM_LEVEL, code.getAlarmLevel());
						alarmMap.put("REPAIR_LEVEL", repairLevel);
						alarmMap.put(Constants.ALARM_RULE_ID, ruleId);
						alarmMap.put(Constants.ALARM_DESC, code.getStatusDesc());
						alarmMap.put(Constants.STATUS_CODE, code.getStatusCode());
						list.add(alarmMap);
					}
				}

				return list;
			}
		}
		return null;
	}
}
