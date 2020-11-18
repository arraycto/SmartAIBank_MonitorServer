package com.dcfs.smartaibank.manager.monitor.web.controller;

import com.dcfs.smartaibank.manager.monitor.web.domian.BranchWaitTimeReport;
import com.dcfs.smartaibank.manager.monitor.web.domian.QueueTime;
import com.dcfs.smartaibank.manager.monitor.web.service.HistoryReportBojjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @author sunbba
 * @date 20200327
 * @since
 */
@Api(value = "/api/v1/history/report/jjBank", description = "九江银行客户化报表")
@RestController
@RequestMapping(value = "/api/v1/history/report/jjBank")
@Slf4j
public class HistoryReportBojjController {

    @Autowired
    private HistoryReportBojjService historyReportBojjService;

    @ApiOperation(value = "支行视角客户等候时间报表", notes = "支行视角指定时间区间客户等候时间报表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "startTime", value = "查询开始时间",
                    dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "endTime", value = "查询结束时间",
                    dataType = "string", paramType = "query", required = true)
    })
    @PostMapping(value = "/page/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BranchWaitTimeReport> getBranchWaitTimeReport(
            @NotNull @PathVariable(value = "branchId") String branchId,
            @RequestParam(value = "startTime", required = true) String startTime,
            @RequestParam(value = "endTime", required = true) String endTime) {
        return historyReportBojjService.getBranchWaitTimeReport(branchId, startTime, endTime);

    }

    @ApiOperation(value = "查询所有机构每月的叫号数量及平均排队等待时长", notes = "查询所有机构每月的叫号数量及平均排队等待时长")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构id",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "startDate",
                    value = "机构数据统计类型 : 1-作为营业机构进行统计,2-作为上级机构进行汇总",
                    dataType = "string", paramType = "path", required = true)
    })
    @GetMapping(value = "/queue/{branchId}/{startDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<QueueTime> selectTransDetail(
            @NotNull @PathVariable(value = "branchId") String branchId,
            @NotNull @PathVariable(value = "startDate") String startDate) {
        return historyReportBojjService.getQueueWaitTime(branchId, startDate);
    }



}
