package com.dcfs.smartaibank.manager.monitor.analyzer.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 引导分流监控数据批处理
 *
 * @author jiazw
 */
@Mapper
public interface GuiderMonitorBatchDao {
	/**
	 * 删除机构繁忙度数据
	 */
	void clearBranchBusyData();

	/**
	 * 删除村镇繁忙度数据
	 */
	void clearCountyBusyData();

	/**
	 * 删除市繁忙度数据
	 */
	void clearCityBusyData();

	/**
	 * 删除省繁忙度数据
	 */
	void clearProvinceBusyData();

	/**
	 * 删除国家繁忙度数据
	 */
	void clearCountryBusyData();

	/**
	 * 插入或更新机构繁忙度数据
	 */
	void insertOrUpdateBranchBusyData();

	/**
	 * 插入机构繁忙度初始数据
	 *
	 * @param map 初始数据
	 */
	void insertBranchBusyData(Map<String, Object> map);

	/**
	 * 更新机构繁忙度状态
	 *
	 * @param map 更新数据
	 */
	void updateBranchBusyStatus(Map<String, Object> map);

	/**
	 * 插入村镇繁忙度初始数据
	 *
	 * @param map 初始数据
	 */
	void insertCountyBusyData(Map<String, Object> map);


	/**
	 * 插入城市繁忙度初始数据
	 *
	 * @param map 初始数据
	 */
	void insertCityBusyData(Map<String, Object> map);

	/**
	 * 更新城市繁忙度状态
	 *
	 * @param map 初始数据
	 */
	void updateCityBusyStatus(Map<String, Object> map);

	/**
	 * 插入省繁忙度初始数据
	 *
	 * @param map 初始数据
	 */
	void insertProvinceBusyData(Map<String, Object> map);

	/**
	 * 更新省繁忙度状态
	 *
	 * @param map 初始数据
	 */
	void updateProvinceBusyStatus(Map<String, Object> map);

	/**
	 * 插入国家繁忙度初始数据
	 *
	 * @param map 初始数据
	 */
	void insertCountryBusyData(Map<String, Object> map);

	/**
	 * 更新国家繁忙度状态
	 *
	 * @param map 初始数据
	 */
	void updateCountryBusyStatus(Map<String, Object> map);

	/**
	 * 删除机构服务质量数据
	 */
	void clearBranchQualData();

	/**
	 * 插入机构服务质量数据
	 *
	 * @param map 服务质量数据
	 */
	void insertBranchQualData(Map<String, Object> map);

	/**
	 * 删除村镇服务质量数据
	 */
	void clearCountyQualData();

	/**
	 * 插入村镇服务质量数据
	 *
	 * @param map 服务质量数据
	 */
	void insertCountyQualData(Map<String, Object> map);

	/**
	 * 删除市服务质量数据
	 */
	void clearCityQualData();

	/**
	 * 插入市服务质量数据
	 *
	 * @param map 服务质量数据
	 */
	void insertCityQualData(Map<String, Object> map);

	/**
	 * 删除省服务质量数据
	 */
	void clearProvinceQualData();

	/**
	 * 插入省服务质量数据
	 *
	 * @param map 服务质量数据
	 */
	void insertProvinceQualData(Map<String, Object> map);

	/**
	 * 删除全国服务质量数据
	 */
	void clearCountryQualData();

	/**
	 * 插入全国服务质量数据
	 *
	 * @param map 服务质量数据
	 */
	void insertCountryQualData(Map<String, Object> map);

}
