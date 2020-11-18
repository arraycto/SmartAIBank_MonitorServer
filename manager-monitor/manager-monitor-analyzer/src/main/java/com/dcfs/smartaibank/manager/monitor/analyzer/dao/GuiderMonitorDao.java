package com.dcfs.smartaibank.manager.monitor.analyzer.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 排队业务监控数据DAO
 *
 * @author zhaofy
 */
@Mapper
public interface GuiderMonitorDao {
	/**
	 * 插入
	 *
	 * @param map 监控数据
	 */
	void insert(Map<String, Object> map);

	/**
	 * 更新
	 *
	 * @param map 监控数据
	 * @return 更新记录数
	 */
	int update(Map<String, Object> map);

	/**
	 * 清理
	 */
	void clearData();
}
