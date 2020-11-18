package com.dcfs.smartaibank.manager.monitor.web.service;

import com.dcfs.smartaibank.manager.monitor.web.domian.DeviceClassCount;
import com.dcfs.smartaibank.manager.monitor.web.domian.DeviceDetail;
import com.dcfs.smartaibank.manager.monitor.web.domian.DeviceRunningStatusSum;
import com.dcfs.smartaibank.manager.monitor.web.domian.Operator;
import com.dcfs.smartaibank.manager.monitor.web.domian.PeripheralStatus;
import com.dcfs.smartaibank.manager.monitor.web.domian.RemoteOperationRecode;
import com.dcfs.smartaibank.manager.monitor.web.enums.DeviceDetailsSortRuleEnum;
import com.dcfs.smartaibank.manager.monitor.web.enums.SortOrderEnum;
import com.dcfs.smartaibank.manager.monitor.web.param.DeviceRunningQueryParams;
import com.dcfs.smartaibank.manager.monitor.web.param.RemoteOptCondition;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 设备运行业务层接口
 *
 * @author wangjzm
 * @data 2019/06/17 09:52
 * @since 1.0.0
 */

public interface MonitorDeviceService {

    /**
     * 根据机构号、机构类型、查询条件（设备类型、设备型号、设备id、设备厂商、机具状态）统计设备运行汇总信息，
     * 汇总信息包括机具总数及分机具运行状态数量及占比
     * 1.branchStatsType为1代表营业型机构，sql查询的是当前传入机构id的汇总信息
     * 2.branchStatsType为2代表管理型机构，sql查询的是当前传入机构id的下属营业性机构（包含自身）的汇总信息
     *
     * @param branchId                 机构id
     * @param branchStatsType          机构数据统计类型 : 1-作为营业机构进行统计,2-作为上级机构进行汇总
     * @param deviceRunningQueryParams 设备运行查询条件（包括设备类型、设备型号、设备id、设备厂商）
     * @return 机具总数及分机具运行状态数量及占比信息
     */
    DeviceRunningStatusSum selectEachStatusCountGatherInfo(String branchId,
                                                           Integer branchStatsType,
                                                           DeviceRunningQueryParams deviceRunningQueryParams);


    /**
     * 按条件分页查询设备运行列表详情信息。如果传入的sort字段为空，默认排序按照机构表中机构顺序（优先）和设备编号进行排序。
     * sort(1-设备总状态,2-网络通讯状态,3-应用运行状态,4-外设运行状态)和sortOrder（1-降序，2-升序)字段
     * 不为空，排序按照两个字段进行组合排序
     *
     * @param pageNum                  每页记录数
     * @param pageSize                 页码
     * @param branchId                 机构id
     * @param sortRule                 排序规则(1-设备总状态,2-网络通讯状态,3-应用运行状态,4-外设运行状态)
     * @param sortOrder                排序顺序（1-降序，2-升序)
     * @param deviceRunningQueryParams 设备运行查询条件（设备类型、设备型号、设备id、设备厂商、机具状态）
     * @return 设备运行详情信息集合
     */
    PageInfo<DeviceDetail> selectDeviceOperationDetails(Integer pageNum,
                                                        Integer pageSize,
                                                        String branchId,
                                                        DeviceDetailsSortRuleEnum sortRule,
                                                        SortOrderEnum sortOrder,
                                                        DeviceRunningQueryParams deviceRunningQueryParams);

    /**
     * 查询设备远程操作记录列表，查询条件可选（设备id、设备类型、所属机构、操作人员）
     *
     * @param pageNum            每页记录数
     * @param pageSize           页码
     * @param branchId           机构id
     * @param remoteOptCondition 设备远程操作记录查询条件（设备id、设备类型、所属机构、操作人员）
     * @return 设备远程操作记录集合
     */
    PageInfo<RemoteOperationRecode> selectAllRemoteInfo(Integer pageNum,
                                                        Integer pageSize,
                                                        String branchId,
                                                        RemoteOptCondition remoteOptCondition);

    /**
     * 根据mac查询设备远程操作记录
     *
     * @param deviceMac mac地址
     * @return 设备远程操作记录集合
     */
    List<RemoteOperationRecode> selectRemoteInfo(String deviceMac);

    /**
     * /**
     * 根据机构号查询其下属所有操作人员信息
     *
     * @param branchId 机构id
     * @return 操作人员备选数据集合
     */
    List<Operator> selectOperatorsByBranchId(String branchId);

    /**
     * 根据mac查询监控外设详情信息
     *
     * @param deviceMac mac地址
     * @return 外设详情信息集合
     */
    List<PeripheralStatus> selectPeripheralDetails(String deviceMac);

    /**
     * 查询设备类型的数量
     *
     * @param branchId 机构id
     * @param flag 标示
     * @return 设备类型的数量
     */
    DeviceClassCount selectBranchSummaryInfo(String branchId, String flag);

    /**
     * 查询设备详细信息
     *
     * @param branchId    机构编号
     * @param devClassKey 设备类型主键
     * @return 机具外设的详细信息
     */
    List<DeviceDetail> selectBranchDetailInfo(String branchId, String devClassKey);

}
