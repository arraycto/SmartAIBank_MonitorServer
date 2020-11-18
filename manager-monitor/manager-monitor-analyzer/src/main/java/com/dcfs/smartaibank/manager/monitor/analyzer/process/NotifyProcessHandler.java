package com.dcfs.smartaibank.manager.monitor.analyzer.process;

import com.dcfs.smartaibank.handler.exception.HandlerException;
import com.dcfs.smartaibank.handler.Status;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.AbstractMonitorHandler;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.ProcessHandler;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.exception.NotifyException;
import com.dcfs.smartaibank.manager.monitor.notify.NotifyManager;
import org.springframework.util.Assert;

/**
 * 通知处理器
 * @author jiazw
 */
public class NotifyProcessHandler extends AbstractMonitorHandler implements ProcessHandler, Constants {

	private NotifyManager notifyManager;

	@Override
	protected Status doHandle(MonitorContext context) throws HandlerException {
		try {
			notifyManager.notify(context);
		} catch (NotifyException e) {
			throw new HandlerException("handler.error", e);
		}
		return Status.CONTINUE;
	}

	public void setNotifyManager(NotifyManager notifyManager) {
		this.notifyManager = notifyManager;
	}

	@Override
	protected void doStart() {
		Assert.notNull(this.notifyManager, "notifyManager must be not null");
	}
}
