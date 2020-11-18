package com.dcfs.smartaibank.manager.monitor.web.service;

import com.dcfs.smartaibank.manager.monitor.web.domian.AlterNativeInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.HistoryTranType;
import com.dcfs.smartaibank.manager.monitor.web.domian.MonitorRemoteDevice;

import java.util.List;

/**
 * @author tanchena
 * @date 2019/8/23 11:24
 */
public interface MonitorConditionService {

    /**
     * 查询下拉设备型号备选数据
     *
     * @param typeId 设备类型Id
     * @return
     */
    List<AlterNativeInfo> queryDevModelInfo(String typeId);

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
    List<AlterNativeInfo> queryManufacturer(String devModelId, String devId);

    /**
     * 通过设备编号获取外设信息
     * @param deviceId 设备编号
     * @return
     */
    List<MonitorRemoteDevice> getDevClass(String deviceId);

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

    /**
     * 查询报表 交易质量交易类型备选数据
     *
     * @param id 系统id
     * @return 交易类型集合
     */
    List<HistoryTranType> getTransType(String id);

}
