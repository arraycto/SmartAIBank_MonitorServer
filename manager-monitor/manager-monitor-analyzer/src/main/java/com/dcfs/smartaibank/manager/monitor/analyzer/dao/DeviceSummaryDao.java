package com.dcfs.smartaibank.manager.monitor.analyzer.dao;

import com.dcfs.smartaibank.manager.monitor.rule.domain.DeviceInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 机具汇总数据DAO
 *
 * @author jiazw
 */
@Mapper
public interface DeviceSummaryDao {
	/**
	 * 插入
	 *
	 * @param map 机具汇总信息
	 */
	void insert(Map<String, Object> map);

	/**
	 * 更新
	 *
	 * @param map 机具汇总信息
	 * @return 更新记录数
	 */
	int update(Map<String, Object> map);

	/**
	 * 机具监控记录数
	 *
	 * @param mac 机具MAC
	 * @return 记录数
	 */
	Integer querySumCount(String mac);

	/**
	 * 机构信息查询
	 *
	 * @param branchNo 机构编号
	 * @return 机构信息
	 */
	Map<String, Object> queryBranchInfo(String branchNo);

	/**
	 * 查询当前机具监控数据
	 *
	 * @param map 查询条件
	 * @return 当前机具监控数据
	 */
	Map<String, Object> queryStatus(Map<String, Object> map);

	/**
	 * 从mas_device_info中查询当前时间下指定设备的App、Network、device的运行状况
	 *
	 * @param map 查询条件
	 * @return 当前机具的监控数据
	 */
	List<DeviceInfo> selectSingleDeviceInfo(Map<String, Object> map);
}
