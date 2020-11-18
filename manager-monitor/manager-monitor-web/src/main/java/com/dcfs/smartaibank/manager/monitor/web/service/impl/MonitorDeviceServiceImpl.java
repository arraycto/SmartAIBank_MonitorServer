package com.dcfs.smartaibank.manager.monitor.web.service.impl;

import com.dcfs.smartaibank.manager.monitor.web.dao.MonitorDeviceDao;
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
import com.dcfs.smartaibank.manager.monitor.web.service.MonitorDeviceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备运行业务层实现
 *
 * @author wangjzm
 * @data 2019/06/17 09:56
 * @since 1.0.0
 */
@Service
public class MonitorDeviceServiceImpl implements MonitorDeviceService {
    @Autowired
    private MonitorDeviceDao monitorDeviceDao;

    @Override
    public DeviceRunningStatusSum selectEachStatusCountGatherInfo(String branchId,
                                                                  Integer branchStatsType,
                                                                  DeviceRunningQueryParams deviceRunningQueryParams) {
        DeviceRunningStatusSum deviceRunningStatusSum =
                monitorDeviceDao.selectEachStatusCountGatherInfo(branchId, branchStatsType, deviceRunningQueryParams);
        if (deviceRunningStatusSum.getTotalCount() != 0) {
            deviceRunningStatusSum.setUnmonitoredRate(100
                    - deviceRunningStatusSum.getAlarmRate()
                    - deviceRunningStatusSum.getNormalRate()
                    - deviceRunningStatusSum.getCommFailureRate()
                    - deviceRunningStatusSum.getPeripheralFailureRate()
                    - deviceRunningStatusSum.getAppNotStartedRate());
        }
        return deviceRunningStatusSum;
    }


    @Override
    public PageInfo<DeviceDetail> selectDeviceOperationDetails(Integer pageNum,
                                                               Integer pageSize,
                                                               String branchId,
                                                               DeviceDetailsSortRuleEnum sortRule,
                                                               SortOrderEnum sortOrder,
                                                               DeviceRunningQueryParams deviceRunningQueryParams) {
        PageHelper.startPage(pageNum, pageSize);
        List<DeviceDetail> deviceDetails =
                monitorDeviceDao.selectDeviceDetailList(branchId, sortRule, sortOrder, deviceRunningQueryParams);
        return new PageInfo<>(deviceDetails);
    }

    @Override
    public PageInfo<RemoteOperationRecode> selectAllRemoteInfo(Integer pageNum,
                                                               Integer pageSize,
                                                               String branchId,
                                                               RemoteOptCondition remoteOptCondition) {
        PageHelper.startPage(pageNum, pageSize);
        List<RemoteOperationRecode> remoteOperationRecodes =
                monitorDeviceDao.selectAllRemoteInfo(branchId, remoteOptCondition);
        return new PageInfo<>(remoteOperationRecodes);
    }

    @Override
    public List<RemoteOperationRecode> selectRemoteInfo(String deviceMac) {
        return monitorDeviceDao.selectRemoteInfo(deviceMac);
    }

    @Override
    public List<Operator> selectOperatorsByBranchId(String branchId) {
        return monitorDeviceDao.selectOperatorsByBranchId(branchId);
    }

    @Override
    public List<PeripheralStatus> selectPeripheralDetails(String deviceMac) {
        return monitorDeviceDao.selectPeripheralDetails(deviceMac);
    }

    @Override
    public DeviceClassCount selectBranchSummaryInfo(String branchId, String flag) {
        DeviceClassCount devClaCou = monitorDeviceDao.selectBranchSummaryInfo(branchId, flag);
        //求和运算
        devClaCou.setSumCount(devClaCou.getFillerCount()
                + devClaCou.getQueueCount() + devClaCou.getAtmCount()
                + devClaCou.getStmCount() + devClaCou.getBsrCount());
        return devClaCou;
    }

    @Override
    public List<DeviceDetail> selectBranchDetailInfo(String branchId, String devClassKey) {
        return monitorDeviceDao.selectBranchDetailInfo(branchId, devClassKey);
    }

}
