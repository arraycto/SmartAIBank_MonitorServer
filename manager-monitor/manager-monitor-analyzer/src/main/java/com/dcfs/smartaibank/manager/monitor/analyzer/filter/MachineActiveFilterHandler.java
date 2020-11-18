package com.dcfs.smartaibank.manager.monitor.analyzer.filter;

import com.dcfs.smartaibank.handler.exception.HandlerException;
import com.dcfs.smartaibank.handler.Status;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.AbstractMonitorHandler;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.FilterHandler;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.rule.RuleCenterAccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

/**
 * 机具类型是否被监控（M_ACTIVE）
 *
 * @author jiazw
 */
@Slf4j
public class MachineActiveFilterHandler extends AbstractMonitorHandler implements
	FilterHandler, Constants {

	private RuleCenterAccess ruleCenter;

	@Override
	protected Status doHandle(MonitorContext context) throws HandlerException {
		String mClassKey = context.getMechineClassKey();
		// 设置机具是否监控
		boolean isActive = ruleCenter.isActiveForMClass(mClassKey);
		return isActive ? Status.CONTINUE : Status.BREAK;
	}

	/**
	 * 设置规则中心
	 *
	 * @param ruleCenter 规则中心
	 */
	public void setRuleCenter(RuleCenterAccess ruleCenter) {
		this.ruleCenter = ruleCenter;
	}

	@Override
	public void doStart() throws Exception {
		Assert.notNull(this.ruleCenter, "ruleCenter must be not null");
		super.doStart();
	}
}
