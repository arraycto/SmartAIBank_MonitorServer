package com.dcfs.smartaibank.manager.monitor.analyzer.process;

import com.dcfs.smartaibank.handler.exception.HandlerException;
import com.dcfs.smartaibank.handler.Status;
import com.dcfs.smartaibank.manager.monitor.core.context.ContextHelper;
import com.dcfs.smartaibank.manager.monitor.analyzer.service.Service;
import com.dcfs.smartaibank.manager.monitor.analyzer.service.ServiceManager;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.AbstractMonitorHandler;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.ProcessHandler;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;


/**
 * 故障记录Handler，保存记录故障开始和结束时间
 *
 * @author ligg
 */
@Slf4j
public class FaultRecordSaveProcessHandler extends AbstractMonitorHandler implements ProcessHandler, Constants {
	private ServiceManager serviceManager;

	@Override
	protected Status doHandle(MonitorContext context) throws HandlerException {
		String uuid = context.getUUID();
		String type = context.getType();
		Service service = serviceManager.getService("FAULT");
		boolean cond = type.equals(ContextHelper.TYPE_APP)
			|| type.equals(ContextHelper.TYPE_DEVICE)
			|| type.equals(ContextHelper.TYPE_NETWORK);
		if (service != null) {
			// 故障记录保存失败不能影响其他正常流程
			if (cond) {
				try {
					service.insertOrUpdateInfo(context);
				} catch (Exception e) {
					log.warn("[" + uuid + "]保存故障信息时发生异常，可能会发生漏报", e);
				}
				log.info("[{}]保存故障信息成功", uuid);
			}
		} else {
			log.error("[{}]无法获取指定类型的服务实例", uuid);
		}

		return Status.CONTINUE;
	}

	public void setServiceManager(ServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}

	@Override
	protected void doStart() {
		Assert.notNull(this.serviceManager, "serviceManager must be not null");
	}
}
