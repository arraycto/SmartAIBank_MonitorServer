package com.dcfs.smartaibank.manager.monitor.analyzer.service.impl;

import com.dcfs.smartaibank.manager.monitor.analyzer.dao.GuiderMonitorDao;
import com.dcfs.smartaibank.manager.monitor.analyzer.service.GuiderMonitorService;
import com.dcfs.smartaibank.manager.monitor.analyzer.service.ServiceManager;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.ContextHelper;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 引导分流监控服务
 *
 * @author zhaofy
 */
@Component("guiderMonitorService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
public class GuiderMonitorServiceImpl implements GuiderMonitorService, InitializingBean {
	@Autowired
	private GuiderMonitorDao guiderMonitorDao;

	@Autowired
	private ServiceManager serviceManager;

	@Override
	public void insertInfo(MonitorContext context) {
		Map<String, Object> info = getInfo(context);
		guiderMonitorDao.insert(info);
	}

	@Override
	public void updateInfo(MonitorContext context) {
		Map<String, Object> info = getInfo(context);
		guiderMonitorDao.update(info);
	}

	@Override
	public void insertOrUpdateInfo(MonitorContext context) {
		Map<String, Object> info = getInfo(context);
		int num = guiderMonitorDao.update(info);
		if (num <= 0) {
			guiderMonitorDao.insert(info);
		}
	}

	private Map<String, Object> getInfo(MonitorContext context) {
		Map<String, Object> map = new HashMap<>(16);
		ContextHelper.fillFromBody(context, map);
		map.put("SORT_NO", context.getBody("SEQ"));
		map.put("TICKET_INFO", context.getBody("TICKET_NO"));
		return map;
	}

	@Override
	public List<Map<String, Object>> insertAlarm(MonitorContext context) {
		return null;
	}

	@Override
	public void afterPropertiesSet() {
		this.serviceManager.addService(Constants.TYPE_BUSI, this);
	}
}
