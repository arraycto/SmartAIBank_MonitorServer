package com.dcfs.smartaibank.manager.monitor.web.controller;

import com.dcfs.smartaibank.manager.monitor.web.domian.BusinessTypeDetailGather;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusyAllCustomerTypeGatherInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusyDetails;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusyWaitTimeDistribution;
import com.dcfs.smartaibank.manager.monitor.web.domian.OrgBusinessHours;
import com.dcfs.smartaibank.manager.monitor.web.domian.TimePhasedBusinessInfo;
import com.dcfs.smartaibank.manager.monitor.web.enums.BusyDetailsSortRuleEnum;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusyGatherInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.BusyOrgRankInfo;
import com.dcfs.smartaibank.manager.monitor.web.enums.BusyRankQueryConditionEnum;
import com.dcfs.smartaibank.manager.monitor.web.enums.BusyStatusEnum;
import com.dcfs.smartaibank.manager.monitor.web.enums.SortOrderEnum;
import com.dcfs.smartaibank.manager.monitor.web.service.MonitorBusyService;
import com.dcfs.smartaibank.springboot.core.validation.annotation.MaxWithConfig;
import com.github.pagehelper.PageInfo;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 繁忙度控制器
 *
 * @author wangjzm
 * @since 1.0.0
 */
@Api(value = "/api/v1/monitor/busy", description = "监控繁忙度模块")
@RestController
@RequestMapping(value = "/api/v1/monitor/busy")
@Validated
@Slf4j
public class MonitorBusyController {
    @Autowired
    private MonitorBusyService monitorBusyService;

    @ApiOperation(value = "根据机构id查询繁忙度汇总信息", notes = "根据机构id查询汇总信息，网点繁忙度状态分布")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "branchStatsType", value = "营业兼管理机构类型时，营业=1，管理=2",
                    dataType = "int", paramType = "path", required = true)
    })
    @GetMapping(value = "/summary/{branchId}/{branchStatsType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BusyGatherInfo selectBusinessGatherInfo(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @NotNull @PathVariable(value = "branchStatsType", required = true) Integer branchStatsType) {
        return monitorBusyService.selectBusyGatherInfo(branchId, branchStatsType);
    }

    @ApiOperation(value = "根据机构id查询繁忙度详情列表信息信息", notes = "根据机构id查询繁忙度详情列表信息信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页记录数",
                    dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "busyStatus",
                    value = "繁忙度状态，可选值：0-未营业，1-空闲，2-正常，3-忙碌，4-饱和，5-全部",
                    dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "_sort",
                    value = "排序条件(1-繁忙度状态,2-等待客户数,3-窗口平均等待客户数排序,"
                            + "4-客户平均等待时间排序，5-客户平均等待时间排序)",
                    dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "sortOrder",
                    value = "排序顺序（1-降序，2-升序)",
                    dataType = "string", paramType = "query", required = false)
    })
    @GetMapping(value = "/details/page/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<BusyDetails> selectBusyDetails(
            @Min(value = 0, message = "{page.pageNum.min}")
            @RequestParam(defaultValue = "1")
                    Integer pageNum,
            @MaxWithConfig(value = "${page.maxPageSize}", message = "{page.pageSize.max}")
            @RequestParam(defaultValue = "${page.defaultPageSize}")
                    Integer pageSize,
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @NotNull @RequestParam(value = "busyStatus", required = true) BusyStatusEnum busyStatus,
            @RequestParam(value = "_sort", required = false) BusyDetailsSortRuleEnum sortRule,
            @RequestParam(value = "sortOrder", required = false) SortOrderEnum sortOrder) {
        return monitorBusyService.selectBusyDetailsByBranchId(pageNum,
                pageSize, branchId, busyStatus, sortRule, sortOrder);
    }

    @ApiOperation(value = "根据等待客户数或客户平均等待时间展示Top和Bottom繁忙度排名信息",
            notes = "根据等待客户数或客户平均等待时间展示Top和Bottom繁忙度排名信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "queryCondition", value = "查询条件：1-等待客户数，2-客户平均的等待时间",
                    dataType = "string", paramType = "query", required = true)
    })
    @GetMapping(value = "/org/rank/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BusyOrgRankInfo getBusynessRankInfo(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @NotNull @RequestParam(value = "queryCondition", required = true)
                    BusyRankQueryConditionEnum queryCondition) {
        return monitorBusyService.getBusynessRankInfo(branchId, queryCondition);
    }

    @ApiOperation(value = "查询营业机构繁忙度排队等待时长（10分钟以下，10-20分钟，20-30分钟，30分钟以上）",
            notes = "查询营业机构繁忙度排队等待时长（10分钟以下，10-20分钟，20-30分钟，30分钟以上）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true)
    })
    @GetMapping(value = "/org/queue/wait-time/distribution/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BusyWaitTimeDistribution getBusyWaitingTimeDistribution(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId) {
        return monitorBusyService.getBusynessWaitingTimeDistribution(branchId);
    }


    @ApiOperation(value = "查询营业机构繁忙度分业务类型繁忙度详情（等待客户数，客户平均等待时间，可办理窗口）",
            notes = "查询营业机构繁忙度分业务类型繁忙度详情（等待客户数，客户平均等待时间，可办理窗口）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true)
    })
    @GetMapping(value = "/org/queue/business-type/distribution/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BusinessTypeDetailGather getBusyBusinessTypeDistribution(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId) {
        return monitorBusyService.getBusyBusinessTypeDistribution(branchId);
    }

    @ApiOperation(value = "查询机构当天的营业时间",
            notes = "查询机构当天的营业时间(机构如果没有设置营业时间，则查询上级机构的营业时间)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true)
    })
    @GetMapping(value = "/org/business-hours/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrgBusinessHours getOrgBusinessHours(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId) {
        return monitorBusyService.getOrgBusinessHours(branchId);
    }

    @ApiOperation(value = "查询监控分时段繁忙度信息（抽号数量、叫号数量）",
            notes = "查询监控分时段繁忙度信息（抽号数量、叫号数量）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "branchStatsType", value = "营业兼管理机构类型时，营业=1，管理=2",
                    dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "busyStartTime", value = "营业开始时间,格式：HH24:MI",
                    dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "busyEndTime", value = "营业结束时间,格式：HH24:MI",
                    dataType = "string", paramType = "query", required = true)
    })
    @GetMapping(value = "/time-phased-business/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TimePhasedBusinessInfo> getPhasedBusyInfoBeforeCurrentTime(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @NotNull @RequestParam(value = "branchStatsType", required = true) Integer branchStatsType,
            @NotNull @RequestParam(value = "busyStartTime", required = true) String busyStartTime,
            @NotNull @RequestParam(value = "busyEndTime", required = true) String busyEndTime) {
        return monitorBusyService.getPhasedBusyInfoBeforeCurrentTime(branchId,
                branchStatsType, busyStartTime, busyEndTime);
    }

    @ApiOperation(value = "分客户类型繁忙度详情",
            notes = "分客户类型繁忙度详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "branchStatsType", value = "营业兼管理机构类型时，营业=1，管理=2",
                    dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "busyStartTime", value = "营业开始时间,格式：HH24:MI",
                    dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "busyEndTime", value = "营业结束时间,格式：HH24:MI",
                    dataType = "string", paramType = "query", required = true)
    })
    @GetMapping(value = "/phased-customer-type/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BusyAllCustomerTypeGatherInfo getPhasedCustomerTypeBusyInfo(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @NotNull @RequestParam(value = "branchStatsType", required = true) Integer branchStatsType,
            @NotNull @RequestParam(value = "busyStartTime", required = true) String busyStartTime,
            @NotNull @RequestParam(value = "busyEndTime", required = true) String busyEndTime) {
        return monitorBusyService.getPhasedCustomerTypeBusyInfo(branchId,
                branchStatsType, busyStartTime, busyEndTime);
    }
}
