package com.dcfs.smartaibank.manager.monitor.analyzer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dcfs.smartaibank.manager.monitor.analyzer.dao.LogDao;
import com.dcfs.smartaibank.manager.monitor.analyzer.service.LogService;
import com.dcfs.smartaibank.manager.monitor.analyzer.service.ServiceManager;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.ContextHelper;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * 日志控服务实现类
 *
 * @author zhaofy
 */
@Component("logService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
public class LogServiceImpl implements LogService, InitializingBean {

	private static final String TRANS_OPEN = "TRANS_OPEN";
	private static final String LOG_ACTION_ADD = "ADD";
	@Autowired
	private LogDao logDao;
	@Autowired
	private ServiceManager serviceManager;

	@Override
	public void insertInfo(MonitorContext context) {
		Map<String, Object> info = getTransInfo(context);
		logDao.insertInfo(info);
	}

	@Override
	public void updateInfo(MonitorContext context) {
		Map<String, Object> info = getTransInfo(context);
		logDao.updateInfo(info);
	}

	@Override
	public void insertOrUpdateInfo(MonitorContext context) {
		String action = context.get(Constants.ACTION);
		Map<String, Object> info = getTransInfo(context);

		if (action == null) {
			return;
		}

		if (TRANS_OPEN.equals(action)) {
			logDao.insertTranInfo(info);
		} else {
			Map<String, Object> totalinfo = getTotalInfo(context);
			if (logDao.getTotalInfo(totalinfo) > 0) {
				switch (action) {
					case "TRANS_CLOSE":
						logDao.updateTranInfo(info);
						break;
					case "SERVICE_END":
					case "SERVICE_OVERTIME":
						Map<String, Object> serviceinfo = getServiceInfo(context);
						logDao.insertServiceInfo(serviceinfo);
						break;
					case "DEVICE_RESPONSE":
						Map<String, Object> deviceinfo = getDeviceInfo(context);
						logDao.insertDeviceInfo(deviceinfo);
						break;
					case "DEVICE_CALL":
						String logaction = context.get("LOG_ACTION");
						Map<String, Object> temp = getVTMDeviceInfo(context);
						if (LOG_ACTION_ADD.equals(logaction)) {
							logDao.insertVTMDeviceInfo(temp);
						} else {
							logDao.updateVTMDeviceInfo(temp);
						}
						break;
					default:
						break;
				}
			}
		}
	}

	private Map<String, Object> getVTMDeviceInfo(MonitorContext context) {
		Map<String, Object> map = new HashMap<>(16);
		map.put(Constants.MID, context.getBody(Constants.MID));
		map.put(Constants.CONVERSATIONID, context.getBody(Constants.CONVERSATIONID));
		map.put(Constants.LOG_DATE, formatDate(context.getBody(Constants.LOG_DATE)));
		map.put(Constants.LOG_TIME, formatTime(context.getBody(Constants.LOG_TIME)));
		map.put("DEVICE_ID", context.get("DEVICE_ID"));
		map.put("FUNCTION_NAME", context.get("FUNCTION_NAME"));
		map.put("TIME_FINISHED", context.get("TIME_FINISHED"));
		return map;
	}

	private Map<String, Object> getDeviceInfo(MonitorContext context) {
		Map<String, Object> map = new HashMap<>(16);
		map.put(Constants.MID, context.getBody(Constants.MID));
		map.put(Constants.CONVERSATIONID, context.getBody(Constants.CONVERSATIONID));
		map.put(Constants.LOG_DATE, formatDate(context.getBody(Constants.LOG_DATE)));
		map.put(Constants.LOG_TIME, formatTime(context.getBody(Constants.LOG_TIME)));
		map.put("DEVICE_ID", context.get("DEVICE_ID"));
		map.put("FUNCTION_NAME", context.get("FUNCTION_NAME"));
		map.put(Constants.TIME_CONSUMED, context.get(Constants.TIME_CONSUMED));
		return map;
	}

	private Map<String, Object> getServiceInfo(MonitorContext context) {
		Map<String, Object> map = new HashMap<>(16);
		map.put(Constants.MID, context.getBody(Constants.MID));
		map.put(Constants.CONVERSATIONID, context.getBody(Constants.CONVERSATIONID));
		map.put(Constants.LOG_DATE, formatDate(context.getBody(Constants.LOG_DATE)));
		map.put(Constants.LOG_TIME, formatTime(context.getBody(Constants.LOG_TIME)));
		map.put(Constants.SERVICE_CODE, context.get(Constants.SERVICE_CODE));
		map.put(Constants.MESSAGE_TYPE, context.get(Constants.MESSAGE_TYPE));
		map.put(Constants.MESSAGE_CODE, context.get(Constants.MESSAGE_CODE));
		map.put(Constants.SERVICE_TYPE, context.get(Constants.SERVICE_TYPE));
		map.put(Constants.TIME_CONSUMED, context.get(Constants.TIME_CONSUMED));
		return map;
	}

	private Map<String, Object> getTransInfo(MonitorContext context) {
		Map<String, Object> map = new HashMap<>(16);
		ContextHelper.fillFromBody(context, map);
		map.put(Constants.TRAN_CODE, context.get(Constants.TRAN_CODE));
		map.put(Constants.TRAN_NAME, context.get(Constants.TRAN_NAME));
		map.put(Constants.TIME_CONSUMED, context.get(Constants.TIME_CONSUMED));
		map.put(Constants.LOG_DATE, formatDate(context.getBody(Constants.LOG_DATE)));
		map.put(Constants.LOG_TIME, formatTime(context.getBody(Constants.LOG_TIME)));
		return map;
	}

	private Map<String, Object> getTotalInfo(MonitorContext context) {
		Map<String, Object> map = new HashMap<>(16);
		map.put(Constants.CONVERSATIONID, context.getBody(Constants.CONVERSATIONID));
		map.put(Constants.MAC, context.getBody(Constants.MAC));
		return map;
	}

	@Override
	public List<Map<String, Object>> insertAlarm(MonitorContext context) {
		return null;
	}


	private String formatDate(Object date) {
		if (date == null) {
			return null;
		} else {
			return ((String) date).replaceAll("-", "");
		}
	}

	private String formatTime(Object time) {
		if (time == null) {
			return null;
		} else {
			return ((String) time).replaceAll(":", "").substring(0, 6);
		}
	}

	@Override
	public void afterPropertiesSet() {
		this.serviceManager.addService(Constants.TYPE_LOG, this);
	}
}
