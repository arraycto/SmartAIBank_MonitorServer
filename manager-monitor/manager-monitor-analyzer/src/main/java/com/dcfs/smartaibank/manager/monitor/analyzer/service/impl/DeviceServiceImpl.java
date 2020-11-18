package com.dcfs.smartaibank.manager.monitor.analyzer.service.impl;

import com.dcfs.smartaibank.manager.monitor.alarmurge.AlarmUrge;
import com.dcfs.smartaibank.manager.monitor.alarmurge.AlarmUrgeManager;
import com.dcfs.smartaibank.manager.monitor.analyzer.dao.DeviceDao;
import com.dcfs.smartaibank.manager.monitor.analyzer.service.DeviceService;
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
 * 外设服务
 * @author jiazw
 */
@Slf4j
@Component("deviceService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
public class DeviceServiceImpl implements DeviceService, InitializingBean {
	@Autowired
	private DeviceDao deviceDao;
	@Autowired
	private DeviceSummaryService deviceSummaryService;
	@Autowired
	private ServiceManager serviceManager;
	@Autowired
	private AlarmUrgeManager alarmUrgeManager;

	@Override
	public void insertInfo(MonitorContext context) {
		Map<String, Object> info = getDeviceInfo(context);
		Map<String, Object> detail = getDeviceDetail(context);
		deviceDao.insertInfo(info);
		deviceDao.insertDetail(detail);
		deviceSummaryService.insertOrUpdateInfo(context);
	}

	@Override
	public void updateInfo(MonitorContext context) {
		Map<String, Object> info = getDeviceInfo(context);
		Map<String, Object> detail = getDeviceDetail(context);
		deviceDao.updateInfo(info);
		deviceDao.updateDetail(detail);
		deviceSummaryService.insertOrUpdateInfo(context);
	}

	@Override
	public void insertOrUpdateInfo(MonitorContext context) {
		Map<String, Object> info = getDeviceInfo(context);
		Map<String, Object> detail = getDeviceDetail(context);
		int num = deviceDao.updateInfo(info);
		if (num <= 0) {
			deviceDao.insertInfo(info);
		}

		if (detail != null) {
			num = deviceDao.updateDetail(detail);
			if (num <= 0) {
				deviceDao.insertDetail(detail);
			}
		}
		deviceSummaryService.insertOrUpdateInfo(context);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> insertAlarm(MonitorContext context) {
		Map<String, Object> param = getAlarmInfo(context);
		List<Map<String, Object>> list = context.get(Constants.ALARM_LEVEL_LIST);
		List<Map<String, Object>> result = new ArrayList<>();
		//有预警信息，保存预警信息
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				int alarmLevel = ((AlarmLevel) map.get(Constants.ALARM_LEVEL)).getCode();
				String statusCode = (String) map.get(Constants.STATUS_CODE);
				String alarmDesc = (String) map.get(Constants.ALARM_DESC);
				String alarmRuleId = (String) map.get(Constants.ALARM_RULE_ID);
				param.put(Constants.ALARM_LEVEL, alarmLevel);
				param.put(Constants.ALARM_DESC, alarmDesc);
				param.put(Constants.ALARM_RULE_ID, alarmRuleId);
				param.put(Constants.STATUS_CODE, statusCode);
				// 查询预警信息表中是否有未处理的相同预警信息
				int count = deviceDao.count(param);
				if (count == 0) {
					try {
						int c = deviceDao.insertAlarm(param);
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
							String delaystr = deviceDao.queryAlarmDelay(param);
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
					//小程序端登记维修任务未已修复，实际情况未修复,自审核改为不通过,1.先找到此条预警
					deviceDao.updateAlarmCheckStatusNo(param);
				}
			}
		} else {
			//无预警信息，判断原预警是否可自动解除
			Integer status = context.get(Constants.STATUS);
			if (status == 1) {
				List<Map<String, Object>> alarminfolist = deviceDao.queryAlarmInfo(param);
				for (Map<String, Object> alarminfo : alarminfolist) {
					alarminfo.put("END_TIME", new Date());
					int up = deviceDao.updateAlarm(alarminfo);
					if (up != 1) {
						deviceDao.insertAlarmDealInfo(alarminfo);
						alarminfo.put(Constants.ALARM_STATUS, ContextHelper.ALARM_CLOSE);
						deviceDao.updateAlarmStatus(alarminfo);
						//预警自解除时，跟新审核状态为已审核通过
						deviceDao.updateAlarmCheckStatus(alarminfo);
					} else {
						alarminfo.put(Constants.ALARM_STATUS, ContextHelper.ALARM_REMOVE);
						deviceDao.updateAlarmStatus(alarminfo);
						//预警自解除时，跟新审核状态为已审核通过
						deviceDao.updateAlarmCheckStatus(alarminfo);
					}
				}
			}
		}
		return result;
	}

	private void setAlarmData(Map<String, Object> target, Map<String, Object> source) {
		ServiceHelper.setAlarmData(source, target);
	}

	private Map<String, Object> getDeviceDetail(MonitorContext context) {
		String detail = context.getBody(Constants.DETAIL);
		if (detail != null) {
			Map<String, Object> map = new HashMap<>(16);
			map.put(Constants.MAC, context.getBody(Constants.MAC));
			map.put(Constants.DEVMODELKEY, context.getBody(Constants.DEVMODELKEY));
			map.put(Constants.DETAIL, detail);
			map.put(Constants.DETAIL_DESC, context.get(Constants.DETAIL_DESC));
			map.put(Constants.SEQ_NO, context.get(Constants.SEQ_NO));
			return map;
		} else {
			return null;
		}
	}

	private Map<String, Object> getDeviceInfo(MonitorContext context) {
		Map<String, Object> map = new HashMap<>(16);
		ContextHelper.fillFromBody(context, map);
		String mac = context.getBody(Constants.MAC);
		String deviceModelKey = context.getBody(Constants.DEVMODELKEY);
		String mType = context.getHeader(Constants.TYPE);
		//接收时间修改为从上下文中获取
		Date now = context.get(Constants.RECEIVE_TIME);
		long nowL = DateUtil.getTime(now);
		String seqNo = String.format("%s_%s_%s", mType, mac, deviceModelKey);
		context.put(Constants.SEQ_NO, seqNo);
		map.put(Constants.SEQ_NO, seqNo);
		map.put(Constants.M_TYPE, mType);
		map.put(Constants.STATUS, context.get(Constants.STATUS));
		map.put(Constants.STATUS_CODE, context.get(Constants.STATUS_CODE));
		map.put(Constants.STATUS_DESC, context.get(Constants.STATUS_DESC));
		map.put(Constants.RECEIVE_TIME, now);
		map.put(Constants.RECEIVE_TIME_INT, nowL);
		map.put(Constants.M_STATUS, context.get(Constants.M_STATUS));
		return map;
	}

	private Map<String, Object> getAlarmInfo(MonitorContext context) {
		Map<String, Object> map = new HashMap<>(16);
		ContextHelper.fillFromBody(context, map);
		MonitorItem item = context.get(Constants.CURRENT_MONITOR_ITEM);
		String itemId = item.getId();
		map.put(Constants.ALARM_DATE, new Date());
		map.put(Constants.ALARM_STATUS, Constants.ALARM_NEW);
		map.put(Constants.STATUS_DESC, context.get(Constants.STATUS_DESC));
		map.put(Constants.MONITOR_ITEM_ID, itemId);
		map.put(Constants.M_TYPE, context.getType());
		return map;
	}

	@Override
	public void afterPropertiesSet() {
		this.serviceManager.addService(Constants.TYPE_DEVICE, this);
	}
}
