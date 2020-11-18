package com.dcfs.smartaibank.manager.monitor.web.service;

import com.dcfs.smartaibank.file.excel.ExcelExportException;
import com.dcfs.smartaibank.manager.monitor.web.domian.ClientFlowReportInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.EquipmentConsumablesInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.HistoryGraphInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.HistoryReportInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.HistoryResultList;
import com.dcfs.smartaibank.manager.monitor.web.domian.HistoryTransQualityInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.ManagersFaultHandlingSpeedGraphicGatherInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.ManagersFaultHandlingSpeedInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.PeripheralFaultRateGatherInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.PeripheralModel;
import com.dcfs.smartaibank.manager.monitor.web.domian.QuWaitTimeInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.ResultTranQuality;
import com.dcfs.smartaibank.manager.monitor.web.domian.ResultWrongAccount;
import com.dcfs.smartaibank.manager.monitor.web.domian.SatisfactionOrgReportInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.SatisfactionTellerInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.ServiceTimeReportInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.TradeTimeBranchReportInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.TradeTimeCounterClerkReportInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.TradeTimeLeaderInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.TradeTimeZhInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.WaittingTimebranchReportInfo;
import com.dcfs.smartaibank.manager.monitor.web.param.ClientFlowReportParams;
import com.dcfs.smartaibank.manager.monitor.web.param.EquipmentSuppliesQueryParams;
import com.dcfs.smartaibank.manager.monitor.web.param.HistoryReportParams;
import com.dcfs.smartaibank.manager.monitor.web.param.HistoryTransQualityParams;
import com.dcfs.smartaibank.manager.monitor.web.param.ManagersFaultHandlingSpeedParams;
import com.dcfs.smartaibank.manager.monitor.web.param.PeripheralFaultRateQueryParams;
import com.dcfs.smartaibank.manager.monitor.web.param.QuWaitTimeParams;
import com.dcfs.smartaibank.manager.monitor.web.param.SatisfactionOrgReportParams;
import com.dcfs.smartaibank.manager.monitor.web.param.SatisfactionTellerParams;
import com.dcfs.smartaibank.manager.monitor.web.param.ServiceTimeParams;
import com.dcfs.smartaibank.manager.monitor.web.param.TradeTimeBranchParams;
import com.dcfs.smartaibank.manager.monitor.web.param.TradeTimeLeaderParams;
import com.dcfs.smartaibank.manager.monitor.web.param.TradeTimeZhParams;
import com.dcfs.smartaibank.manager.monitor.web.param.TradetimecounterclerkParams;
import com.dcfs.smartaibank.manager.monitor.web.param.WaittingTimebranchParams;
import com.dcfs.smartaibank.manager.monitor.web.param.WrongAccountParams;


import java.io.InputStream;
import java.util.List;


/**
 * @author wangtingo
 * @date 2019/7/11 10:56
 * @since
 */
public interface HistoryReportService {

    /**
     * 查询历史交易信息
     *
     * @param branchId            机构编号
     * @param historyReportParams 查询条件
     * @return HistoryReportInfo 报表统计信息
     */
    List<HistoryReportInfo> getHistroyReportInfo(String branchId, HistoryReportParams historyReportParams);

    /**
     * 查询图表信息
     *
     * @param branchId            机构编号
     * @param historyReportParams 查询条件
     * @return HistoryReportInfo 报表统计信息
     */
    HistoryGraphInfo getHistroyGraphic(String branchId, HistoryReportParams historyReportParams);

    /**
     * 查询外设的图表信息
     *
     * @param branchId            机构编号
     * @param historyReportParams 查询条件
     * @return PeripheralFaultRateQueryParams 外设的图表信息
     */
    List<HistoryGraphInfo> getPeripheralGraphic(String branchId, PeripheralFaultRateQueryParams historyReportParams);

    /**
     * 外设故障情况报表统计-终端外设故障汇总统计结果
     *
     * @param branchId                       机构编号
     * @param peripheralFaultRateQueryParams 查询条件
     * @param pageOpening                    区分当前查询是列表查询还是导出，影响到是否分页(列表查询-true,导出查询-false)
     * @return 外设故障情况报表统计-终端外设故障汇总统计结果
     */
    List<PeripheralFaultRateGatherInfo> getPeripheralFaultRateGather(
            String branchId,
            PeripheralFaultRateQueryParams peripheralFaultRateQueryParams,
            boolean pageOpening);

    /**
     * 外设故障情况报表统计-获取表头的外设模块信息
     *
     * @param branchId    机构编号
     * @param devClassKey 机具类型
     * @return 表头的外设模块信息集合
     */
    List<PeripheralModel> getPeripheralFaultRateHeader(String branchId, String devClassKey);

    /**
     * 报表 外设故障详情统计
     *
     * @param branchId         机构编号
     * @param peripheralParams 查询参数
     * @param isExport         是否导出
     * @return PageInfo<HistoryResultList>
     */
    List<HistoryResultList> getHistoryReportListAndPheral(
            String branchId,
            PeripheralFaultRateQueryParams peripheralParams,
            Boolean isExport);

    /**
     * 外设故障详情导出
     *
     * @param branchId         机构编号
     * @param peripheralParams 查询参数
     * @param title            题目
     * @return XSSFWorkbook
     */
    InputStream createExcelWithPeripheral(
            String branchId,
            PeripheralFaultRateQueryParams peripheralParams,
            String title);

    /**
     * 设备耗材报表统计
     *
     * @param branchId    机构编号
     * @param queryParams 查询条件
     * @param pageOpening 区分当前查询是列表查询还是导出，影响到是否分页(列表查询-true,导出查询-false)
     * @return HistoryReportInfo 外设的图表信息
     */
    List<EquipmentConsumablesInfo> getEquipmentSupplies(String branchId,
                                                        EquipmentSuppliesQueryParams queryParams,
                                                        boolean pageOpening);

    /**
     * 设备管理人员故障处理速度考核查询
     *
     * @param branchId    机构编号
     * @param queryParams 设备管理人员故障处理速度考核报表查询参数
     * @param pageOpening 区分当前查询是列表查询还是导出，影响到是否分页(列表查询-true,导出查询-false)
     * @return 设备管理人员故障处理速度考核集合
     */
    List<ManagersFaultHandlingSpeedInfo> getManagersFaultHandlingSpeedInfo(
            String branchId,
            ManagersFaultHandlingSpeedParams queryParams,
            boolean pageOpening);

    /**
     * 设备管理人员故障处理速度考核图表信息查询
     *
     * @param branchId    机构编号
     * @param queryParams 设备管理人员故障处理速度考核报表查询参数
     * @return 设备管理人员故障处理速度考核图表信息
     */
    ManagersFaultHandlingSpeedGraphicGatherInfo getManagersFaultHandlingSpeedGraphicInfo(
            String branchId,
            ManagersFaultHandlingSpeedParams queryParams);

    /**
     * 设备耗材报表统计-图表信息查询
     *
     * @param branchId    机构编号
     * @param queryParams 查询条件
     * @return HistoryReportInfo 设备耗材报表统计-图表信息查询
     */
    List<EquipmentConsumablesInfo> getEquipmentSuppliesGraphicInfo(String branchId,
                                                                   EquipmentSuppliesQueryParams queryParams);

    /**
     * 网点错账速度统计报表查询
     *
     * @param branchId
     * @param isExport         是否导出
     * @param peripheralParams
     * @return
     */
    ResultWrongAccount getWrongAccountInfo(String branchId,
                                           WrongAccountParams peripheralParams,
                                           Boolean isExport);

    /**
     * 网点错账速度统计报表导出
     *
     * @param branchId         机构编号
     * @param title            题目
     * @param peripheralParams 查询参数
     * @return XSSFWorkbook
     * @throws ExcelExportException
     */
    InputStream exportWrongAccountInfo(String branchId,
                                       WrongAccountParams peripheralParams,
                                       String title) throws ExcelExportException;

    /**
     * 柜员满意度评价统计报表导出
     *
     * @param branchId         机构编号
     * @param title            题目
     * @param satisfactionTellerParams 查询参数
     * @return XSSFWorkbook
     * @throws ExcelExportException
     */
    InputStream exportSatisfactionTellerParamsInfo(String branchId,
                                       SatisfactionTellerParams satisfactionTellerParams,
                                       String title) throws ExcelExportException;

    /**
     * 查询交易质量统计报表---列表和图标信息
     *
     * @param historyTransQualityParams 查询参数
     * @param isExport                  是否导出
     * @return
     */
    ResultTranQuality getHistoryTransQualityInfoAndGraphic(
            HistoryTransQualityParams historyTransQualityParams,
            Boolean isExport);

    /**
     * 网点交易质量报表导出
     *
     * @param params 查询参数
     * @param title  题目
     * @return XSSFWorkbook
     * @throws ExcelExportException
     */
    InputStream exportTranQuality(HistoryTransQualityParams params,
                                  String title) throws ExcelExportException;

    /**
     * 网点交易质量详情查询
     *
     * @param historyTransQualityParams 查询参数
     * @param isExport                  是否导出
     * @return HistoryTransQualityInfo    返回实体
     */
    List<HistoryTransQualityInfo> getTranQualityDataForListByBranchDetail(
            HistoryTransQualityParams historyTransQualityParams,
            Boolean isExport);

    /**
     * 网点交易质量详情导出
     *
     * @param params 查询参数
     * @param title  题目
     * @return XSSFWorkbook
     * @throws ExcelExportException
     */
    InputStream exportTranQualityDetail(HistoryTransQualityParams params,
                                         String title) throws ExcelExportException;

    /**
     * 创建设备管理人员故障处理速度WorkBook
     *
     * @param branchId    机构编号
     * @param queryParams 查询参数
     * @param title       报表title
     * @return 设备管理人员故障处理速度WorkBook
     * @throws ExcelExportException
     */
    InputStream createExcelWithManagersFaultHandlingSpeedInfo(String branchId,
                                                              ManagersFaultHandlingSpeedParams queryParams,
                                                              String title) throws ExcelExportException;

    /**
     * 创建设备耗材WorkBook
     *
     * @param branchId    机构编号
     * @param queryParams 查询参数
     * @param title       报表title
     * @return 设备耗材WorkBook
     * @throws ExcelExportException
     */
    InputStream createExcelWithEquipmentSupplies(String branchId,
                                                 EquipmentSuppliesQueryParams queryParams,
                                                 String title) throws ExcelExportException;

    /**
     * 返回报表title中的机构部分
     *
     * @param branchId 机构id
     * @return 返回报表title中的机构部分
     */
    StringBuilder getOrgReportTitle(String branchId);

    /**
     * 创建外设故障汇总信息WorkBook
     *
     * @param branchId         机构id
     * @param peripheralParams 查询参数
     * @param title            报表title
     * @return 创建外设故障汇总信息WorkBook
     * @throws ExcelExportException
     */
    InputStream createExcelWithPeripheralFaultRateGather(String branchId,
                                                         PeripheralFaultRateQueryParams peripheralParams,
                                                         String title) throws ExcelExportException;

    /**
     * 创建设备耗材WorkBook
     *
     * @param branchId            机构编号
     * @param historyReportParams 查询参数
     * @param title               报表title
     * @return 设备耗材WorkBook
     * @throws ExcelExportException
     */
    InputStream createExcelWithHistroyReportInfo(String branchId,
                                                 HistoryReportParams historyReportParams,
                                                 String title) throws ExcelExportException;

    /**
     * 柜员满意度评价数据
     *
     * @param branchId            机构编号
     * @param satisfactionTellerParams 查询参数
     * @return 设备耗材WorkBook
     * @throws ExcelExportException
     */
    List<SatisfactionTellerInfo>
     getSatisfactionTellerInfo(String branchId, SatisfactionTellerParams satisfactionTellerParams);
    /**
     * 总行视角各机构满意度评价数据
     *
     * @param branchId            机构编号
     * @param satisfactionOrgReportParams 查询参数
     * @return 设备耗材WorkBook
     * @throws ExcelExportException
     */
    List<SatisfactionOrgReportInfo>
     getsatisfactionOrgReportInfo(String branchId, SatisfactionOrgReportParams satisfactionOrgReportParams);
    /**
     * 总行视角各机构满意度评价数据
     *
     * @param branchId            机构编号
     * @param satisfactionOrgReportParams 查询参数
     * @param reportTitle 表头
     * @return 设备耗材WorkBook
     * @throws ExcelExportException
     */
    InputStream
     exportSatisfactionOrgReportInfo(String branchId,
                                     SatisfactionOrgReportParams satisfactionOrgReportParams,
                                     String reportTitle) throws ExcelExportException;

    /**
     * 总行视角机构交易处理时长报表(高频交易前100)查询
     *@param branchId            机构编号
     * @param tradeTimeZhParams 查询参数
     * @return 设备耗材WorkBook
     */
    List<TradeTimeZhInfo> getTradeTimeZhInfo(String branchId, TradeTimeZhParams tradeTimeZhParams);

    /**
     * 总行视角机构交易处理时长报表(高频交易前100)导出
     *@param branchId            机构编号
     * @param tradeTimeZhParams 查询参数
     * @param title 表头
     * @exception Exception 错误
     * @throws Exception
     * @return 设备耗材WorkBook
     */
    InputStream exportTradeTimeZHParamsInfo(String branchId,
                                            TradeTimeZhParams tradeTimeZhParams,
                                            String title) throws Exception;


    /**
     * 支行主管视角柜员交易代码处理时长（展示前100高频交易）查询
     *@param branchId            机构编号
     * @param tradeTimeLeaderParams 查询参数
     * @return 设备耗材WorkBook
     */
    List<TradeTimeLeaderInfo> getTradeTimeLeaderInfo(
            String branchId,
            TradeTimeLeaderParams tradeTimeLeaderParams);


    /**
     * 支行主管视角柜员交易代码处理时长（展示前100高频交易）导出
     *@param branchId            机构编号
     * @param tradeTimeLeaderParams 查询参数
     * @param title 表头
     * @exception Exception 错误
     * @throws Exception
     * @return 设备耗材WorkBook
     */

    InputStream exportTradeTimeLeaderParamsInfo(String branchId,
                                                TradeTimeLeaderParams tradeTimeLeaderParams,
                                                String title) throws Exception;

    /**
     * 总行视角等候时间数据报表查询
     *@param branchId            机构编号
     * @param quWaitTimeParams 查询参数
     * @return 设备耗材WorkBook
     */
    List<QuWaitTimeInfo> getQuWaitTimeInfo(String branchId, QuWaitTimeParams quWaitTimeParams);


    /**
     * 总行视角等候时间数据报表导出
     *@param branchId            机构编号
     * @param quWaitTimeParams 查询参数
     * @param title 表头
     * @exception Exception 错误
     * @throws Exception
     * @return 设备耗材WorkBook
     */

    InputStream exportQuWaitTimeParamsInfo(String branchId,
                                           QuWaitTimeParams quWaitTimeParams,
                                           String title) throws Exception;


    /**
     *支行视角等候时间数据查询
     *@param branchId            机构编号
     * @param waittingTimebranchParams 查询参数
     * @return 设备耗材WorkBook
     */
    List<WaittingTimebranchReportInfo>
      getWaittingTimebranchReportInfo(String branchId, WaittingTimebranchParams waittingTimebranchParams);
    /**
     * 支行视角等候时间数据导出
     *@param branchId            机构编号
     * @param waittingTimebranchParams 查询参数
     * @param title 表头
     * @exception Exception 错误
     * @throws ExcelExportException
     * @return 设备耗材WorkBook
     */

    InputStream exportwaittingTimebranc(String branchId,
                                              WaittingTimebranchParams waittingTimebranchParams,
                                              String title) throws ExcelExportException;



    /**
     *支行视角柜员服务客户时间查询
     *@param branchId            机构编号
     * @param serviceTimeParams 查询参数
     * @return 设备耗材WorkBook
     */
    List<ServiceTimeReportInfo> getServiceTimeReportInfo(String branchId, ServiceTimeParams serviceTimeParams);
    /**
     * 支行视角柜员服务客户时间导出
     *@param branchId            机构编号
     * @param serviceTimeParams 查询参数
     * @param title 表头
     * @exception Exception 错误
     * @throws ExcelExportException
     * @return 设备耗材WorkBook
     */
    InputStream exportserviceTime(String branchId,
                                        ServiceTimeParams serviceTimeParams,
                                        String title) throws ExcelExportException;



    /**
     *支行网点视角交易代码时长数据查询
     *@param branchId            机构编号
     * @param tradeTimeBranchParams 查询参数
     * @return 设备耗材WorkBook
     */
    List<TradeTimeBranchReportInfo>
        getTradeTimeBranchReportInfo(String branchId, TradeTimeBranchParams tradeTimeBranchParams);

    /**
     * 支行网点视角交易代码时长数据导出
     *@param branchId            机构编号
     * @param tradeTimeBranchParams 查询参数
     * @param title 表头
     * @exception Exception 错误
     * @throws ExcelExportException
     * @return 设备耗材WorkBook
     */
    InputStream exporttradeTimeBranch(String branchId,
                                            TradeTimeBranchParams tradeTimeBranchParams,
                                            String title) throws ExcelExportException;

    /**
     *支行柜员视角柜员交易代码处理时长查询
     *@param branchId            机构编号
     * @param tradetimecounterclerkParams 查询参数
     * @return 设备耗材WorkBook
     */
    List<TradeTimeCounterClerkReportInfo>
        getTradeTimeCounterClerkReportInfo(String branchId,
                                           TradetimecounterclerkParams tradetimecounterclerkParams);
    /**
     * 支行柜员视角柜员交易代码处理时长导出
     *@param branchId            机构编号
     * @param tradetimecounterclerkParams 查询参数
     * @param title 表头
     * @throws ExcelExportException
     * @return 设备耗材WorkBook
     */
    InputStream exporttradetimecounterclerk(String branchId,
                                                  TradetimecounterclerkParams tradetimecounterclerkParams,
                                                  String title) throws ExcelExportException;


    /**
     * 查询时间段和票号数图表信息
     *
     * @param branchId            机构编号
     * @param clientFlowReportParams 查询条件
     * @return HistoryReportInfo 报表统计信息
     */
    ClientFlowReportInfo getClientFlowGraphic(String branchId, ClientFlowReportParams clientFlowReportParams);
}
