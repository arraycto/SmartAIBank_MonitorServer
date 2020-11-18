package com.dcfs.smartaibank.manager.monitor.web.controller;

import com.dcfs.smartaibank.manager.monitor.web.domian.AlterNativeInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.HistoryTranType;
import com.dcfs.smartaibank.manager.monitor.web.domian.MonitorRemoteDevice;
import com.dcfs.smartaibank.manager.monitor.web.service.MonitorConditionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liangchenglong
 * @date 2019/7/9 9:48
 * @since 1.0.0
 */

@Api(value = "/api/v1/monitor/condition", description = "条件查询下拉列表")
@RestController
@RequestMapping(value = "/api/v1/monitor/condition")
public class MonitorConditionController {

    @Autowired
    private MonitorConditionService service;

    @ApiOperation(value = "查询下拉框设备型号备选数据", notes = "查询下拉框设备型号备选数据")
    @ApiImplicitParam(name = "devId", value = "设备类型编号",
            dataType = "string", paramType = "query", required = false)
    @GetMapping(value = "/device/device-model", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlterNativeInfo> getDevModelType(@RequestParam(value = "devId", required = false) String devId) {
        return service.queryDevModelInfo(devId);
    }

    @ApiOperation(value = "查询下拉框设备类型备选数据", notes = "查询下拉框设备类型备选数据")
    @GetMapping(value = "/device/device-type", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlterNativeInfo> getDevType() {
        return service.queryDevTypeInfo();
    }

    @ApiOperation(value = "查询下拉框机构备选数据", notes = "查询下拉框机构备选数据")
    @ApiImplicitParam(name = "branchNo", value = "机构编号",
            dataType = "string", paramType = "path", required = true)
    @GetMapping(value = "/org/{branchNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlterNativeInfo> getOrgInfo(@PathVariable(value = "branchNo", required = true) String branchNo) {
        return service.queryBranchInfo(branchNo);
    }

    @ApiOperation(value = "查询下拉框厂商备选数据", notes = "查询下拉框厂商备选数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "devModelId", value = "设备型号编号",
                    dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "devId", value = "设备类型编号",
                    dataType = "string", paramType = "query", required = false)
    })
    @GetMapping(value = "/device/firm", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlterNativeInfo> getDevModel(
            @RequestParam(value = "devModelId", required = false, defaultValue = "") String devModelId,
            @RequestParam(value = "devId", required = false, defaultValue = "") String devId) {
        return service.queryManufacturer(devModelId, devId);
    }

    @ApiOperation(value = "远程调用-右键模块复位", notes = "远程调用-右键模块复位")
    @ApiImplicitParam(name = "deviceId", value = "设备编号",
            dataType = "string", paramType = "path", required = true)
    @GetMapping(value = "/remote/reset/{deviceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MonitorRemoteDevice> getDevClassByDeviceKey(@PathVariable(value = "deviceId", required = true)
                                                                    String deviceId) {
        return service.getDevClass(deviceId);
    }

    @ApiOperation(value = "交易对账-机构备选数据", notes = "交易对账-机构备选数据")
    @ApiImplicitParam(name = "branchNo", value = "机构编号",
            dataType = "string", paramType = "path", required = true)
    @GetMapping(value = "/record/org/{branchNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlterNativeInfo> getRecordOrgInfo(@PathVariable(value = "branchNo", required = true) String branchNo) {
        return service.getRecordOrgInfo(branchNo);
    }

    @ApiOperation(value = "交易对账-设备备选数据", notes = "交易对账-设备备选数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchNo", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "classKey", value = "机构类型",
                    dataType = "string", paramType = "path", required = true)
    })

    @GetMapping(value = "/record/device/{branchNo}/{classKey}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlterNativeInfo> getDeviceInfo(
            @PathVariable(value = "branchNo", required = true) String branchNo,
            @PathVariable(value = "classKey", required = true) String classKey) {
        return service.getDeviceInfo(branchNo, classKey);
    }

    @ApiOperation(value = "报表交易类型备选数据", notes = "交易质量报表的交易类型备选数据查询")
    @ApiImplicitParam(name = "id", value = "系统编号",
            dataType = "string", paramType = "query", required = false)
    @GetMapping(value = "/trans/quality", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HistoryTranType> getTransType(@RequestParam(value = "id", required = false) String id) {
        return service.getTransType(id);
    }
}
