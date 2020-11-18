package com.dcfs.smartaibank.manager.monitor.analyzer.prepare;

import com.dcfs.smartaibank.handler.exception.HandlerException;
import com.dcfs.smartaibank.handler.Status;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.AbstractMonitorHandler;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.PrepareHandler;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.rule.RuleCenterAccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

/**
 * 添加机具管理员属性的预处理器
 * Key:MANAGER_ID
 *
 * @author jiazw
 */
@Slf4j
public class ManagerPrepareHandler extends AbstractMonitorHandler implements PrepareHandler, Constants {

	private RuleCenterAccess ruleCenter;

	@Override
	protected Status doHandle(MonitorContext context) throws HandlerException {
		String mid = context.getBody(Constants.MID);
		String managerid = ruleCenter.getDeviceManager(mid);
		if (managerid != null) {
			//机具管理员
			context.put("MANAGER_ID", managerid);
			if (log.isDebugEnabled()) {
				log.debug("[{}]预处理器新增字段,MANAGER_ID={}]", managerid);
			}
		}
		return Status.CONTINUE;
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
	protected void doStart() {
		Assert.notNull(this.ruleCenter, "ruleCenter must be not null");
	}
}
