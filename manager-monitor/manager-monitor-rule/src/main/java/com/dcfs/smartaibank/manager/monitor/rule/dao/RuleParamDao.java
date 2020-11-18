package com.dcfs.smartaibank.manager.monitor.rule.dao;

import java.util.List;
import java.util.Map;

import com.dcfs.smartaibank.manager.monitor.rule.domain.DeviceModelCode;
import com.dcfs.smartaibank.manager.monitor.rule.domain.ParamCode;
import com.dcfs.smartaibank.manager.monitor.rule.domain.TranCode;
import org.apache.ibatis.annotations.Param;

/**
 * 规则参数DAO
 *
 * @author jiazw
 */
public interface RuleParamDao {
	/**
	 * 获取参数代码列表
	 *
	 * @return 参数代码列表
	 */
	List<ParamCode> getParamCode();

	/**
	 * 获取全部的设备类型代码
	 *
	 * @return 全部的设备类型代码
	 */
	List<DeviceModelCode> getAllDeviceModelCode();

	/**
	 * 获取全部的设备类型名称
	 *
	 * @return 全部的设备类型名称
	 */
	List<Map<String, String>> getAllDeviceModelName();

	/**
	 * 获取机具是否监控参数
	 *
	 * @return 机具是否监控参数
	 */
	List<Map<String, Object>> getAllMClassMonitor();

	/**
	 * 获取交易码配置参数
	 *
	 * @return 交易码配置参数
	 */
	List<TranCode> getAllTranCode();

	/**
	 * 获取数据字典
	 *
	 * @return 数据字典
	 */
	List<ParamCode> getDictCode();

	/**
	 * 获取设备状态描述
	 *
	 * @return 设备状态描述
	 */
	List<Map<String, String>> getAllDeviceDesc();

	/**
	 * 获取设备管理员
	 *
	 * @return 设备管理员
	 */
	List<Map<String, String>> getDeviceManager();

	/**
	 * 获取设备参数
	 *
	 * @return 设备参数
	 */
	List<Map<String, String>> getDeviceParams();

	/**
	 * 获取系统默认的参数
	 *
	 * @return 默认的参数
	 */
	List<Map<String, String>> getDefaultDeviceParams();

	/**
	 * 获取VTM设备参数
	 *
	 * @return VTM设备参数
	 */
	List<Map<String, String>> getVtmDeviceParams();

	/**
	 * 获取工作时间
	 *
	 * @param nowDate 当前日期格式为YYYY-MM-DD
	 * @return 工作日期参数
	 */
	List<Map<String, Object>> getWorkTimeParams(@Param("NOW_DATE") String nowDate);

	/**
	 * 获取停机计划
	 *
	 * @param nowDate 当前日期格式为YYYY-MM-DD
	 * @return 停机计划
	 */
	List<Map<String, Object>> queryShutDownTime(@Param("NOW_DATE") String nowDate);
}
