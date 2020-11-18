package com.dcfs.smartaibank.manager.monitor.analyzer.service;

import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.util.DateUtil;

import java.util.Date;
import java.util.Map;

/**
 * 服务帮助类
 *
 * @author jiazw
 */
public final class ServiceHelper {
	private ServiceHelper() {
	}

	/**
	 * 设置预警信息
	 *
	 * @param source 源信息
	 * @param target 目标信息
	 */
	public static void setAlarmData(Map<String, Object> source, Map<String, Object> target) {
		// 获取预警信息主键
		String id = (String) source.get("ID");
		target.put(Constants.ALARM_ID, id);
		target.put(Constants.ALARM_RESULT, true);
		Date date = (Date) source.get(Constants.ALARM_DATE);
		target.put(Constants.ALARM_DATE, DateUtil.getStrTime(date));
		target.put(Constants.ALARM_STATUS, source.get(Constants.ALARM_STATUS));
	}
}
