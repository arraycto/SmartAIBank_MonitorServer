package com.dcfs.smartaibank.manager.monitor.analyzer.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 故障历史记录DAO
 *
 * @author jiazw
 */
@Mapper
public interface FaultRecordDao {

	/**
	 * 新增故障记录
	 *
	 * @param map 参数
	 */
	void insertInfo(Map<String, Object> map);

	/**
	 * 更新故障记录
	 *
	 * @param map 更新条件
	 * @return 更新记录数
	 */
	int updateInfo(Map<String, Object> map);

	/**
	 * 查询故障历史记录
	 *
	 * @param map 查询条件
	 * @return 查询结果集
	 */
	List<Map<String, Object>> queryInfo(Map<String, Object> map);

	/**
	 * 查询制定日期的故障历史记录
	 *
	 * @param map 查询条件
	 * @return 查询结果
	 */
	List<Map<String, Object>> queryAllInfo(Map<String, Object> map);

	/**
	 * 自动生成昨日未解决的错误信息
	 *
	 * @param map 错误信息
	 */

	void createFaultRecord(Map<String, Object> map);
}
