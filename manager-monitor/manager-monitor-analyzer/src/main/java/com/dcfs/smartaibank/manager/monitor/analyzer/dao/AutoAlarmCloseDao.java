package com.dcfs.smartaibank.manager.monitor.analyzer.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 自动预警关闭
 * @author jiazw
 */
@Mapper
public interface AutoAlarmCloseDao {

	/**
	 * 查询预警信息
	 * @param querymap 查询条件
	 * @return 预警信息集合
	 */
	List<Map<String, Object>> queryAlarmInfo(Map<String, Object> querymap);

	/**
	 * 更新预警信息
	 * @param map 待更新信息
	 */
	void updateAlarm(Map<String, Object> map);

	/**
	 * 清理设备预警信息
	 */
	void clearDeviceAlarmData();

	/**
	 * 清理交易预警信息
	 */
	void clearTranAlarmData();

	/**
	 * 清理故障信息
	 */
	void clearFaultRecordData();

}
