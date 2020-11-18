package com.dcfs.smartaibank.manager.monitor.web.controller;


import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmMaintainer;
import com.dcfs.smartaibank.manager.monitor.web.param.AlarmParams;
import com.dcfs.smartaibank.manager.monitor.web.param.AlarmRepairInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmSimpleResult;
import com.dcfs.smartaibank.manager.monitor.web.param.AlarmWorkInfo;
import com.dcfs.smartaibank.manager.monitor.web.service.AlarmDeviceService;
import com.dcfs.smartaibank.springboot.core.validation.group.InsertOrUpdate;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tanchen1
 * @author wangtingo
 * @date 2019/6/20 9:48
 * @since
 */
@Api(value = "/api/v1/alarm/device", description = "预警设备信息")
@RestController
@RequestMapping(value = "/api/v1/alarm/device")
public class AlarmDeviceController {

    @Autowired
    private AlarmDeviceService alarmService;

    @ApiOperation(value = "按条件分页查询预警设备信息", notes = "按条件分页查询预警设备信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryType", value = "查询类型(sum-全部,user-我处理的,当为user时 body体内的userId为必填)",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "branchNo", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "alarmParams", value = "查询参数",
                    dataType = "AlarmParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/page/{queryType}/{branchNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<AlarmInfo> getAlarmInfo(@PathVariable(value = "queryType", required = true) String queryType,
                                            @PathVariable(value = "branchNo", required = true) String branchNo,
                                            @RequestBody AlarmParams alarmParams) {
        return alarmService.getAlarmInfo(queryType, alarmParams, branchNo);
    }

    @ApiOperation(value = "按条件分页查询预警设备信息", notes = "按条件分页查询预警设备信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryType", value = "查询类型(sum-全部,user-我处理的,当为user时 body体内的userId为必填)",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "branchNo", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "alarmParams", value = "查询参数",
                    dataType = "AlarmParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/page/{queryType}/{branchNo}/{index}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<AlarmInfo> getAlarmInfobyLevel(@PathVariable(value = "queryType", required = true) String queryType,
                                            @PathVariable(value = "branchNo", required = true) String branchNo,
                                             @PathVariable(value = "index") String index,
                                            @RequestBody AlarmParams alarmParams) {
        return alarmService.getAlarmInfobyLevel(queryType, alarmParams, branchNo, index);
    }

    @ApiOperation(value = "查询首页的预警面板信息", notes = "查询首页的预警面板信息")
    @ApiImplicitParam(name = "branchNo", value = "机构编号", dataType = "string",
            paramType = "path", required = true)
    @GetMapping(value = "/simple/{branchNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AlarmSimpleResult getSimpleAlarmInfo(@PathVariable(value = "branchNo", required = true) String branchNo) {
        return alarmService.getSimpleAlarmInfo(branchNo);
    }

    @ApiOperation(value = "查询设备预警未处理个数", notes = "查询设备预警未处理个数")
    @ApiImplicitParam(name = "branchNo", value = "机构编号",
            dataType = "string", paramType = "path", required = true)
    @GetMapping(value = "/statistics/{branchNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer getAlarmDevCount(@PathVariable(value = "branchNo", required = true) String branchNo) {
        return alarmService.getUnDealInfo(branchNo);
    }

    @ApiOperation(value = "开始处理预警", notes = "开始处理预警")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "alarmId", value = "预警流水Id",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "userId", value = "用户Id",
                    dataType = "string", paramType = "path", required = true)
    })
    @PutMapping(value = "/handle/{alarmId}/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void handleAlarm(@PathVariable(value = "alarmId", required = true) String alarmId,
                            @PathVariable(value = "userId", required = true) String userId) {
        alarmService.beginDeal(userId, alarmId);
    }

    @ApiOperation(value = "处理预警-挂起", notes = "处理预警-挂起")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "alarmId", value = "预警流水Id",
                    dataType = "string", paramType = "path", required = true)
    })
    @PutMapping(value = "/hand-up/{alarmId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void handleUp(@PathVariable(value = "alarmId", required = true) String alarmId
    ) {
        alarmService.handUp(alarmId);
    }

    @ApiOperation(value = "预警操作-报修登记", notes = "预警操作-报修登记")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "alarmRepairInfo", value = "新增参数",
                    dataType = "AlarmRepairInfo", paramType = "body", required = false)
    })
    @PostMapping(value = {"/repair"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public void insertRepair(@Validated({InsertOrUpdate.class}) @RequestBody AlarmRepairInfo alarmRepairInfo) {
        alarmService.insertRepair(alarmRepairInfo);
    }

    @ApiOperation(value = "预警操作-工单登记", notes = "预警操作-工单登记")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "alarmWorkInfo", value = "参数实体",
                    dataType = "AlarmWorkInfo", paramType = "body", required = false)
    })
    @PostMapping(value = {"/work-order"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateRepair(@Validated({InsertOrUpdate.class}) @RequestBody AlarmWorkInfo alarmWorkInfo) {
        alarmService.updateRepair(alarmWorkInfo);
    }

    @ApiOperation(value = "工单登记-回显", notes = "工单登记之后回显")
    @ApiImplicitParam(name = "alarmId", value = "预警编号",
            dataType = "string", paramType = "path", required = true)
    @GetMapping(value = "/work-order/detail/{alarmId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AlarmWorkInfo getRepairInfo(@PathVariable(value = "alarmId", required = true) String alarmId) {
        return alarmService.getAlarmInfo(alarmId);
    }

    @ApiOperation(value = "查询维修人员信息", notes = "预警操作-报修-查询维修人员信息")
    @ApiImplicitParam(name = "alarmId", value = "预警编号",
            dataType = "string", paramType = "path", required = true)
    @GetMapping(value = "/maintainer/{alarmId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AlarmMaintainer getMaintainer(@PathVariable(value = "alarmId", required = true) String alarmId) {
        return alarmService.getMaintainer(alarmId);
    }
}
