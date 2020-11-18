package com.dcfs.smartaibank.manager.monitor.notify;

import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.exception.NotifyException;
import com.dcfs.smartaibank.manager.monitor.notify.handler.NotifyHandler;

/**
 * 通知管理器
 *
 * @author
 */
public interface NotifyManager {
	/**
	 * 通知
	 *
	 * @param context 上下文
	 * @throws NotifyException 通知异常
	 */
	void notify(MonitorContext context) throws NotifyException;

	/**
	 * 添加通知执行器
	 *
	 * @param name     执行器名称
	 * @param executor 执行器
	 */
	void addNotifyHandler(String name, NotifyHandler executor);
}
