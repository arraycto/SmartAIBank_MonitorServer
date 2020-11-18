package com.dcfs.smartaibank.manager.monitor.analyzer.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 通用DAO
 *
 * @author jiazw
 */
@Mapper
public interface CommonDao {
	/**
	 * 计算重复次数
	 *
	 * @param map 查询条件
	 */
	void computeRepeatCount(Map<String, Object> map);
}
