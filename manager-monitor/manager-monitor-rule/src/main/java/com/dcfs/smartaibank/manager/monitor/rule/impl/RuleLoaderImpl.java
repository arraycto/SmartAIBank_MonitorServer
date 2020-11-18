package com.dcfs.smartaibank.manager.monitor.rule.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.template.TemplateDefine;
import com.dcfs.smartaibank.manager.monitor.rule.RuleCenterManager;
import com.dcfs.smartaibank.manager.monitor.rule.RuleLoader;
import com.dcfs.smartaibank.manager.monitor.rule.dao.RuleDao;
import com.dcfs.smartaibank.manager.monitor.rule.dao.RuleParamDao;
import com.dcfs.smartaibank.manager.monitor.rule.domain.AlarmRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.FilterRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.NotifyRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.PrepareRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.RealRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.TimedRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.DeviceModelCode;
import com.dcfs.smartaibank.manager.monitor.rule.domain.ParamCode;
import com.dcfs.smartaibank.manager.monitor.rule.domain.TranCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 规则加载
 *
 * @author jiazw
 */
public class RuleLoaderImpl implements RuleLoader {
	private static final long TIMEOFFSET = Calendar.getInstance().getTimeZone().getRawOffset() / 1000;
	private static final String STR_SPLIT = "_";
	@Autowired
	private RuleDao ruleDao;

	@Autowired
	private RuleParamDao ruleParamDao;

	@Autowired
	private RuleCenterManager ruleCenterManager;

	@Override
	public void load() {

		//加载过滤规则
		loadFilterRule();

		//设置实时规则集
		loadRealRule();

		//设置定时规则
		loadTimedRule();

		//设置预警规则
		loadAlarmRule();

		//设置通知规则集
		loadNotifyRule();

		//加载预处理规则
		loadPrepareRule();

		//监控预警参数
		loadParamCode();

		//数据字典
		loadDictCode();

		//交易返回码参数
		loadTranCode();

		//设备状态参数
		loadDeviceModelCode();

		//设备名称
		loadDeviceModelName();

		//设备是否激活监控
		loadMechineClassMonitor();

		//设备描述
		loadDeviceDesc();

		//设备管理员
		loadDeviceManager();

		//设备参数设置
		loadDeviceParams();


		//设备参数设置
		loadDefaultDeviceParams();


		//设备参数设置
		loadVtmDeviceParams();


		//模板定义
		loadTempalteDefines();

		//设备工作时间
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowDate = sdf.format(date);

		//查询计划工作时间列表，结果以设备ID和工作开始时间的升序排序
		List<Map<String, Object>> workTimeparams = ruleParamDao.getWorkTimeParams(nowDate);
		// 查询计划停机时间列表，结果以设备ID和工作开始时间的升序排序
		List<Map<String, Object>> shutDownTimeList = ruleParamDao.queryShutDownTime(nowDate);
		// 从计划工作时间中过滤掉计划停机时间，重新计算计划工作时间
		Map<String, List<Date[]>> workTime = filterWorkTime(workTimeparams,
			shutDownTimeList);
		ruleCenterManager.setWorkTimeRules(workTime);
	}


	private void loadFilterRule() {
		//设置过滤规则
		List<FilterRule> filterRules = ruleDao.getAllFilterRule();
		ruleCenterManager.setFilterRules(filterRules);
	}

	private void loadRealRule() {
		List<RealRule> realRules = ruleDao.getAllRealRule();
		ruleCenterManager.setRealRules(realRules);
	}

	private void loadTimedRule() {
		List<TimedRule> timedRules = ruleDao.getAllTimedRule();
		ruleCenterManager.setTimedRules(timedRules);
	}

	private void loadAlarmRule() {
		List<AlarmRule> alarmRules = ruleDao.getAllAlarmRule();
		ruleCenterManager.setAlarmRules(alarmRules);
	}

	private void loadNotifyRule() {
		List<NotifyRule> list = ruleDao.getAllNotifyRule();
		ruleCenterManager.setNotifyRules(list);
	}

	private void loadPrepareRule() {
		List<PrepareRule> prepareRules = ruleDao.getAllPrepareRule();
		ruleCenterManager.setPrepareRules(prepareRules);
	}

	private void loadParamCode() {
		List<ParamCode> paramCodes = ruleParamDao.getParamCode();
		for (ParamCode paramCode : paramCodes) {
			ruleCenterManager.setParamCode(paramCode);
		}
	}

	private void loadDictCode() {
		List<ParamCode> dictCodes = ruleParamDao.getDictCode();
		for (ParamCode dictCode : dictCodes) {
			ruleCenterManager.setDictCode(dictCode);
		}
	}

	private void loadTranCode() {
		List<TranCode> tranCodes = ruleParamDao.getAllTranCode();
		for (TranCode tranCode : tranCodes) {
			ruleCenterManager.setTranCode(tranCode);
		}
	}

	private void loadDeviceModelCode() {
		List<DeviceModelCode> deviceModelCodes = ruleParamDao.getAllDeviceModelCode();
		for (DeviceModelCode deviceModelCode : deviceModelCodes) {
			ruleCenterManager.setDeviceModelCode(deviceModelCode);
		}
	}

	private void loadDeviceModelName() {
		List<Map<String, String>> names = ruleParamDao.getAllDeviceModelName();
		for (Map<String, String> map : names) {
			String key = map.get("KEY");
			String value = map.get("VALUE");
			ruleCenterManager.setDeviceModelName(key, value);
		}
	}

	private void loadMechineClassMonitor() {
		List<Map<String, Object>> actives = ruleParamDao.getAllMClassMonitor();
		for (Map<String, Object> map : actives) {
			String key = (String) map.get("KEY");
			boolean value = (Boolean) map.get("VALUE");
			ruleCenterManager.setMClassMonitor(key, value);
		}
	}

	private void loadDeviceDesc() {
		List<Map<String, String>> descs = ruleParamDao.getAllDeviceDesc();
		for (Map<String, String> map : descs) {
			String key = map.get("KEY");
			String value = map.get("VALUE");
			ruleCenterManager.setDeviceDesc(key, value);
		}
	}

	private void loadDeviceManager() {
		List<Map<String, String>> managers = ruleParamDao.getDeviceManager();
		for (Map<String, String> map : managers) {
			String key = map.get("DEVICEID");
			String value = map.get("MANAGER_ID");
			ruleCenterManager.setDeviceManager(key, value);
		}
	}

	private void loadDeviceParams() {
		List<Map<String, String>> deviceparams = ruleParamDao.getDeviceParams();
		for (Map<String, String> map : deviceparams) {
			Map<String, String> parammap = new HashMap<>(3);
			String key = map.get("DEVICEID");
			parammap.put("NETWORK_DELAY", map.get("NETWORK_DELAY"));
			parammap.put("CASH_UPPER_LIMIT", map.get("CASH_UPPER_LIMIT"));
			parammap.put("CASH_LOWER_LIMIT", map.get("CASH_LOWER_LIMIT"));
			ruleCenterManager.setDeviceParams(key, parammap);
		}
	}

	private void loadDefaultDeviceParams() {
		List<Map<String, String>> defaultdeviceparams = ruleParamDao.getDefaultDeviceParams();
		for (Map<String, String> map : defaultdeviceparams) {
			Map<String, String> parammap = new HashMap<>(3);
			parammap.put("NETWORK_DELAY", map.get("NETWORK_DELAY"));
			parammap.put("CASH_UPPER_LIMIT", map.get("CASH_UPPER_LIMIT"));
			parammap.put("CASH_LOWER_LIMIT", map.get("CASH_LOWER_LIMIT"));
			ruleCenterManager.setDeviceParams("default", parammap);
		}
	}

	private void loadVtmDeviceParams() {
		List<Map<String, String>> vtmdeviceparams = ruleParamDao.getVtmDeviceParams();
		for (Map<String, String> map : vtmdeviceparams) {
			Map<String, String> parammap = new HashMap<>(3);
			String key = map.get("EVENT_CODE");
			parammap.put(Constants.SOURCE_EVENT_CODE, map.get(Constants.SOURCE_EVENT_CODE));
			parammap.put("DEVICE_ID", map.get("DEVICE_ID"));
			parammap.put("EVENT_CODE", map.get("EVENT_CODE"));
			ruleCenterManager.setVtmDeviceParams(key, parammap);
		}
	}


	/****
	 * 从计划工作时间中过滤掉计划停机时间，重新计算计划工作时间
	 * @param workTimeList 工作计划
	 * @param shutDownTimeList 计划停机时间
	 * @return 过滤掉计划停机时间后的结果
	 */
	private Map<String, List<Date[]>> filterWorkTime(
		List<Map<String, Object>> workTimeList,
		List<Map<String, Object>> shutDownTimeList) {
		boolean shutDownTimeEmpty = shutDownTimeList.size() == 0;
		// 存放实际工作时间
		Map<String, List<Date[]>> workTimeMap = new HashMap<>(16);
		for (Map<String, Object> workMap : workTimeList) {
			if (shutDownTimeEmpty) {
				filterWorkTime(workTimeMap, workMap);
			} else {
				filterWorkTime(workTimeMap, workMap, shutDownTimeList);
			}
		}
		return workTimeMap;
	}

	private void filterWorkTime(Map<String, List<Date[]>> workTimeMap,
								Map<String, Object> workMap) {
		Date startDate = getMapValue(workMap, "START_TIME");
		Date endDate = getMapValue(workMap, "END_TIME");
		String groupKey = getGroupKey(workMap);
		List<Date[]> timeList = workTimeMap.get(groupKey);
		if (timeList == null) {
			timeList = new ArrayList<>();
		}
		addTime(timeList, startDate, endDate);
		workTimeMap.put(groupKey, timeList);
		return;
	}

	private void filterWorkTime(Map<String, List<Date[]>> workTimeMap,
								Map<String, Object> workMap, List<Map<String, Object>> shutDownTimeList) {
		int len = shutDownTimeList.size();
		Date startDate = getMapValue(workMap, "START_TIME");
		Date endDate = getMapValue(workMap, "END_TIME");
		String groupKey = getGroupKey(workMap);
		long startTime = getTime(startDate);
		long endTime = getTime(endDate);
		long startDownTime = 0L;
		long endDownTime;
		int index = 0;
		for (int i = 0; i < shutDownTimeList.size(); i++) {
			Map<String, Object> downMap = shutDownTimeList.get(i);
			index = index + 1;
			String tempKey = getGroupKey(downMap);
			if (groupKey.equals(tempKey)) {
				Date startDownDate = getMapValue(downMap, "START_TIME");
				Date endDownDate = getMapValue(downMap, "END_TIME");
				long[] tempTimes = getDateNum(startDownDate, endDownDate);
				if (tempTimes[0] < startDownTime) {
					continue;
				}
				startDownTime = tempTimes[0];
				endDownTime = tempTimes[1];
				List<Date[]> timeList = workTimeMap.get(groupKey) == null
					? new ArrayList<>() : workTimeMap.get(groupKey);
				//T1: 计划工作开始时间 T2: 计划工作结束时间 T3：  计划停机开始时间 T4： 计划停机结束时间
				// 	                T1|_______|T2
				//  T3|________|T4
				if (endDownTime <= startTime) {
					if (len == index) {
						addTime(timeList, startDate, endDate);
						workTimeMap.put(groupKey, timeList);
					} else {
						continue;
					}
				} else if (startDownTime >= endTime) {
					//  T1|___________|T2
					//  				T3|________|T4
					addTime(timeList, startDate, endDate);
					workTimeMap.put(groupKey, timeList);
					break;
				} else if (startDownTime >= startTime && endDownTime <= endTime) {
					//  T1|__________________|T2
					//  	T3|____________|T4
					addTime(timeList, startDate, startDownDate);
					startDate = endDownDate;
					startTime = endDownTime;
					// 如果是最后一条停机记录，则应将T4——T2时间段也添加到容器中
					if (len == index && startTime != endTime) {
						addTime(timeList, startDate, endDate);
					}
				} else if (startDownTime < startTime && endDownTime >= startTime && endDownTime <= endTime) {
					//       T1|______________|T2
					// T3|_________|T4  T5|___________|T6
					if (index < len) {
						startDate = endDownDate;
						startTime = endDownTime;
					} else {
						addTime(timeList, endDownDate, endDate);
					}
				} else if (startDownTime > startTime && startDownTime < endTime && endDownTime > endTime) {
					// T1|_________|T2
					//  	 T3|_________|T4
					addTime(timeList, startDate, startDownDate);
					endDate = startDownDate;
					endTime = startDownTime;
				}
				workTimeMap.put(groupKey, timeList);
			}
			if (i == shutDownTimeList.size() - 1) {
				if (!workTimeMap.containsKey(groupKey)) {
					List<Date[]> timeList = new ArrayList<>();
					addTime(timeList, startDate, endDate);
					workTimeMap.put(groupKey, timeList);
				}
			}
		}
	}

	private void loadTempalteDefines() {
		List<TemplateDefine> defines = ruleDao.getAllTemplateDefine();
		ruleCenterManager.setTemplateDefine(defines);
	}

	private String getGroupKey(Map<String, Object> map) {
		String branchNo = getMapValue(map, Constants.BRANCH_NO);
		String deviceId = getMapValue(map, "DEVICEID");
		return getKey(branchNo, deviceId);
	}

	/**
	 * @param timeList  存放开始时间和结束时间的列表容器
	 * @param startDate 开始时间
	 * @param endDate   结束时间
	 */
	private void addTime(List<Date[]> timeList, Date startDate, Date endDate) {
		Date[] dateObj = new Date[2];
		dateObj[0] = startDate;
		dateObj[1] = endDate;
		timeList.add(dateObj);
	}

	/**
	 * @return [0] 开始时间 [1]结束时间
	 */
	private long[] getDateNum(Date startDate, Date endDate) {
		// 当前故障开始时间和结束时间
		long startTime = getTime(startDate);
		long endTime = Long.MAX_VALUE;
		// 不为空表示故障已恢复,取故障结束时间,否则取最大值
		if (endDate != null) {
			endTime = getTime(endDate);
		}
		return new long[]{startTime, endTime};
	}

	private Long getTime(Date date) {
		return date.getTime() + TIMEOFFSET;
	}

	private <T> T getMapValue(Map<String, Object> map, String key) {
		return (T) map.get(key);
	}

	private String getKey(String... args) {
		StringBuilder sb = new StringBuilder();
		for (String str : args) {
			sb.append(str);
			sb.append(STR_SPLIT);
		}
		return sb.substring(0, sb.length() - 1);
	}

}
