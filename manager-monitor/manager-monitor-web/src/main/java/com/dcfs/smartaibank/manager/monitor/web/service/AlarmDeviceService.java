package com.dcfs.smartaibank.manager.monitor.web.service;

import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmMaintainer;
import com.dcfs.smartaibank.manager.monitor.web.param.AlarmParams;
import com.dcfs.smartaibank.manager.monitor.web.param.AlarmRepairInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmSimpleResult;
import com.dcfs.smartaibank.manager.monitor.web.param.AlarmWorkInfo;
import com.github.pagehelper.PageInfo;



/**
 * @author tanchen1
 * @author wangtingo
 * @date 2019/6/20 10:56
 * @since
 */
public interface AlarmDeviceService {

    /**
     * 查询设备预警信息
     *
     * @param queryType   查询类型
     * @param alarmParams 查询参数
     * @param branchNo    机构编号
     * @return
     */
    PageInfo<AlarmInfo> getAlarmInfo(String queryType, AlarmParams alarmParams, String branchNo);

    /**
     * 查询首页 预警面板信息（简单面板）
     *
     * @param branchNo 机构编号
     * @return
     */
    AlarmSimpleResult getSimpleAlarmInfo(String branchNo);

    /**
     * 开始处理预警
     *
     * @param userId  用户Id
     * @param alarmId 预警流水Id
     */
    void beginDeal(String userId, String alarmId);

    /**
     * 挂起
     *
     * @param alarmId 预警流水编号
     */
    void handUp(String alarmId);

    /**
     * 查询未处理预警信息
     *
     * @param branchNo 机构编号
     * @return
     */
    Integer getUnDealInfo(String branchNo);

    /**
     * 预警信息报修
     *
     * @param alarmRepairInfo 预警报修实体
     */
    void insertRepair(AlarmRepairInfo alarmRepairInfo);

    /**
     * 预警信息工单登记
     *
     * @param alarmWorkInfo 预警工单登记实体
     */
    void updateRepair(AlarmWorkInfo alarmWorkInfo);

    /**
     * 查询维修人信息
     *
     * @param alarmId 预警id
     * @return AlarmMaintainer 维修人员实体
     */
    AlarmMaintainer getMaintainer(String alarmId);

    /**
     * 工单回显信息
     *
     * @param id 预警流水id
     * @return 工单登记信息
     */
    AlarmWorkInfo getAlarmInfo(String id);
    /**
     * 预警信息报修
     *
     * @param queryType 预警报修实体
     * @param alarmParams 预警报修实体类
     * @param branchNo 预警报修实体机构
     * @param index 预警报修实体索引
     * @return AlarmMaintainer 维修人员实体
     */
    PageInfo<AlarmInfo> getAlarmInfobyLevel(String queryType, AlarmParams alarmParams, String branchNo, String index);
}
