package com.dcfs.smartaibank.manager.monitor.web.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 设备运行控制器
 *
 * @author wangjzm
 * @data 2019/06/14 16:45
 * @since 1.0.0
 */
@Api(value = "/api/v1/monitor/device", description = "监控设备运行管理")
@RestController
@RequestMapping(value = "/api/v1/monitor/device")
@Validated
@Slf4j
public class MonitorDeviceController {

    @Autowired
    private MonitorDeviceService monitorDeviceService;

    @ApiOperation(value = "查询设备运行汇总信息", notes = "根据机构号、机构类型、查询条件（设备类型、设备型号、"
            + "设备id、设备厂商、机具状态）统计设备运行汇总信息，汇总信息包括机具总数及分机具运行状态数量及占比。\n"
            + "1.branchStatsType为1代表营业型机构，sql查询的是当前传入机构id的汇总信息\n"
            + "2.branchStatsType为2代表管理型机构，sql查询的是当前传入机构id的下属营业性机构（包含自身）的汇总信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构id",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "branchStatsType",
                    value = "机构数据统计类型 : 1-作为营业机构进行统计,2-作为上级机构进行汇总",
                    dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "deviceRunningQueryParams",
                    value = "设备运行查询条件(包括设备类型、设备型号、设备id、设备厂商)",
                    dataType = "DeviceRunningQueryParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/summary/{branchId}/{branchStatsType}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DeviceRunningStatusSum selectEachStatusCountGatherInfo(
            @NotNull @PathVariable(value = "branchId") String branchId,
            @NotNull @PathVariable(value = "branchStatsType") Integer branchStatsType,
            @RequestBody DeviceRunningQueryParams deviceRunningQueryParams) {
        return monitorDeviceService.
                selectEachStatusCountGatherInfo(branchId, branchStatsType, deviceRunningQueryParams);
    }


    @ApiOperation(value = "按条件分页查询设备运行列表详情信息", notes = "按条件分页查询设备运行列表详情信息。如果传入的"
            + "sort字段为空，默认排序按照机构表中机构顺序（优先）和设备编号进行排序，"
            + "sort(1-设备总状态,2-网络通讯状态,3-应用运行状态,4-外设运行状态)和sortOrder（1-降序，2-升序)字段不为空，"
            + "排序按照两个字段进行组合排序")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "pageSize", value = "每页记录数", dataType = "int", paramType = "query", required = true
            ),
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "branchId", value = "机构id",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "_sort",
                    value = "排序条件(1-设备总状态,2-网络通讯状态,3-应用运行状态,4-外设运行状态)",
                    dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "sortOrder",
                    value = "排序顺序（1-降序，2-升序)",
                    dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "deviceRunningQueryParams",
                    value = "设备运行查询条件(包括设备类型、设备型号、设备id、设备厂商、机具状态)",
                    dataType = "DeviceRunningQueryParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/details/page/{branchId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<DeviceDetail> selectDeviceOperationDetails(
            @Min(value = 0, message = "{page.pageNum.min}")
            @RequestParam(defaultValue = "1", required = true)
                    Integer pageNum,
            @MaxWithConfig(value = "${page.maxPageSize}", message = "{page.pageSize.max}")
            @RequestParam(defaultValue = "${page.defaultPageSize}", required = true)
                    Integer pageSize,
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @RequestParam(value = "_sort", required = false) DeviceDetailsSortRuleEnum sortRule,
            @RequestParam(value = "sortOrder", required = false) SortOrderEnum sortOrder,
            @RequestBody DeviceRunningQueryParams deviceRunningQueryParams) {
        return monitorDeviceService.selectDeviceOperationDetails(pageNum, pageSize, branchId,
                sortRule, sortOrder, deviceRunningQueryParams);
    }

    @ApiOperation(value = "按条件分页查询设备远程操作记录", notes = "按条件分页查询设备远程操作记录，可选填"
            + "查询条件（设备id、设备类型、所属机构、操作人员）")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "pageSize", value = "每页记录数", dataType = "int", paramType = "query", required = true
            ),
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "branchId", value = "机构id",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "remoteOptCondition",
                    value = "设备远程操作记录查询条件（设备id、设备类型、所属机构、操作人员）",
                    dataType = "RemoteOptCondition", paramType = "body", required = false)
    })
    @PostMapping(value = "/remote-operation/page/{branchId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<RemoteOperationRecode> selectAllRemoteInfo(
            @Min(value = 0, message = "{page.pageNum.min}")
            @RequestParam(defaultValue = "1")
                    Integer pageNum,
            @MaxWithConfig(value = "${page.maxPageSize}", message = "{page.pageSize.max}")
            @RequestParam(defaultValue = "${page.defaultPageSize}")
                    Integer pageSize,
            @NotNull @PathVariable(value = "branchId") String branchId,
            @RequestBody RemoteOptCondition remoteOptCondition) {
        return monitorDeviceService.selectAllRemoteInfo(pageNum, pageSize, branchId, remoteOptCondition);
    }

    @ApiOperation(value = "根据mac查询设备远程操作记录", notes = "根据mac查询设备远程操作记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceMac", value = "设备mac",
                    dataType = "string", paramType = "path", required = true)
    })
    @GetMapping(value = "/remote-operation/{deviceMac}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RemoteOperationRecode> selectRemoteInfo(
            @PathVariable(value = "deviceMac") String deviceMac) {
        return monitorDeviceService.selectRemoteInfo(deviceMac);
    }

    @ApiOperation(value = "查询机构各种设备类型的个数", notes = "按照设备类型获取总个数统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构id",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "flag", value = "是否是支行界面",
                    dataType = "string", paramType = "query", required = false)
    })
    @GetMapping(value = "/count/statistics/{branchId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DeviceClassCount selectBranchSummaryInfo(
            @NotNull @PathVariable(value = "branchId") String branchId,
            @RequestParam(value = "flag", required = false) String flag) {
        return monitorDeviceService.selectBranchSummaryInfo(branchId, flag);
    }


    @ApiOperation(value = "查询设备运行面板的数据", notes = "按照机构号查询这个机构下设备的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构id",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "devClassKey", value = "设备类型id",
                    dataType = "string", paramType = "query", required = false),
    })
    @GetMapping(value = "/{branchId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DeviceDetail> selectBranchDetailInfo(@NotNull @PathVariable(value = "branchId", required = true)
                                                             String branchId,
                                                     @RequestParam(value = "devClassKey", required = false)
                                                             String devClassKey) {
        return monitorDeviceService.selectBranchDetailInfo(branchId, devClassKey);
    }

    @ApiOperation(value = "远程操作记录-操作人员", notes = "根据机构号查询其下属所有操作人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构id",
                    dataType = "string", paramType = "path", required = true)
    })
    @GetMapping(value = "/staff/remote-operation/{branchId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Operator> selectOperators(
            @PathVariable(value = "branchId") String branchId) {
        return monitorDeviceService.selectOperatorsByBranchId(branchId);
    }

    @ApiOperation(value = "根据mac查询监控外设详情信息", notes = "根据mac查询监控外设详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceMac", value = "设备mac",
                    dataType = "string", paramType = "path", required = true)
    })
    @GetMapping(value = "/peripheral/details/{deviceMac}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PeripheralStatus> selectPeripheralDetails(
            @PathVariable(value = "deviceMac") String deviceMac) {
        return monitorDeviceService.selectPeripheralDetails(deviceMac);
    }

}
