package com.dcfs.smartaibank.manager.monitor.alarmurge.dao;

import java.util.List;
import java.util.Map;

/**
 * 预警督促DAO
 *
 * @author jiazw
 */
public interface AutoAlarmUrgeDao {

	/**
	 * 查询预警信息
	 *
	 * @param querymap 更新信息
	 * @return 预警信息
	 */
	List<Map<String, Object>> queryAlarmInfo(Map<String, Object> querymap);

	/**
	 * 更新预警信息
	 *
	 * @param map 预警信息
	 */
	void updateAlarm(Map<String, Object> map);

	/**
	 * 查询分行督促用户
	 *
	 * @param querymap 更新信息
	 * @return 督促用户
	 */
	List<Map<String, Object>> queryBranchNoticeUsers(Map<String, Object> querymap);

	/**
	 * 查询总行督促用户
	 *
	 * @return 督促用户
	 */
	List<Map<String, Object>> queryHeaderNoticeUsers();

	/**
	 * 获取预警对应的厂商维护人员信息
	 *
	 * @param querymap 查询条件
	 * @return 厂商维护人员信息
	 */
	List<Map<String, Object>> getRepairManInfo(Map<String, Object> querymap);

	/**
	 * 获取厂商任务的预计修复时间
	 *
	 * @param querymap 查询条件
	 * @return 厂商任务的预计修复时间
	 */
	List<Map<String, Object>> getRepairPlanDateInfo(Map<String, Object> querymap);

	/**
	 * 更新短信状态
	 *
	 * @param map 更新信息
	 */
	void updateRepairPlanDateSMSstatus(Map<String, Object> map);

}
