package com.dcfs.smartaibank.manager.monitor.web.controller;

import com.dcfs.smartaibank.manager.monitor.web.domian.TimePhasedTransCount;
import com.dcfs.smartaibank.manager.monitor.web.domian.TranCount;
import com.dcfs.smartaibank.manager.monitor.web.domian.TranStatusSum;
import com.dcfs.smartaibank.manager.monitor.web.domian.TransDetail;
import com.dcfs.smartaibank.manager.monitor.web.service.MonitorTransService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 监控交易处理控制器
 *
 * @author wangjzm
 * @data 2019/07/01 14:29
 * @since 1.0.0
 */
@Api(value = "/api/v1/monitor/transaction", description = "监控交易处理控制器")
@RestController
@RequestMapping(value = "/api/v1/monitor/transaction")
@Validated
@Slf4j
public class MonitorTransController {
    @Autowired
    private MonitorTransService monitorTransService;

    @ApiOperation(value = "查询当天交易处理汇总信息", notes = "查询当天交易处理汇总信息,包括正常、异常、失败交易笔数及占比")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构id",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "branchStatsType",
                    value = "机构数据统计类型 : 1-作为营业机构进行统计,2-作为上级机构进行汇总",
                    dataType = "int", paramType = "path", required = true)
    })
    @GetMapping(value = "/status/summary/{branchId}/{branchStatsType}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public TranStatusSum selectEachStatusCountGatherInfo(
            @NotNull @PathVariable(value = "branchId") String branchId,
            @NotNull @PathVariable(value = "branchStatsType") Integer branchStatsType) {
        return monitorTransService.selectEachStatusCountGatherInfo(branchId, branchStatsType);
    }

    @ApiOperation(value = "查询当天ATM、STM、BSR交易笔数以及总笔数", notes = "查询当天ATM、STM交易笔数以及总笔数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构id",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "branchStatsType",
                    value = "机构数据统计类型 : 1-作为营业机构进行统计,2-作为上级机构进行汇总",
                    dataType = "int", paramType = "path", required = true)
    })
    @GetMapping(value = "/pens-count/summary/{branchId}/{branchStatsType}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public TranCount selectTransCount(
            @NotNull @PathVariable(value = "branchId") String branchId,
            @NotNull @PathVariable(value = "branchStatsType") Integer branchStatsType) {
        return monitorTransService.selectTransCount(branchId, branchStatsType);
    }

    @ApiOperation(value = "查询监控交易处理前20条记录，按照时间排序", notes = "查询监控交易处理前20条记录，按照时间排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构id",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "branchStatsType",
                    value = "机构数据统计类型 : 1-作为营业机构进行统计,2-作为上级机构进行汇总",
                    dataType = "int", paramType = "path", required = true)
    })
    @GetMapping(value = "/details/{branchId}/{branchStatsType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransDetail> selectTransDetail(
            @NotNull @PathVariable(value = "branchId") String branchId,
            @NotNull @PathVariable(value = "branchStatsType") Integer branchStatsType) {
        return monitorTransService.selectTransDetail(branchId, branchStatsType);
    }

    @ApiOperation(value = "首页监控交易处理分时段交易数量查询",
            notes = "根据机构id和时间（HH24:MI:SS）查询首页监控交易处理分时段交易数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构id",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "currentTime",
                    value = "当前时间,格式：HH24:MI:SS",
                    dataType = "string", paramType = "path", required = true)
    })
    @GetMapping(value = "/time-phased-trans/count/{branchId}/{currentTime}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TimePhasedTransCount> selectTimePhasedTransCount(
            @NotNull @PathVariable(value = "branchId") String branchId,
            @NotNull @PathVariable(value = "currentTime") String currentTime) {
        return monitorTransService.selectTimePhasedTransCount(branchId, currentTime);
    }
}
