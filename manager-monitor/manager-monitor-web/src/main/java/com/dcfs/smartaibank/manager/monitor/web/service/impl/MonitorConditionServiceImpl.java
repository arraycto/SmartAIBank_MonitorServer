package com.dcfs.smartaibank.manager.monitor.web.service.impl;

import com.dcfs.smartaibank.manager.monitor.web.dao.HistoryReportDao;
import com.dcfs.smartaibank.manager.monitor.web.dao.MonitorConditionDao;
import com.dcfs.smartaibank.manager.monitor.web.domian.AlterNativeInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.HistoryTranType;
import com.dcfs.smartaibank.manager.monitor.web.domian.MonitorRemoteDevice;
import com.dcfs.smartaibank.manager.monitor.web.service.MonitorConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tanchena
 * @date 2019/8/23 11:25
 */
@Service
public class MonitorConditionServiceImpl implements MonitorConditionService {

    @Autowired
    private MonitorConditionDao dao;

    @Autowired
    private HistoryReportDao historyReportDao;

    @Override
    public List<AlterNativeInfo> queryDevModelInfo(String typeId) {
        return dao.queryDevModelInfo(typeId);
    }

    @Override
    public List<AlterNativeInfo> queryDevTypeInfo() {
        return dao.queryDevTypeInfo();
    }

    @Override
    public List<AlterNativeInfo> queryBranchInfo(String branchNo) {
        return dao.queryBranchInfo(branchNo);
    }

    @Override
    public List<AlterNativeInfo> queryManufacturer(String devModelId, String devId) {
        return dao.queryManufacturer(devModelId, devId);
    }

    @Override
    public List<MonitorRemoteDevice> getDevClass(String deviceId) {
        return dao.getDevClass(deviceId);
    }

    @Override
    public List<AlterNativeInfo> getRecordOrgInfo(String branchNo) {
        return dao.getRecordOrgInfo(branchNo);
    }

    @Override
    public List<AlterNativeInfo> getDeviceInfo(String branchNo, String classKey) {
        return dao.getDeviceInfo(branchNo, classKey);
    }

    @Override
    public List<HistoryTranType> getTransType(String id) {
        return historyReportDao.getTranTypeCondition(id);
    }
}
