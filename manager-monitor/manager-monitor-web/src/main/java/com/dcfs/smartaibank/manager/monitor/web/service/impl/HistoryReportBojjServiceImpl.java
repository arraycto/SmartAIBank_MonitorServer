package com.dcfs.smartaibank.manager.monitor.web.service.impl;

import com.dcfs.smartaibank.manager.monitor.web.dao.HistoryReportBojjDao;
import com.dcfs.smartaibank.manager.monitor.web.domian.BranchWaitTimeReport;
import com.dcfs.smartaibank.manager.monitor.web.domian.QueueTime;
import com.dcfs.smartaibank.manager.monitor.web.service.HistoryReportBojjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * @author sunba
 * @date 20200327
 * @since
 */
@Service
public class HistoryReportBojjServiceImpl implements HistoryReportBojjService {

    @Autowired
    private HistoryReportBojjDao historyReportBojjDao;

    @Override
    public List<BranchWaitTimeReport> getBranchWaitTimeReport(String branchId, String startTime, String endTime) {
        return historyReportBojjDao.queryBranchWaitTime(branchId, startTime, endTime);
    }

    @Override
    public List<QueueTime> getQueueWaitTime(String branchId, String startDate) {
        return historyReportBojjDao.selectQueueWait(branchId, startDate);
    }
}
