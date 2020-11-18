package com.dcfs.smartaibank.manager.monitor.analyzer.service;

import java.util.List;
import java.util.Map;

import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;

/**
 * 监控预警数据服务通用接口
 *
 * @author jiazw
 */
public interface Service {
	/**
	 * 插入信息
	 *
	 * @param context 上下文
	 */
	void insertInfo(MonitorContext context);

	/**
	 * 更新信息
	 *
	 * @param context 上下文
	 */
	void updateInfo(MonitorContext context);

	/**
	 * 插入或者更新信息
	 *
	 * @param context 上下文
	 */
	void insertOrUpdateInfo(MonitorContext context);

	/**
	 * 插入预警信息
	 *
	 * @param context 上下文
	 * @return 预警信息
	 */
	List<Map<String, Object>> insertAlarm(MonitorContext context);
}
