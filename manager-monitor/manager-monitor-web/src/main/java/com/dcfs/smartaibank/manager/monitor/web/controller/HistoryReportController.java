package com.dcfs.smartaibank.manager.monitor.web.controller;
import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.manager.monitor.web.constance.Constance;
import com.dcfs.smartaibank.manager.monitor.web.domian.HistoryGraphInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.EquipmentConsumablesInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.ManagersFaultHandlingSpeedGraphicGatherInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.TradeTimeBranchReportInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.ClientFlowReportInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.TradeTimeCounterClerkReportInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.ServiceTimeReportInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.WaittingTimebranchReportInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.QuWaitTimeInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.TradeTimeLeaderInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.TradeTimeZhInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.SatisfactionOrgReportInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.SatisfactionTellerInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.HistoryTransQualityInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.ManagersFaultHandlingSpeedInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.HistoryResultList;
import com.dcfs.smartaibank.manager.monitor.web.domian.ResultWrongAccount;
import com.dcfs.smartaibank.manager.monitor.web.domian.PeripheralFaultRateGatherInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.PeripheralModel;
import com.dcfs.smartaibank.manager.monitor.web.domian.ResultTranQuality;
import com.dcfs.smartaibank.manager.monitor.web.domian.HistoryReportInfo;
import com.dcfs.smartaibank.manager.monitor.web.param.HistoryReportParams;
import com.dcfs.smartaibank.manager.monitor.web.param.EquipmentSuppliesQueryParams;
import com.dcfs.smartaibank.manager.monitor.web.param.PeripheralFaultRateQueryParams;
import com.dcfs.smartaibank.manager.monitor.web.param.SatisfactionTellerParams;
import com.dcfs.smartaibank.manager.monitor.web.param.ManagersFaultHandlingSpeedParams;
import com.dcfs.smartaibank.manager.monitor.web.param.WrongAccountParams;
import com.dcfs.smartaibank.manager.monitor.web.param.HistoryTransQualityParams;
import com.dcfs.smartaibank.manager.monitor.web.param.ClientFlowReportParams;
import com.dcfs.smartaibank.manager.monitor.web.param.SatisfactionOrgReportParams;
import com.dcfs.smartaibank.manager.monitor.web.param.TradeTimeZhParams;
import com.dcfs.smartaibank.manager.monitor.web.param.TradeTimeLeaderParams;
import com.dcfs.smartaibank.manager.monitor.web.param.QuWaitTimeParams;
import com.dcfs.smartaibank.manager.monitor.web.param.WaittingTimebranchParams;
import com.dcfs.smartaibank.manager.monitor.web.param.ServiceTimeParams;
import com.dcfs.smartaibank.manager.monitor.web.param.TradeTimeBranchParams;
import com.dcfs.smartaibank.manager.monitor.web.param.TradetimecounterclerkParams;
import com.dcfs.smartaibank.manager.monitor.web.service.HistoryReportService;
import com.dcfs.smartaibank.manager.monitor.web.util.excelutil.ExportExcelUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author wangtingo
 * @date 2019/7/10 9:48
 * @since
 */
@Api(value = "/api/v1/history/report", description = "历史记录-统计报表")
@RestController
@RequestMapping(value = "/api/v1/history/report")
@Slf4j
public class HistoryReportController {

    @Autowired
    private HistoryReportService historyReportService;


    @ApiOperation(value = "按条件分页查询历史统计信息", notes = "按条件分页查询历史统计信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "historyReportParams", value = "查询参数",
                    dataType = "HistoryReportParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/page/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<HistoryReportInfo> getHistroyReportInfo(@NotNull @PathVariable(value = "branchId", required = true)
                                                                    String branchId,
                                                            @RequestBody HistoryReportParams historyReportParams) {
        List<HistoryReportInfo> resultList = historyReportService.getHistroyReportInfo(branchId, historyReportParams);
        return new PageInfo<>(resultList);
    }

    @ApiOperation(value = "查询历史统计报表信息导出（机具开机情况、机具故障率、网络通讯故障率）",
            notes = "查询历史统计报表信息导出（机具开机情况、机具故障率、网络通讯故障率）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "historyReportParams", value = "查询参数",
                    dataType = "HistoryReportParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/excel/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void exportHistroyReportInfo(@NotNull @PathVariable(value = "branchId", required = true)
                                                String branchId,
                                        @RequestBody HistoryReportParams historyReportParams,
                                        HttpServletResponse response) {
        String reportTitle;
        if (Constance.NORMAL.equals(historyReportParams.getDataType())) {
            reportTitle =
                    historyReportService.getOrgReportTitle(branchId).append(Constance.DEVICE_START_INFO_TITLE)
                            .toString();
        } else if (Constance.TOOL.equals(historyReportParams.getDataType())) {
            reportTitle =
                    historyReportService.getOrgReportTitle(branchId).append(Constance.EQUIPMENT_FAILURE_TITLE)
                            .toString();
        } else {
            reportTitle =
                    historyReportService.getOrgReportTitle(branchId).append(Constance.NETWORK_FAILURE_RATE_TITLE)
                            .toString();
        }

        try (InputStream wb = historyReportService.createExcelWithHistroyReportInfo(
                branchId, historyReportParams, reportTitle)) {
            ExportExcelUtil.export(wb, response, reportTitle);
        } catch (Exception e) {
            log.error("历史统计信息报表导出异常", e);
            throw new BusinessException("历史统计信息报表导出异常", e);
        }
    }

    @ApiOperation(value = "历史统计图表信息", notes = "历史统计图表信息（设备故障率、网络故障率、机具开机率）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "historyReportParams", value = "查询参数",
                    dataType = "HistoryReportParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/graphic/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HistoryGraphInfo getHistroyGraphic(@NotNull @PathVariable(value = "branchId", required = true)
                                                      String branchId,
                                              @RequestBody HistoryReportParams historyReportParams) {
        return historyReportService.getHistroyGraphic(branchId, historyReportParams);
    }

    @ApiOperation(value = "外设故障图表数据", notes = "外设故障图表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "historyReportParams", value = "查询参数",
                    dataType = "PeripheralFaultRateQueryParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/graphic/peripheral/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HistoryGraphInfo> getPeripheralGraphic(
            @NotNull @PathVariable(value = "branchId", required = true)
                    String branchId,
            @RequestBody PeripheralFaultRateQueryParams historyReportParams) {
        return historyReportService.getPeripheralGraphic(branchId, historyReportParams);
    }

    @ApiOperation(value = "外设故障情况报表统计-获取表头的外设模块信息",
            notes = "外设故障情况报表统计-获取表头的外设模块信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "devClassKey", value = "机具类型",
                    dataType = "string", paramType = "path", required = true)
    })
    @PostMapping(value = "/peripheral/fault-rate/header/{branchId}/{devClassKey}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PeripheralModel> getPeripheralFaultRateHeader(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @NotNull @PathVariable(value = "devClassKey", required = true) String devClassKey) {
        return historyReportService.getPeripheralFaultRateHeader(branchId, devClassKey);
    }

    @ApiOperation(value = "外设故障情况报表统计-终端外设故障汇总统计结果",
            notes = "外设故障情况报表统计-终端外设故障汇总统计结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "peripheralParams", value = "查询参数",
                    dataType = "PeripheralFaultRateQueryParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/peripheral/fault-rate/gather/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<PeripheralFaultRateGatherInfo> getPeripheralFaultRateGather(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @RequestBody PeripheralFaultRateQueryParams peripheralParams) {
        List<PeripheralFaultRateGatherInfo> peripheralFaultRateGather =
                historyReportService.getPeripheralFaultRateGather(branchId, peripheralParams, true);
        return new PageInfo<>(peripheralFaultRateGather);
    }

    @ApiOperation(value = "外设故障详情报表统计-包括列表和表头及对应的表头信息",
            notes = "外设故障详情报表统计-包括列表和表头及对应的表头信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "peripheralParams", value = "查询参数",
                    dataType = "PeripheralFaultRateQueryParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/peripheral/detail/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<HistoryResultList> getPeripheralDetail(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @RequestBody PeripheralFaultRateQueryParams peripheralParams) {
        List<HistoryResultList> resultLists =
                historyReportService.getHistoryReportListAndPheral(branchId, peripheralParams, !Constance.IS_EXPORT);
        return new PageInfo<>(resultLists);
    }

    @ApiOperation(value = "外设故障--详情报表统计 列表信息导出",
            notes = "外设故障--详情报表统计 列表信息导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "peripheralParams", value = "查询参数",
                    dataType = "PeripheralFaultRateQueryParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/export/peripheral/detail/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void exportPeripheralDetail(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @RequestBody PeripheralFaultRateQueryParams peripheralParams,
            HttpServletResponse response) {
        String reportTitle = historyReportService.getOrgReportTitle(branchId)
                .append(Constance.PERIPHERAL_DETAILS_TITLE).toString();
        try (
                InputStream inputStream = historyReportService.createExcelWithPeripheral(
                        branchId, peripheralParams, reportTitle)
        ) {
            ExportExcelUtil.export(inputStream, response, reportTitle);
        } catch (IOException e) {
            log.error("终端外设故障详情报表统计导出异常", e);
            throw new BusinessException("终端外设故障汇总报表统计导出异常", e);
        }
    }

    @ApiOperation(value = "设备耗材报表统计", notes = "设备耗材报表统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "queryParams", value = "查询参数",
                    dataType = "EquipmentSuppliesQueryParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/equipment/supplies/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<EquipmentConsumablesInfo> getEquipmentSupplies(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @RequestBody EquipmentSuppliesQueryParams queryParams) {
        List<EquipmentConsumablesInfo> resultList =
                historyReportService.getEquipmentSupplies(branchId, queryParams, true);
        return new PageInfo<>(resultList);
    }

    @ApiOperation(value = "设备管理人员故障处理速度考核",
            notes = "设备管理人员故障处理速度考核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "queryParams", value = "查询参数",
                    dataType = "ManagersFaultHandlingSpeedParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/managers/fault-handling-speed/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<ManagersFaultHandlingSpeedInfo> getManagersFaultHandlingSpeedInfo(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @RequestBody ManagersFaultHandlingSpeedParams queryParams) {
        return new PageInfo<>(historyReportService.getManagersFaultHandlingSpeedInfo(branchId, queryParams, true));
    }

    @ApiOperation(value = "设备管理人员故障处理速度考核图表信息查询",
            notes = "设备管理人员故障处理速度考核图表信息查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "queryParams", value = "查询参数",
                    dataType = "ManagersFaultHandlingSpeedParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/managers/fault-handling-speed/graphic/{branchId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ManagersFaultHandlingSpeedGraphicGatherInfo getManagersFaultHandlingSpeedGraphicInfo(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @RequestBody ManagersFaultHandlingSpeedParams queryParams) {
        return historyReportService.getManagersFaultHandlingSpeedGraphicInfo(branchId, queryParams);
    }

    @ApiOperation(value = "设备耗材报表统计-图表信息查询", notes = "设备耗材报表统计-图表信息查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "queryParams", value = "查询参数",
                    dataType = "EquipmentSuppliesQueryParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/equipment/supplies/graphic/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EquipmentConsumablesInfo> getEquipmentSuppliesGraphicInfo(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @RequestBody EquipmentSuppliesQueryParams queryParams) {
        return historyReportService.getEquipmentSuppliesGraphicInfo(branchId, queryParams);
    }


    @ApiOperation(value = "设备管理人员故障处理速度考核Excel报表导出",
            notes = "设备管理人员故障处理速度考核Excel报表导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "queryParams", value = "查询参数",
                    dataType = "ManagersFaultHandlingSpeedParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/excel/managers/fault-handling-speed/{branchId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void exportManagersFaultHandlingSpeedInfo(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @RequestBody ManagersFaultHandlingSpeedParams queryParams,
            HttpServletResponse response) {
        String reportTitle;
        if (queryParams.getDataType() == Constance.RESOLVE_TIME) {
            reportTitle =
                    historyReportService.getOrgReportTitle(branchId).append(Constance.RESOLVE_TIME_TITLE).toString();
        } else {
            reportTitle =
                    historyReportService.getOrgReportTitle(branchId).append(Constance.RESPONSE_TIME_TITLE).toString();
        }
        try (InputStream wb = historyReportService.createExcelWithManagersFaultHandlingSpeedInfo(
                branchId, queryParams, reportTitle)) {
            ExportExcelUtil.export(wb, response, reportTitle);
        } catch (Exception e) {
            log.error("设备管理人员故障处理速度考核报表导出异常", e);
            throw new BusinessException("设备管理人员故障处理速度考核报表导出异常", e);
        }
    }

    @ApiOperation(value = "设备耗材报表统计Excel报表导出", notes = "设备耗材报表统计Excel报表导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "queryParams", value = "查询参数",
                    dataType = "EquipmentSuppliesQueryParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/excel/equipment/supplies/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void exportEquipmentSupplies(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @RequestBody EquipmentSuppliesQueryParams queryParams,
            HttpServletResponse response) {
        String reportTitle = historyReportService.getOrgReportTitle(branchId)
                .append(Constance.EQUIPMENT_SUPPLIES_TITLE).toString();
        try (
                InputStream inputStream = historyReportService.createExcelWithEquipmentSupplies(
                        branchId, queryParams, reportTitle)
        ) {
            ExportExcelUtil.export(inputStream, response, reportTitle);
        } catch (Exception e) {
            log.error("{}导出异常", Constance.EQUIPMENT_SUPPLIES_TITLE, e);
            throw new BusinessException("设备耗材报表导出异常", e);
        }
    }

    @ApiOperation(value = "错账统计报表查询", notes = "错账统计报表查询--包括列表数据和图表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "params", value = "查询参数",
                    dataType = "WrongAccountParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/errorAccount/page/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultWrongAccount getWrongAccountInfo(@PathVariable(value = "branchId", required = true)
                                                          String branchId,
                                                  @RequestBody WrongAccountParams params) {
        return historyReportService.getWrongAccountInfo(branchId, params, !Constance.IS_EXPORT);
    }

    @ApiOperation(value = "错账统计报表导出", notes = "错账统计报表导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "params", value = "查询参数",
                    dataType = "WrongAccountParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/excel/errorAccount/page/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void exportWrongAccountInfo(@PathVariable(value = "branchId", required = true)
                                               String branchId,
                                       @RequestBody WrongAccountParams params,
                                       HttpServletResponse response) {
        String reportTitle = historyReportService.getOrgReportTitle(branchId)
                .append(Constance.WRONG_ACCOUNT_TITLE).toString();
        try (
                InputStream inputStream = historyReportService.exportWrongAccountInfo(
                        branchId, params, reportTitle)
        ) {
            ExportExcelUtil.export(inputStream, response, reportTitle);
        } catch (Exception e) {
            log.error("{}导出异常", Constance.WRONG_ACCOUNT_TITLE, e);
            throw new BusinessException("错账统计报表导出异常", e);
        }
    }

    @ApiOperation(value = "交易质量统计报表查询", notes = "交易质量报表查询--包括列表数据和图表数据")
    @ApiImplicitParam(name = "params", value = "查询参数",
            dataType = "HistoryTransQualityParams", paramType = "body", required = false)
    @PostMapping(value = "/tranQuality/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultTranQuality getTransQualityInfo(@RequestBody HistoryTransQualityParams params) {
        return historyReportService.getHistoryTransQualityInfoAndGraphic(params, !Constance.IS_EXPORT);
    }

    @ApiOperation(value = "交易质量统计报表导出", notes = "交易质量报表导出")
    @ApiImplicitParam(name = "params", value = "查询参数",
            dataType = "HistoryTransQualityParams", paramType = "body", required = false)
    @PostMapping(value = "/excel/tranQuality/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public void exportTransQualityInfo(@RequestBody HistoryTransQualityParams params,
                                       HttpServletResponse response) {
        String reportTitle = historyReportService.getOrgReportTitle(params.getBranchNo())
                .append(Constance.TRANS_QUALITY_TITLE).toString();
        try (
                InputStream inputStream = historyReportService.exportTranQuality(
                        params, reportTitle)
        ) {
            ExportExcelUtil.export(inputStream, response, reportTitle);
        } catch (Exception e) {
            log.error("{}导出异常", Constance.TRANS_QUALITY_TITLE, e);
            throw new BusinessException("交易质量统计报表导出异常", e);
        }
    }

    @ApiOperation(value = "交易质量统计报表查询--网点详情", notes = "交易质量报表查询--网点详情（列表数据）")
    @ApiImplicitParam(name = "params", value = "查询参数",
            dataType = "HistoryTransQualityParams", paramType = "body", required = false)
    @PostMapping(value = "/tranQuality/detail/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<HistoryTransQualityInfo> getTransQualityInfoByBranch(
            @RequestBody HistoryTransQualityParams params) {
        return new PageInfo<>(
                historyReportService.getTranQualityDataForListByBranchDetail(params, !Constance.IS_EXPORT));
    }

    @ApiOperation(value = "交易质量统计报表网点详情导出", notes = "交易质量统计报表网点详情导出")
    @ApiImplicitParam(name = "params", value = "查询参数",
            dataType = "HistoryTransQualityParams", paramType = "body", required = false)
    @PostMapping(value = "/excel/tranQuality/detail/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public void exportTransQualityInfoByBranch(@RequestBody HistoryTransQualityParams params,
                                               HttpServletResponse response) {
        String reportTitle = historyReportService.getOrgReportTitle(params.getBranchNo())
                .append(Constance.TRANS_QUALITY_DETAIL_TITLE).toString();
        try (
                InputStream inputStream = historyReportService.exportTranQualityDetail(
                        params, reportTitle)
        ) {
            ExportExcelUtil.export(inputStream, response, reportTitle);
        } catch (Exception e) {
            log.error("{}导出异常", Constance.TRANS_QUALITY_DETAIL_TITLE, e);
            throw new BusinessException("设备交易质量统计报表网点详情导出异常", e);
        }
    }

    @ApiOperation(value = "外设故障情况报表导出-终端外设故障汇总统计结果",
            notes = "外设故障情况报表导出-终端外设故障汇总统计结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "peripheralParams", value = "查询参数",
                    dataType = "PeripheralFaultRateQueryParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/excel/peripheral/fault-rate/gather/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void exportPeripheralFaultRateGather(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @RequestBody PeripheralFaultRateQueryParams peripheralParams,
            HttpServletResponse response) {
        String reportTitle = historyReportService.getOrgReportTitle(branchId)
                .append(Constance.PERIPHERAL_GATHER_TITLE).toString();
        try (
                InputStream inputStream = historyReportService.createExcelWithPeripheralFaultRateGather(
                        branchId, peripheralParams, reportTitle)
        ) {
            ExportExcelUtil.export(inputStream, response, reportTitle);
        } catch (Exception e) {
            log.error("终端外设故障汇总报表统计导出异常", e);
            throw new BusinessException("终端外设故障汇总报表统计导出异常", e);
        }
    }

    @ApiOperation(value = "柜员满意度评价数据报表查询", notes = "柜员满意度评价数据报表查询--包括列表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "params", value = "查询参数",
                    dataType = "SatisfactionTellerParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/satisfactionTeller/page/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)

    public PageInfo<SatisfactionTellerInfo> getSatisfactionTellerInfo(
            @NotNull @PathVariable(value = "branchId", required = true)
                     String branchId,
            @RequestBody SatisfactionTellerParams satisfactionTellerParams) {
        List<SatisfactionTellerInfo> resultList =
                historyReportService.getSatisfactionTellerInfo(branchId, satisfactionTellerParams);
        return new PageInfo<>(resultList);
    }


    @ApiOperation(value = "柜员满意度评价报表导出", notes = "柜员满意度评价报表导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "params", value = "查询参数",
                    dataType = "SatisfactionTellerParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/excel/satisfactionTeller/page/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void exportSatisfactionTellerInfo(
            @PathVariable(value = "branchId", required = true)
                                               String branchId,
                                       @RequestBody SatisfactionTellerParams params,
                                       HttpServletResponse response) {
        String reportTitle =
                historyReportService.getOrgReportTitle(branchId).append(Constance.SATISFACTION_TELLER_TITLE)
                .toString();
        try (InputStream wb = historyReportService.exportSatisfactionTellerParamsInfo(
                branchId, params, reportTitle)) {
            ExportExcelUtil.export(wb, response, reportTitle);
        } catch (Exception e) {
            log.error("柜员满意度评价报表导出异常", e);
            throw new BusinessException("柜员满意度评价报表导出异常", e);
        }
    }

    @ApiOperation(value = "总行视角各机构满意度评价数据报表查询", notes = "总行视角各机构满意度评价数据报表查询--包括列表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "params", value = "查询参数",
                    dataType = "SatisfactionTellerParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/satisfactionOrgReport/page/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)

    public PageInfo<SatisfactionOrgReportInfo> getsatisfactionOrgReportInfo(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @RequestBody SatisfactionOrgReportParams satisfactionOrgReportParams) {
        List<SatisfactionOrgReportInfo>
                resultList = historyReportService.getsatisfactionOrgReportInfo(branchId, satisfactionOrgReportParams);
        return new PageInfo<>(resultList);
    }

    @ApiOperation(value = "总行视角各机构满意度评价报表导出", notes = "总行视角各机构满意度评价报表导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "params", value = "查询参数",
                    dataType = "SatisfactionTellerParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/excel/satisfactionOrgReport/page/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void exportSatisfactionOrgReportInfo(@PathVariable(value = "branchId", required = true)
                                                     String branchId,
                                             @RequestBody SatisfactionOrgReportParams params,
                                             HttpServletResponse response) {
        String reportTitle = historyReportService.getOrgReportTitle(branchId).append(Constance.SATISFACTION_ORG_TITLE)
                .toString();
        try (InputStream wb = historyReportService.exportSatisfactionOrgReportInfo(
                branchId, params, reportTitle)) {
            ExportExcelUtil.export(wb, response, reportTitle);
        } catch (Exception e) {
            log.error("总行视角各机构满意度评价报表导出异常", e);
            throw new BusinessException("总行视角各机构满意度评价报表导出异常", e);
        }
    }

    @ApiOperation(value = "总行视角机构交易处理时长报表(高频交易前100)查询", notes = "总行视角机构交易处理时长报表(高频交易前100)查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "params", value = "查询参数",
                    dataType = "TradeTimeZhParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/zhview/tradetime/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)

    public PageInfo<TradeTimeZhInfo> getradetimezhInfo(@NotNull @PathVariable(value = "branchId", required = true)
                                                               String branchId,
                                                       @RequestBody TradeTimeZhParams tradeTimeZhParams) {
        List<TradeTimeZhInfo> resultList = historyReportService.getTradeTimeZhInfo(branchId, tradeTimeZhParams);
        return new PageInfo<>(resultList);
    }

    @ApiOperation(value = "总行视角机构交易处理时长报表(高频交易前100)导出", notes = "总行视角机构交易处理时长报表(高频交易前100)导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "params", value = "查询参数",
                    dataType = "TradeTimeZhParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/excel/zhview/tradetimes/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void exportTradeTimeZHInfo(@PathVariable(value = "branchId", required = true)
                                              String branchId,
                                      @RequestBody TradeTimeZhParams params,
                                      HttpServletResponse response) {
        String reportTitle = historyReportService.getOrgReportTitle(branchId).append(Constance.ZH_VIEW_INFO_TITLE)
                .toString();
        try (InputStream wb = historyReportService.exportTradeTimeZHParamsInfo(
                branchId, params, reportTitle)) {
            ExportExcelUtil.export(wb, response, reportTitle);
        } catch (Exception e) {
            log.error("总行视角机构交易处理时长报表导出异常", e);
            throw new BusinessException("总行视角机构交易处理时长报表导出异常", e);
        }
    }

    @ApiOperation(value = "支行主管视角柜员交易代码处理时长（展示前100高频交易）查询", notes = "支行主管视角柜员交易代码处理时长（展示前100高频交易）查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "params", value = "查询参数",
                    dataType = "TradeTimeLeaderParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/leaderview/tradetime/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)

    public PageInfo<TradeTimeLeaderInfo> getradetimeLeaderInfo(
            @NotNull @PathVariable(value = "branchId", required = true)
             String branchId,
            @RequestBody TradeTimeLeaderParams tradeTimeLeaderParams) {
        List<TradeTimeLeaderInfo> resultList =
                historyReportService.getTradeTimeLeaderInfo(branchId, tradeTimeLeaderParams);
        return new PageInfo<>(resultList);
    }

    @ApiOperation(value = "支行主管视角柜员交易代码处理时长（展示前100高频交易）导出", notes = "支行主管视角柜员交易代码处理时长（展示前100高频交易）导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "params", value = "查询参数",
                    dataType = "TradeTimeLeaderParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/excel/leaderview/tradetime/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void exportTradeTimeLeaderInfo(@PathVariable(value = "branchId", required = true)
                                                  String branchId,
                                          @RequestBody TradeTimeLeaderParams tradeTimeLeaderParams,
                                          HttpServletResponse response) {
        String reportTitle = historyReportService.getOrgReportTitle(branchId).append(Constance.LEADER_VIEW_INFO_TITLE)
                .toString();
        try (InputStream wb = historyReportService.exportTradeTimeLeaderParamsInfo(
                branchId, tradeTimeLeaderParams, reportTitle)) {
            ExportExcelUtil.export(wb, response, reportTitle);
        } catch (Exception e) {
            log.error("支行主管视角柜员报表导出异常", e);
            throw new BusinessException("支行主管视角柜员报表导出异常", e);
        }
    }

    @ApiOperation(value = "总行视角等候时间数据报表查询", notes = "总行视角等候时间数据报表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "params", value = "查询参数",
                    dataType = "TradeTimeLeaderParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/coreQu/waittime/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)

    public PageInfo<QuWaitTimeInfo> getquWaitTimeInfo(@NotNull @PathVariable(value = "branchId", required = true)
                                                              String branchId,
                                                      @RequestBody QuWaitTimeParams quWaitTimeParams) {
        List<QuWaitTimeInfo> resultList = historyReportService.getQuWaitTimeInfo(branchId, quWaitTimeParams);
        return new PageInfo<>(resultList);
    }

    @ApiOperation(value = "总行视角等候时间数据报表导出", notes = "总行视角等候时间数据报表导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "params", value = "查询参数",
                    dataType = "QuWaitTimeParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/excel/coreBankQu/waittime/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void exportQuWaitTimeParamsInfo(@PathVariable(value = "branchId", required = true)
                                                   String branchId,
                                           @RequestBody QuWaitTimeParams quWaitTimeParams,
                                           HttpServletResponse response) {
        String reportTitle = historyReportService.getOrgReportTitle(branchId).append(Constance.QU_WAITTIMEINFO_TITLE)
                .toString();
        try (InputStream wb = historyReportService.exportQuWaitTimeParamsInfo(
                branchId, quWaitTimeParams, reportTitle)) {
            ExportExcelUtil.export(wb, response, reportTitle);
        } catch (Exception e) {
            log.error("总行视角等候时间数据导出异常", e);
            throw new BusinessException("总行视角等候时间数据导出异常", e);
        }
    }



    @ApiOperation(value = "支行视角客户等候时间数据报表查询",
            notes = "支行视角客户等候时间数据报表查询--包括列表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "params", value = "查询参数",
                    dataType = "WaittingTimebranchParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/branch/wattingtime/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)

    public PageInfo<WaittingTimebranchReportInfo> getwaittingTimebranchReport(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @RequestBody WaittingTimebranchParams waittingTimebranchParams) {
        List<WaittingTimebranchReportInfo> resultList =
                historyReportService.getWaittingTimebranchReportInfo(branchId, waittingTimebranchParams);
        return new PageInfo<>(resultList);
    }

    @ApiOperation(value = "支行视角客户等候时间数据报表导出",
            notes = "支行视角客户等候时间数据报表报表导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "params", value = "查询参数",
                    dataType = "WaittingTimebranchParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/excel/branch/waittingtime/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void exportwaittingTimebranch(
            @PathVariable(value = "branchId", required = true) String branchId,
            @RequestBody WaittingTimebranchParams waittingTimebranchParams,
            HttpServletResponse response) {
        String reportTitle = historyReportService.getOrgReportTitle(branchId)
                .append(Constance.WAITTINGTIME_BRANCH_TITLE)
                .toString();
        try (InputStream wb = historyReportService.exportwaittingTimebranc(
                branchId, waittingTimebranchParams, reportTitle)) {
            ExportExcelUtil.export(wb, response, reportTitle);
        } catch (Exception e) {
            log.error("支行视角客户等候时间数据报表导出异常", e);
            throw new BusinessException("支行视角客户等候时间数据报表导出异常", e);
        }
    }


    @ApiOperation(value = "支行视角柜员服务客户时间统计查询",
            notes = "支行视角柜员服务客户时间统计查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "params", value = "查询参数",
                    dataType = "ServiceTimeParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/time/service/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)

    public PageInfo<ServiceTimeReportInfo> getserviceTimeReport(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @RequestBody ServiceTimeParams serviceTimeParams) {
        List<ServiceTimeReportInfo> resultList =
                historyReportService.getServiceTimeReportInfo(branchId, serviceTimeParams);
        return new PageInfo<>(resultList);
    }

    @ApiOperation(value = "支行视角柜员服务客户时间统计导出",
            notes = "支行视角柜员服务客户时间统计导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "params", value = "查询参数",
                    dataType = "ServiceTimeParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/excel/time/service/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void exportserviceTimeReport(
            @PathVariable(value = "branchId", required = true) String branchId,
            @RequestBody ServiceTimeParams serviceTimeParams,
            HttpServletResponse response) {
        String reportTitle = historyReportService.getOrgReportTitle(branchId)
                .append(Constance.TIME_SERVICE_TITLE)
                .toString();
        try (InputStream wb = historyReportService.exportserviceTime(
                branchId, serviceTimeParams, reportTitle)) {
            ExportExcelUtil.export(wb, response, reportTitle);
        } catch (Exception e) {
            log.error("支行视角柜员服务客户时间统计导出异常", e);
            throw new BusinessException("支行视角柜员服务客户时间统计导出异常", e);
        }
    }


    @ApiOperation(value = "支行柜员视角柜员交易代码处理时长查询",
            notes = "支行柜员视角柜员交易代码处理时长查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "params", value = "查询参数",
                    dataType = "TradetimecounterclerkParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/counterclerk/tradetime/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)

    public PageInfo<TradeTimeCounterClerkReportInfo> gettradeTimeCounterClerkReport(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @RequestBody TradetimecounterclerkParams tradetimecounterclerkParams) {
        List<TradeTimeCounterClerkReportInfo> resultList =
                historyReportService.getTradeTimeCounterClerkReportInfo(branchId, tradetimecounterclerkParams);
        return new PageInfo<>(resultList);
    }

    @ApiOperation(value = "支行柜员视角柜员交易代码处理时长导出",
            notes = "支行柜员视角柜员交易代码处理时长导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "params", value = "查询参数",
                    dataType = "TradetimecounterclerkParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/excel/counterclerk/tradetime/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void exporttradetimecounterclerk(
            @PathVariable(value = "branchId", required = true) String branchId,
            @RequestBody TradetimecounterclerkParams tradetimecounterclerkParams,
            HttpServletResponse response) {
        String reportTitle = historyReportService.getOrgReportTitle(branchId)
                .append(Constance.LEADER_VIEW_DEAL_TITLE)
                .toString();
        try (InputStream wb = historyReportService.exporttradetimecounterclerk(
                branchId, tradetimecounterclerkParams, reportTitle)) {
            ExportExcelUtil.export(wb, response, reportTitle);
        } catch (Exception e) {
            log.error("支行柜员视角柜员交易代码处理时长导出异常", e);
            throw new BusinessException("支行柜员视角柜员交易代码处理时长导出异常", e);
        }
    }


    @ApiOperation(value = "支行网点视角交易代码时长数据查询",
            notes = "支行网点视角交易代码时长数据查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "params", value = "查询参数",
                    dataType = "TradeTimeBranchParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/branch/tradetime/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)

    public PageInfo<TradeTimeBranchReportInfo> gettradeTimeBranchReport(
            @NotNull @PathVariable(value = "branchId", required = true) String branchId,
            @RequestBody TradeTimeBranchParams tradeTimeBranchParams) {
        List<TradeTimeBranchReportInfo> resultList =
                historyReportService.getTradeTimeBranchReportInfo(branchId, tradeTimeBranchParams);
        return new PageInfo<>(resultList);
    }

    @ApiOperation(value = "支行网点视角交易代码时长数据导出",
            notes = "支行网点视角交易代码时长数据导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "params", value = "查询参数",
                    dataType = "TradeTimeBranchParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/excel/Branch/tradetime/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void exporttradeTimeBranchReport(
            @PathVariable(value = "branchId", required = true) String branchId,
            @RequestBody TradeTimeBranchParams tradeTimeBranchParams,
            HttpServletResponse response) {
        String reportTitle = historyReportService.getOrgReportTitle(branchId)
                .append(Constance.TRADETIME_BRANCH_TITLE)
                .toString();
        try (InputStream wb = historyReportService.exporttradeTimeBranch(
                branchId, tradeTimeBranchParams, reportTitle)) {
            ExportExcelUtil.export(wb, response, reportTitle);
        } catch (Exception e) {
            log.error("支行网点视角交易代码时长数据导出异常", e);
            throw new BusinessException("支行网点视角交易代码时长数据导出异常", e);
        }
    }


    @ApiOperation(value = "支行视角各时段客户流量曲线", notes = "支行视角各时段客户流量曲线")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "机构编号",
                    dataType = "string", paramType = "path", required = true),
            @ApiImplicitParam(name = "clientFlowReportParams", value = "查询参数",
                    dataType = "ClientFlowReportParams", paramType = "body", required = false)
    })
    @PostMapping(value = "/flowgraphic/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ClientFlowReportInfo getHistroyGraphic(@NotNull @PathVariable(value = "branchId", required = true)
                                                      String branchId,
                                                  @RequestBody ClientFlowReportParams clientFlowReportParams) {
        return historyReportService.getClientFlowGraphic(branchId, clientFlowReportParams);
    }

}
