package com.dcfs.smartaibank.manager.monitor.web.service;

import com.dcfs.smartaibank.manager.monitor.web.domian.BranchWaitTimeReport;
import com.dcfs.smartaibank.manager.monitor.web.domian.QueueTime;
import java.util.List;


/**
 * @author sunbba
 * @date 20200327
 * @since
 */
public interface HistoryReportBojjService {
    /**
     * 查询支行视角指定时间区间客户等候时间报表
     *
     * @param branchId            机构编号
     * @param startTime         查询开始时间
     * @param endTime       查询结束时间
     * @return HistoryReportInfo 报表统计信息
     */
    List<BranchWaitTimeReport> getBranchWaitTimeReport(String branchId, String startTime, String endTime);

    /**
     * 查询所有机构每月的叫号数量及平均排队等待时长
     * @param branchId  机构号
     * @param startDate
     * @return 监控交易处理列表详情集合
     */
    List<QueueTime> getQueueWaitTime(String branchId, String startDate);

}
