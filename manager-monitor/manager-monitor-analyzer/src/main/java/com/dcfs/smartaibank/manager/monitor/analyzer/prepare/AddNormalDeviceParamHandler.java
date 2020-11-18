package com.dcfs.smartaibank.manager.monitor.analyzer.prepare;

import com.dcfs.smartaibank.handler.context.Context;
import com.dcfs.smartaibank.handler.exception.HandlerException;
import com.dcfs.smartaibank.handler.Status;
import com.dcfs.smartaibank.manager.monitor.core.context.ContextHelper;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.AbstractMonitorHandler;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.PrepareHandler;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.rule.RuleCenterAccess;
import com.dcfs.smartaibank.manager.monitor.rule.domain.DeviceModelCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

/**
 * 当TYPE=DEVICE时添加外设名称和外设状态
 *
 * @author jiazw
 */
@Slf4j
public class AddNormalDeviceParamHandler extends AbstractMonitorHandler implements PrepareHandler, Constants {

	private RuleCenterAccess ruleCenter;

	@Override
	protected Status doHandle(MonitorContext context) throws HandlerException {
		//是否为外设监控数据
		boolean cond = ContextHelper.isSameType(context, TYPE_DEVICE);

		if (cond) {
			String devModelKey = context.getBody(Constants.DEVMODELKEY);
			String statusCode = context.getBody(Constants.STATUS_CODE);
			DeviceModelCode codeObj = ruleCenter.getDeviceModelCode(devModelKey, statusCode);
			if (codeObj != null && codeObj.isActive()) {
				int status = codeObj.getStatus().getCode();
				String desc = codeObj.getStatusDesc();
				int mStatus = codeObj.getMStatus().getCode();
				int alarmLevel = codeObj.getAlarmLevel().getCode();

				//外设状态
				context.put(Constants.STATUS, status);
				//外设状态描述
				context.put(Constants.STATUS_DESC, desc);
				//对应机具状态
				context.put(Constants.M_STATUS, mStatus);
				//预警等级
				context.put(Constants.ALARM_LEVEL, alarmLevel);

				if (log.isDebugEnabled()) {
					log.debug("[{}]预处理器新增字段：DEVICE_STATUS={},DEVICE_STATUS_DESC={},M_STATUS={},ALARM_LEVEL={}",
						context.getUUID(), status, desc, status, alarmLevel);
				}
			} else {
				setDefaultData(context);
				if (log.isDebugEnabled()) {
					log.debug("[{}]预处理器新增字段,无法找到该外设[DEVMODELKEY={},STATUS_CODE={}]，设置默认值。DEVICE_STATUS=1",
						context.getUUID(), devModelKey, statusCode);
				}
			}
		}


		return Status.CONTINUE;
	}

	private void setDefaultData(Context context) {
		//默认外设状态为正常
		context.put(Constants.STATUS, 1);
		//默认外设状态描述为空
		context.put(Constants.STATUS_DESC, "正常");
		//默认机具状态为正常
		context.put(Constants.M_STATUS, 1);
		//默认不预警
		context.put(Constants.ALARM_LEVEL, 0);
	}

	public void setRuleCenter(RuleCenterAccess ruleCenter) {
		this.ruleCenter = ruleCenter;
	}

	@Override
	protected void doStart() {
		Assert.notNull(this.ruleCenter, "ruleCenter must be not null");
	}
}
