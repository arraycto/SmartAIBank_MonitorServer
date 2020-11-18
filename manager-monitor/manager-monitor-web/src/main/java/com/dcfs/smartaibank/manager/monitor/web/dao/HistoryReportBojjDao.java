package com.dcfs.smartaibank.manager.monitor.web.dao;


import com.dcfs.smartaibank.manager.monitor.web.domian.BranchWaitTimeReport;
import com.dcfs.smartaibank.manager.monitor.web.domian.QueueTime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 支行视角客户等候时间报表查询
 *
 * @author sunbba
 * @date 20200327
 */
@Mapper
public interface HistoryReportBojjDao {

    /**
     * 1.支行视角客户等候时间报表查询
     *
     * @param branchId                机构号
     * @param startTime              查询开始时间
     * @param endTime                查询结束时间
     * @return BranchWaitTimeReport  支行视角客户等候时间报表信息
     */
    List<BranchWaitTimeReport> queryBranchWaitTime(@Param("branchId") String branchId,
                                                   @Param("startTime") String startTime,
                                                   @Param("endTime") String endTime);


    /**
     * 2.客户等候时间报表查询
     * @param branchId 机构号
     * @param startDate 起始日期
     * @return QueueTime
     */
    List<QueueTime> selectQueueWait(@Param("branchId") String branchId,
                                    @Param("startDate") String startDate);


}
