package com.dcfs.smartaibank.manager.monitor.analyzer.process;

import com.dcfs.smartaibank.handler.exception.HandlerException;
import com.dcfs.smartaibank.handler.Status;
import com.dcfs.smartaibank.manager.monitor.analyzer.service.Service;
import com.dcfs.smartaibank.manager.monitor.analyzer.service.ServiceManager;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.AbstractMonitorHandler;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.ProcessHandler;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * 判断预警结果并保存到Context中，保存预警信息
 *
 * @author jiazw
 */
@Slf4j
public class AlarmSaveProcessHandler extends AbstractMonitorHandler implements ProcessHandler, Constants {
	private ServiceManager serviceManager;

	@Override
	protected Status doHandle(MonitorContext context) throws HandlerException {
		String uuid = context.getUUID();
		String type = context.getType();
		Service service = serviceManager.getService(type);
		if (service != null) {
			List<Map<String, Object>> result = null;
			try {
				result = service.insertAlarm(context);
			} catch (Exception e) {
				if (log.isWarnEnabled()) {
					log.warn("[" + uuid + "]保存预警信息时发生异常，可能会发生漏报", e);
				}
				throw new HandlerException("handler.error", e);
			} finally {
				log.info("[{}]预警结果为{}", uuid, result);
				context.put(ALARM_RESULT_LIST, result);
				context.put(Constants.ALARM_RESULT, (result != null && !result.isEmpty()));
			}
		} else {
			log.error("[{}]无法获取指定类型的服务实例。TYPE:{}", uuid, type);
		}

		return Status.CONTINUE;
	}

	public void setServiceManager(ServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}

	@Override
	protected void doStart() {
		Assert.notNull(this.serviceManager, "ruleCenter must be not null");
	}
}
