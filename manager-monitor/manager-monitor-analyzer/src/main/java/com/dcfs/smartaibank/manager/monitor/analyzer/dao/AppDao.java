package com.dcfs.smartaibank.manager.monitor.analyzer.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 应用监控数据DAO
 *
 * @author jiazw
 */
@Mapper
public interface AppDao {
    /**
     * 插入应用监控该数据
     *
     * @param map 应用监控数据
     */
    void insert(Map<String, Object> map);

    /**
     * 更新应用监控数据
     * @param map 应用监控数据
     * @return 更新成功记录数
     */
    int update(Map<String, Object> map);

    /**
     * 插入应用预警信息
     * @param map 应用预警信息
     * @return 插入成功记录数
     */
    int insertAlarm(Map<String, Object> map);

    /**
     * 未处理完成的预警数量
     * @param map 查询条件
     * @return 未处理完成的预警数量
     */
    int count(Map<String, Object> map);

    /**
     * 查询预警信息
     * @param param 预警信息
     * @return
     */
    List<Map<String, Object>> queryAlarmInfo(Map<String, Object> param);

    /**
     * 更新预警信息
     * @param map 预警信息
     * @return 成功更新的记录数
     */
    int updateAlarm(Map<String, Object> map);

    /**
     * 更新预警状态
     * @param map 预警状态
     * @return 成功更新的记录数
     */
    int updateAlarmStatus(Map<String, Object> map);

    /**
     * 插入预警处理信息
     * @param map 预警处理信息
     * @return 插入成功的记录数
     */
    int insertAlarmDealInfo(Map<String, Object> map);

    /**
     * 查询督促预警处理时间阈值
     * @param map 查询条件
     * @return 预警时间阈值
     */
    String queryAlarmDelay(Map<String, Object> map);
}
