package com.dcfs.smartaibank.manager.monitor.analyzer.service.impl;

import com.dcfs.smartaibank.manager.monitor.alarmurge.AlarmUrge;
import com.dcfs.smartaibank.manager.monitor.alarmurge.AlarmUrgeManager;
import com.dcfs.smartaibank.manager.monitor.analyzer.dao.AppDao;
import com.dcfs.smartaibank.manager.monitor.analyzer.service.AppService;
import com.dcfs.smartaibank.manager.monitor.analyzer.service.DeviceSummaryService;
import com.dcfs.smartaibank.manager.monitor.analyzer.service.ServiceHelper;
import com.dcfs.smartaibank.manager.monitor.analyzer.service.ServiceManager;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.ContextHelper;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.util.DateUtil;
import com.dcfs.smartaibank.manager.monitor.rule.domain.AlarmLevel;
import com.dcfs.smartaibank.manager.monitor.rule.domain.MonitorItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用监控服务实现类
 *
 * @author jiazw
 */
@Slf4j
@Component("appService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
public class AppServiceImpl implements AppService, InitializingBean {
	@Autowired
	private AppDao appDao;

	@Autowired
	private DeviceSummaryService deviceSummaryService;

	@Autowired
	private ServiceManager serviceManager;

	@Autowired
	private AlarmUrgeManager alarmUrgeManager;

	@Override
	public void insertInfo(MonitorContext context) {
		Map<String, Object> info = getInfo(context);
		appDao.insert(info);
		deviceSummaryService.insertOrUpdateInfo(context);
	}

	@Override
	public void updateInfo(MonitorContext context) {
		Map<String, Object> info = getInfo(context);
		appDao.update(info);
		deviceSummaryService.insertOrUpdateInfo(context);
	}

	@Override
	public void insertOrUpdateInfo(MonitorContext context) {
		Map<String, Object> info = getInfo(context);
		int num = appDao.update(info);
		if (num <= 0) {
			appDao.insert(info);
		}
		deviceSummaryService.insertOrUpdateInfo(context);
	}

	private Map<String, Object> getInfo(MonitorContext context) {
		Map<String, Object> map = new HashMap<>(16);
		ContextHelper.fillFromBody(context, map);
		String mac = context.getBody(Constants.MAC);
		String mType = context.getType();
		//接收时间修改为从上下文中获取
		Date now = context.get(Constants.RECEIVE_TIME);
		long nowL = DateUtil.getTime(now);
		String seqNo = String.format("%s_%s", mType, mac);
		context.put(Constants.SEQ_NO, seqNo);
		map.put(Constants.SEQ_NO, seqNo);
		map.put(Constants.M_TYPE, mType);
		map.put(Constants.RECEIVE_TIME, now);
		map.put(Constants.RECEIVE_TIME_INT, nowL);
		map.put(Constants.M_STATUS, context.get(Constants.M_STATUS));
		return map;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> insertAlarm(MonitorContext context) {
		Map<String, Object> param = getAlarmInfo(context);
		List<Map<String, Object>> list = context.get(Constants.ALARM_LEVEL_LIST);

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		//有预警信息，保存预警信息
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				int alarmLevel = ((AlarmLevel) map.get(Constants.ALARM_LEVEL)).getCode();
				String alarmDesc = (String) map.get(Constants.ALARM_DESC);
				String alarmRuleId = (String) map.get(Constants.ALARM_RULE_ID);
				param.put(Constants.ALARM_LEVEL, alarmLevel);
				param.put(Constants.ALARM_DESC, alarmDesc);
				param.put(Constants.ALARM_RULE_ID, alarmRuleId);

				// 查询预警信息表中是否有未处理的相同预警信息
				int count = appDao.count(param);
				if (count == 0) {
					try {
						int c = appDao.insertAlarm(param);
						// 插入成功
						if (c > 0) {
							setAlarmData(map, param);
							result.add(map);

							//添加分行超时督促处理
							if (map.get("REPAIR_LEVEL") != null) {
								param.put("REPAIR_LEVEL", map.get("REPAIR_LEVEL"));
							} else {
								param.put("REPAIR_LEVEL", alarmLevel);
							}
							String delaystr = appDao.queryAlarmDelay(param);
							long delay = 0;
							if (delaystr != null) {
								delay = Long.parseLong(delaystr) * 60;
							}
							alarmUrgeManager.addAlarmUrge(new AlarmUrge(param.get("ID").toString(), delay, "1"));
						} else {
							map.put(Constants.ALARM_RESULT, false);
						}
					} catch (Exception e) {
						log.error("[" + context.get("UUID") + "]保存外设预警数据时出错,该条预警信息丢失！", e);
						map.put(Constants.ALARM_RESULT, false);
					}
				} else {
					map.put(Constants.ALARM_RESULT, false);
				}
			}
		} else {
			//无预警信息，判断原预警是否可自动解除
			Integer status = context.getBody(Constants.STATUS);
			if (status == 1) {
				List<Map<String, Object>> alarminfolist = appDao.queryAlarmInfo(param);
				for (Map<String, Object> alarminfo : alarminfolist) {
					alarminfo.put("END_TIME", new Date());
					int up = appDao.updateAlarm(alarminfo);
					if (up != 1) {
						appDao.insertAlarmDealInfo(alarminfo);
						alarminfo.put(Constants.ALARM_STATUS, ContextHelper.ALARM_CLOSE);
						appDao.updateAlarmStatus(alarminfo);
					} else {
						alarminfo.put(Constants.ALARM_STATUS, ContextHelper.ALARM_REMOVE);
						appDao.updateAlarmStatus(alarminfo);
					}

				}
			}
		}

		return result;
	}

	private void setAlarmData(Map<String, Object> target, Map<String, Object> source) {
		ServiceHelper.setAlarmData(source, target);
	}

	private Map<String, Object> getAlarmInfo(MonitorContext context) {
		Map<String, Object> map = new HashMap<>(16);
		ContextHelper.fillFromBody(context, map);
		MonitorItem item = context.get(Constants.CURRENT_MONITOR_ITEM);
		String itemId = item.getId();
		map.put(Constants.ALARM_DATE, new Date());
		map.put(Constants.ALARM_STATUS, Constants.ALARM_NEW);
		map.put(Constants.MONITOR_ITEM_ID, itemId);
		map.put(Constants.M_TYPE, context.getType());
		return map;
	}

	@Override
	public void afterPropertiesSet() {
		this.serviceManager.addService(Constants.TYPE_APP, this);
	}
}
