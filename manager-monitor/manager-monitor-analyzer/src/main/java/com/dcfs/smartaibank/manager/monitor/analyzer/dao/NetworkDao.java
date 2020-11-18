/**
 * @Package com.dcfs.managerplatform.monitor.analyzer.dao
 * @author jiazw
 * @date 2016年6月1日 上午10:12:02
 * @version V1.0
 * 变更记录：
 *   jiazw 2016年6月1日 上午10:12:02 新建
 */
package com.dcfs.smartaibank.manager.monitor.analyzer.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 网络监控数据DAO
 *
 * @author jiazw
 */
@Mapper
public interface NetworkDao {
	/**
	 * 插入
	 *
	 * @param map 网络监控数据
	 */
	void insert(Map<String, Object> map);

	/**
	 * 更新
	 *
	 * @param map 网路监控数据
	 * @return 更新记录数
	 */
	int update(Map<String, Object> map);

	/**
	 * 插入预警信息
	 *
	 * @param map 预警信息
	 * @return 插入记录数
	 */
	int insertAlarm(Map<String, Object> map);

	/**
	 * 查询未处理预警数
	 *
	 * @param map 查询条件
	 * @return 未处理预警数
	 */
	int count(Map<String, Object> map);

	/**
	 * 更新预警信息
	 *
	 * @param map 预警信息
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
	 * 插入预警处理信息
	 *
	 * @param map 预警处理信息
	 * @return 插入记录数
	 */
	int insertAlarmDealInfo(Map<String, Object> map);

	/**
	 * 查询未处理的预警信息
	 *
	 * @param param 查询条件
	 * @return 预警信息
	 */
	List<Map<String, Object>> queryAlarmInfo(Map<String, Object> param);

	/**
	 * 查询督促时间阈值
	 *
	 * @param param 查询条件
	 * @return 督促时间阈值
	 */
	String queryAlarmDelay(Map<String, Object> param);
}
