package com.dcfs.smartaibank.manager.monitor.web.dao;


import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmDealInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmMaintainer;
import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmNum;
import com.dcfs.smartaibank.manager.monitor.web.param.AlarmParams;
import com.dcfs.smartaibank.manager.monitor.web.param.AlarmRepairInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmTranInfo;
import com.dcfs.smartaibank.manager.monitor.web.param.AlarmTransParams;
import com.dcfs.smartaibank.manager.monitor.web.param.AlarmWorkInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


/**
 * 预警信息查询接口
 *
 * @author tanchen1
 * @author wangtingo
 * @className AlarmQueryDao.java
 * @date 2019-6-20 17:15:45
 */
@Mapper
public interface AlarmQueryDao {

    /**
     * 1.查询全部机具的预警详细信息
     *
     * @param alarmParams
     * @return
     */
    List<AlarmInfo> queryDevAlarmInfo(AlarmParams alarmParams);

    /**
     * 2.查询我处理的机具的预警信息
     *
     * @param alarmParams
     * @return
     */
    List<AlarmInfo> queryDevAlarmInfoByUser(AlarmParams alarmParams);

    /**
     * 3.开始处理
     *
     * @param alarmDealInfo
     */
    void insertAlarmBeginDeal(AlarmDealInfo alarmDealInfo);

    /**
     * 4.查询所有机具的未处理消息数量
     *
     * @param branchNo
     * @return
     */
    Integer queryAlarmCount(String branchNo);

    /**
     * 5.解除预警消息
     *
     * @param id
     * @param status
     */
    void updateAlarmInfoStatus(@Param("id") String id, @Param("status") String status);

    /**
     * 6.挂起时候的修改状态规则
     *
     * @param id
     * @param status
     */
    void updateHandUpStatus(@Param("id") String id, @Param("status") String status);

    /**
     * 7.挂起操作
     *
     * @param alarmId 预警流水号
     * @param date    当前时间
     */
    void updateDealHandUp(@Param("alarmId") String alarmId, @Param("date") Date date);

    /**
     * 8.交易预警信息展示
     *
     * @param alarmTransParams
     * @return
     */
    List<AlarmTranInfo> queryAlarmTranInfo(AlarmTransParams alarmTransParams);

    /**
     * 9.总行 首页 简单面板预警（设备+交易）信息查询
     *
     * @param branchNo 机构编号
     * @return
     */
    List<AlarmInfo> querySimpleAlarmInfo(String branchNo);

    /**
     * 10.总行 首页 简单面板预警（设备+交易）数量查询
     *
     * @param branchNo 机构编号
     * @param level    预警级别
     * @return
     */
    List<AlarmNum> queryAlarmNum(@Param("branchNo") String branchNo, @Param("level") String level);

    /**
     * 预警处理记录表里面报修时间修改
     *
     * @param alarmId 预警id
     * @param repairDate 报修时间
     */
    void updateAlarmRepairInfo(@Param("alarmId") String alarmId, @Param("repairDate") Date repairDate);

    /**
     * 工单登记
     *
     * @param alarmWorkInfo
     */
    void updateAlarmRepairRecord(AlarmWorkInfo alarmWorkInfo);

    /**
     * 工单登记后更新预警状态
     *
     * @param alarmWorkInfo 预警记录实体
     */
    void updateAlarmCloseStatus(AlarmWorkInfo alarmWorkInfo);


    /**
     * 更新预警状态
     *
     * @param alarmId 预警id
     * @param status  预警状态
     */
    void updateAlarmStatus(String alarmId, String status);

    /**
     * 工单回显信息
     *
     * @param id 预警流水id
     * @return 工单登记信息
     */
    AlarmWorkInfo getAlarmInfo(@Param("id") String id);

    /**
     * 新增报修信息
     *
     * @param alarmRepairInfo
     */
    void insertRepairInfo(AlarmRepairInfo alarmRepairInfo);

    /**
     * 查询维修人员信息
     *
     * @param alarmId 预警id
     * @return 维修人员实体
     */
    AlarmMaintainer getMaintainer(String alarmId);

    /**
     * 解除预警交易操作
     * @param id
     */
    void removeTran(@Param("id") String id);
    /**查询预警级别
     * @param branchNo 机构编号
     * @param level    预警级别
     * @return
     */
    List<AlarmInfo> queryDevAlarmInfobyLevel(@Param("branchNo") String branchNo, @Param("level") String level);
    /**查询用户预警级别
     * @param branchNo 机构编号
     * @param level    预警级别
     * @return
     */
    List<AlarmInfo> queryDevAlarmInfoByUserbyLevel(@Param("branchNo") String branchNo, @Param("level") String level);
    /**查询预警级别等级
     * @param branchNo 机构编号
     * @param level    预警级别
     * @return
     */
    List<AlarmTranInfo> queryAlarmTranInfobyLevel(@Param("branchNo") String branchNo, @Param("level") String level);
}
