package com.dcfs.smartaibank.manager.monitor.web.dao;

import com.dcfs.smartaibank.manager.monitor.web.domian.AlterNativeInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.MonitorRemoteDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tanchena
 * @date 2019/8/23 11:27
 */
public interface MonitorConditionDao {

    /**
     * 查询下拉设备型号备选数据
     *
     * @param id 设备类型Id
     * @return
     */
    List<AlterNativeInfo> queryDevModelInfo(@Param("id") String id);

    /**
     * 查询下拉设备类型备选数据
     *
     * @return
     */
    List<AlterNativeInfo> queryDevTypeInfo();

    /**
     * 查询备选机构信息
     *
     * @param branchNo
     * @return
     */
    List<AlterNativeInfo> queryBranchInfo(String branchNo);

    /**
     * 查询设备厂商
     *
     * @param devModelId 设备型号编号
     * @param devId 设备类型编号
     * @return
     */
    List<AlterNativeInfo> queryManufacturer(@Param("devModelId") String devModelId, @Param("devId") String devId);

    /**
     * 通过设备编号获取外设信息
     * @param deviceId
     * @return
     */
    List<MonitorRemoteDevice> getDevClass(@Param("deviceId") String deviceId);

    /**
     * 查询对账备选机构信息
     *
     * @param branchNo
     * @return
     */
    List<AlterNativeInfo> getRecordOrgInfo(String branchNo);

    /**
     * 查询对账备选设备信息
     *
     * @param branchNo
     * @param classKey
     * @return
     */
    List<AlterNativeInfo> getDeviceInfo(String branchNo, String classKey);

}
