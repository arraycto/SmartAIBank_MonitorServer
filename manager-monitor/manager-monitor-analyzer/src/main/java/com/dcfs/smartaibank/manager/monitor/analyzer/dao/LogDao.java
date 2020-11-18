package com.dcfs.smartaibank.manager.monitor.analyzer.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 日志监控数据DAO
 *
 * @author zhaofy
 */
@Mapper
public interface LogDao {
	/**
	 * 插入交易信息
	 * @param info 交易信息
	 */
	void insertTranInfo(Map<String, Object> info);

	/**
	 * 更新交易信息
	 * @param info 交易信息
	 */
	void updateTranInfo(Map<String, Object> info);

	/**
	 * 插入服务信息
	 * @param serviceinfo 服务信息
	 */
	void insertServiceInfo(Map<String, Object> serviceinfo);

	/**
	 * 更新服务信息
	 * @param serviceinfo 服务信息
	 */
	void updateServiceInfo(Map<String, Object> serviceinfo);

	/**
	 * 插入外设信息
	 * @param deviceinfo 外设信息
	 */
	void insertDeviceInfo(Map<String, Object> deviceinfo);

	/**
	 * 插入VTM外设信息
	 * @param deviceinfo WTM外设信息
	 */
	void insertVTMDeviceInfo(Map<String, Object> deviceinfo);

	/**
	 * 更新VTM外设信息
	 * @param deviceinfo WTM外设信息
	 */
	void updateVTMDeviceInfo(Map<String, Object> deviceinfo);

	/**
	 * 插入
	 * @param info 日志信息
	 */
	void insertInfo(Map<String, Object> info);

	/**
	 * 更新
	 * @param info 日志信息
	 */
	void updateInfo(Map<String, Object> info);

	/**
	 * 查询交易日志记录数
	 * @param info 查询条件
	 * @return 记录数
	 */
	int getTotalInfo(Map<String, Object> info);
}
