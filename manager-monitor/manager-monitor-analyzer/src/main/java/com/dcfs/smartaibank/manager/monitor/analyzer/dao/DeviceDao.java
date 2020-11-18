package com.dcfs.smartaibank.manager.monitor.analyzer.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 设备DAO
 *
 * @author jiazw
 */
@Mapper
public interface DeviceDao {
	/**
	 * 插入
	 *
	 * @param map 设备信息
	 */
	void insertInfo(Map<String, Object> map);

	/**
	 * 更新
	 *
	 * @param map 设备信息
	 * @return 成功更新记录数
	 */
	int updateInfo(Map<String, Object> map);

	/**
	 * 插入详情
	 *
	 * @param map 设备详情
	 */
	void insertDetail(Map<String, Object> map);

	/**
	 * 更新详情
	 *
	 * @param map 设备详情
	 * @return 成功更新记录数
	 */
	int updateDetail(Map<String, Object> map);

	/**
	 * 插入预警信息
	 *
	 * @param map 预警信息
	 * @return 成功插入的记录数
	 */
	int insertAlarm(Map<String, Object> map);

	/**
	 * 查询外设状态指定预警规则的数量
	 *
	 * @param map 查询条件
	 * @return 数量
	 */
	int count(Map<String, Object> map);

	/**
	 * 更新预警结束时间
	 *
	 * @param map 更新数据
	 * @return 更新记录数
	 */
	int updateAlarm(Map<String, Object> map);

	/**
	 * 更新预警状态
	 *
	 * @param map 预警状态
	 * @return 更新记录数
	 */
	int updateAlarmStatus(Map<String, Object> map);

	/**
	 * 预警自动处理
	 *
	 * @param map 预警自动处理信息
	 * @return 插入记录数
	 */
	int insertAlarmDealInfo(Map<String, Object> map);

	/**
	 * 查询预警信息
	 *
	 * @param param 查询条件
	 * @return 预警信息
	 */
	List<Map<String, Object>> queryAlarmInfo(Map<String, Object> param);

	/**
	 * 查询督促时间阈值
	 *
	 * @param param 查询条件
	 * @return 时间阈值
	 */
	String queryAlarmDelay(Map<String, Object> param);

	/**
	 * 更新预警检查状态
	 *
	 * @param map 更新数据
	 * @return 更新记录数
	 */
	int updateAlarmCheckStatus(Map<String, Object> map);

	/**
	 * 更新预警检查状态
	 *
	 * @param map 更新数据
	 * @return 更新记录数
	 */
	int updateAlarmCheckStatusNo(Map<String, Object> map);
}
