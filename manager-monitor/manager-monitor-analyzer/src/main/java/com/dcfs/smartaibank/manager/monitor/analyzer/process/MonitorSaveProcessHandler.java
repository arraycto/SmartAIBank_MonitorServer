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
import com.dcfs.smartaibank.manager.monitor.rule.domain.Rule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.RuleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

/**
 * 保存监控数据
 *
 * @author jiazw
 */
@Slf4j
public class MonitorSaveProcessHandler extends AbstractMonitorHandler implements ProcessHandler, Constants {

	private ServiceManager serviceManager;

	@Override
	protected Status doHandle(MonitorContext context) throws HandlerException {
		String uuid = context.getUUID();
		Boolean analyzerResult = context.get(ANALYZER_RESULT);
		Rule rule = context.get(CURRENT_ANALYZER_RULE);
		boolean isReal = rule.getType() == RuleType.REAL;
		if (analyzerResult != null && analyzerResult && isReal) {
			String type = ContextHelper.getType(context);
			Service service = serviceManager.getService(type);
			if (service != null) {
				try {
					if (ContextHelper.isAddOrUpdate(context)) {
						service.insertOrUpdateInfo(context);
					} else if (ContextHelper.isAdd(context)) {
						service.insertInfo(context);
					} else if (ContextHelper.isUpdate(context)) {
						service.updateInfo(context);
					}

					if (log.isDebugEnabled()) {
						log.debug("[{}]监控信息保存成功！", uuid);
					}
				} catch (Exception e) {
					throw new HandlerException("handler.error", e);
				}
			} else {
				log.error("[{}]无法获取指定类型的服务实例。TYPE:{}", uuid, type);
			}
		} else {
			log.info("[{}]监控分析结果为false，不保存监控信息！", uuid);
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
