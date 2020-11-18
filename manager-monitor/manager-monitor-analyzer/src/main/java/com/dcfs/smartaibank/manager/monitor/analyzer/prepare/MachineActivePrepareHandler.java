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
 * 添加机具是否监控属性的预处理器
 * Key:IS_MACTIVE
 *
 * @author jiazw
 */
@Slf4j
public class MachineActivePrepareHandler extends AbstractMonitorHandler implements PrepareHandler, Constants {

	private RuleCenterAccess ruleCenter;

	@Override
	protected Status doHandle(MonitorContext context) throws HandlerException {
		String mClassKey = context.getBody(Constants.MCLASSKEY);
		if (mClassKey != null && !"".equals(mClassKey)) {
			// 设置机具是否监控
			boolean isActive = ruleCenter.isActiveForMClass(mClassKey);
			context.put("IS_MACTIVE", isActive);
			if (log.isDebugEnabled()) {
				log.debug("[{}]机具[{}]是否监控标示，IS_MACTIVE={}", context.getUUID(), mClassKey, isActive);
			}
		} else {
			log.debug("机具类型为空，无法判断机具是否被监控！");
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
