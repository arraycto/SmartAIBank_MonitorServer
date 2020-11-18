package com.dcfs.smartaibank.manager.monitor.analyzer.service;

import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;

/**
 * 设备摘要信息服务
 *
 * @author jiazw
 */

public interface DeviceSummaryService {
	/**
	 * 插入或更新数据
	 * @param context 上下文
	 */
	void insertOrUpdateInfo(MonitorContext context);
}
