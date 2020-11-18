/**
 * @Title: DeviceDao.java
 * @Package com.dcfs.managerplatform.monitor.analyzer.dao
 * @Description: 交易监控数据DAO
 * @author jiazw
 * @date 2016年5月19日 上午10:12:02
 * @version V1.0
 * 变更记录：
 *   jiazw 2016年5月10日 上午10:12:02 新建
 */
package com.dcfs.smartaibank.manager.monitor.analyzer.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 交易监控数据DAO
 *
 * @author jiazw
 */
@Mapper
public interface TranDao {
	/**
	 * 插入交易数据
	 *
	 * @param map 交易数据
	 */
	void insert(Map<String, Object> map);

	/**
	 * 更新交易数据
	 *
	 * @param map 交易数据
	 * @return 成功更新记录数
	 */
	int update(Map<String, Object> map);

	/**
	 * 插入预警信息
	 *
	 * @param map 预警信息
	 * @return 成功插入记录数
	 */
	int insertAlarm(Map<String, Object> map);

	/**
	 * 更新预警统计信息
	 *
	 * @param map 预警统计信息
	 */
	void insertSum(Map<String, Object> map);

	/**
	 * 更新预警统计信息
	 *
	 * @param map 预警统计信息
	 * @return 成功更新记录数
	 */
	int updateSum(Map<String, Object> map);
}
