package com.dcfs.smartaibank.manager.monitor.notify.handler;

import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.exception.NotifyException;
import com.dcfs.smartaibank.manager.monitor.rule.domain.NotifyRule;

/**
 * 通知执行器
 *
 * @author jiazw
 */
public interface NotifyHandler {
	/**
	 * 通知
	 *
	 * @param context 上下文
	 * @param rule    通知规则
	 * @throws NotifyException 通知异常
	 */
	void handle(MonitorContext context, NotifyRule rule) throws NotifyException;
}
