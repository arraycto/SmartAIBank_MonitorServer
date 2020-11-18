package com.dcfs.smartaibank.manager.monitor.web.dao;


import com.dcfs.smartaibank.manager.monitor.web.domian.*;
import com.dcfs.smartaibank.manager.monitor.web.param.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 交易对账数据查询接口
 *
 * @author wangtingo
 * @className HistoryReportDao.java
 * @date 2019-7-11 17:15:45
 */
@Mapper
public interface HistoryReportDao {

    /**
     * 1.查询历史信息的信息服务
     *
     * @param branchId            机构号
     * @param historyReportParams 查询参数
     * @return HistroyReportInfo 历史交易信息
     */
    List<HistoryReportInfo> getHistroyReportList(@Param("branchId") String branchId,
                                                 @Param("historyReportParams") HistoryReportParams historyReportParams);

    /**
     * 1.查询外设图表的信息
     *
     * @param branchId            机构号
     * @param historyReportParams 查询参数
     * @return PeripheralFaultRateQueryParams 历史交易信息
     */
    List<HistoryReportInfo> getPeripheralGraphic(
            @Param("branchId") String branchId,
            @Param("historyReportParams") PeripheralFaultRateQueryParams historyReportParams);

    /**
     * 1.查询历史图表的信息
     *
     * @param branchId            机构号
     * @param historyReportParams 查询参数
     * @return HistroyReportInfo 历史交易信息
     */
    List<HistoryReportInfo> getHistroyGraphic(@Param("branchId") String branchId,
                                              @Param("historyReportParams") HistoryReportParams historyReportParams);

    /**
     * 查询设备类型的横坐标
     * @param arr 设备类型id
     * @return 设备类型信息集合
     */
    List<AlterNativeInfo> getDeviceType(@Param("arr") String[] arr);

    /**
     * 查询厂商
     *
     * @param arr 厂商id
     * @return 厂商信息集合
     */
    List<ManufFaultInfo> queryManuf(@Param("arr") String[] arr);

    /**
     * 查询指定设备类型的所有外设模块
     *
     * @param devClassKey 设备类型
     * @return 指定设备类型的所有外设模块集合
     */
    List<PeripheralModel> getDeviceClass(@Param("devClassKey") String devClassKey);

    /**
     * 查询外设故障详情中的基础信息（机具类型及名称、机具型号及名称、厂商id及名称、开机时长）
     *
     * @param branchId         　机构id
     * @param peripheralParams 监控报表-外设故障率查询参数
     * @return 外设故障详情中的基础信息（机具类型及名称、机具型号及名称、厂商id及名称、开机时长）
     */
    List<PeripheralFaultRateGatherInfo> getPeripheralStartUpTimeGather(
            @Param("branchId") String branchId,
            @Param("peripheralParams") PeripheralFaultRateQueryParams peripheralParams);

    /**
     * 查询设故障详情中的外设故障率集合
     *
     * @param branchId         机构id
     * @param peripheralParams 监控报表-外设故障率查询参数
     * @param models           机具型号集合
     * @return 设故障详情中的外设故障率集合
     */
    List<PeripheralFaultRateGatherInfo> getPeripheralFaultRateGatherInfo(
            @Param("branchId") String branchId,
            @Param("peripheralParams") PeripheralFaultRateQueryParams peripheralParams,
            @Param("models") List<String> models);

    /**
     * 外设详情页面--列表查询     (tanchen)
     *
     * @param branchId         机构编号
     * @param peripheralParams 查询参数
     * @return HistoryResultList 结果集
     */
    List<HistoryResultList> getHistoryDevicePageList(
            @Param("branchId") String branchId,
            @Param("peripheralParams") PeripheralFaultRateQueryParams peripheralParams);

    /**
     * 查询表头下对应的信息       (tanchen)
     *
     * @param branchId         机构编号
     * @param mIds             终端id数组
     * @param peripheralParams 查询参数
     * @return HistoryHeaderAndinfo
     */
    List<HistoryHeaderAndinfo> getHistoryHeaderInfo(
            @Param("branchId") String branchId,
            @Param("mIds") String[] mIds,
            @Param("peripheralParams") PeripheralFaultRateQueryParams peripheralParams);

    /**
     * 查询表头信息     (tanchen)
     *
     * @param devClassKey （大类）设备类型
     * @return HistoryHeaderAndinfo
     */
    List<HistoryHeaderAndinfo> getHistoryHeader(@Param("devClassKey") String devClassKey);

    /**
     * 网点错账记录报表查询 - 列表数据   (tanchen)
     *
     * @param branchId         机构编号
     * @param peripheralParams 查询参数
     * @return HistoryErrotAccountInfo
     */
    List<HistoryErrotAccountInfo> getErrorAccountDataList(
            @Param("branchId") String branchId,
            @Param("peripheralParams") WrongAccountParams peripheralParams);

    /**
     * 网点错账记录报表查询 - 图表数据   (tanchen)
     *
     * @param branchId         机构编号
     * @param peripheralParams 查询参数
     * @return HistoryErrotAccountInfo
     */
    List<HistoryErrotAccountInfo> getErrorAccountDataForGraph(
            @Param("branchId") String branchId,
            @Param("peripheralParams") WrongAccountParams peripheralParams);

    /**
     * 网点错账记录报表查询 - 平均错账处理时间    (tanchen)
     *
     * @param branchId         机构编号
     * @param peripheralParams 查询参数
     * @return getErrorAccountDataAvg
     */
    Float getErrorAccountDataAvg(
            @Param("branchId") String branchId,
            @Param("peripheralParams") WrongAccountParams peripheralParams);

    /**
     * 查询设备耗材信息
     *
     * @param branchId    机构号
     * @param queryParams 查询参数
     * @return HistroyReportInfo 耗材信息
     */
    List<EquipmentConsumablesInfo> getEquipmentSupplies(
            @Param("branchId") String branchId,
            @Param("queryParams") EquipmentSuppliesQueryParams queryParams);


    /**
     * 查询设备管理人员故障处理速度考核信息
     *
     * @param branchId    机构编号
     * @param queryParams 设备管理人员故障处理速度考核报表查询参数
     * @return 设备管理人员故障处理速度考核集合
     */
    List<ManagersFaultHandlingSpeedInfo> selectManagersFaultHandlingSpeedInfo(
            @Param("branchId") String branchId,
            @Param("queryParams") ManagersFaultHandlingSpeedParams queryParams);

    /**
     * 查询设备管理人员故障处理速度考核平均值信息
     *
     * @param branchId    机构编号
     * @param queryParams 设备管理人员故障处理速度考核报表查询参数
     * @return 查询设备管理人员故障处理速度考核平均值信息
     */
    ManagersFaultHandlingSpeedAvgInfo selectManagersFaultHandlingSpeedAVGInfo(
            @Param("branchId") String branchId,
            @Param("queryParams") ManagersFaultHandlingSpeedParams queryParams);

    /**
     * 设备管理人员故障处理速度考核图表信息查询
     *
     * @param branchId    机构编号
     * @param queryParams 设备管理人员故障处理速度考核报表查询参数
     * @return 设备管理人员故障处理速度考核图表信息
     */
    List<ManagersFaultHandlingSpeedGraphicInfo> selectManagersFaultHandlingSpeedGraphicInfo(
            @Param("branchId") String branchId,
            @Param("queryParams") ManagersFaultHandlingSpeedParams queryParams);

    /**
     * 查询指定机构下所有的设备管理人员信息
     *
     * @param branchId 机构id
     * @return
     */
    List<Operator> selectAllManagersInfo(@Param("branchId") String branchId);

    /**
     * 查询设备耗材（平均使用小时-均值）
     *
     * @param branchId    机构id
     * @param queryParams 查询参数
     * @return 查询设备耗材（平均使用小时-均值）
     */
    Float selectEquipmentSuppliesAVGInfo(@Param("branchId") String branchId,
                                         @Param("queryParams") EquipmentSuppliesQueryParams queryParams);

    /**
     * 设备耗材报表统计-图表信息查询
     *
     * @param branchId    机构号
     * @param queryParams 查询参数
     * @return HistroyReportInfo 设备耗材报表统计-图表信息查询
     */
    List<EquipmentConsumablesInfo> selectEquipmentSuppliesGraphicInfo(
            @Param("branchId") String branchId,
            @Param("queryParams") EquipmentSuppliesQueryParams queryParams);

    /**
     * 交易质量--业务类型备选数据
     *
     * @param systemId 系统类型Id
     * @return HistoryTranType业务类型实体
     */
    List<HistoryTranType> getTranTypeCondition(@Param("systemId") String systemId);

    /**
     * 交易质量--列表信息查询
     *
     * @param historyTransQualityParams 查询参数
     * @return HistoryTransQualityInfo 返回结果集
     */
    List<HistoryTransQualityInfo> getTranQualityDataList(HistoryTransQualityParams historyTransQualityParams);

    /**
     * 交易质量--查询办理成功率均值
     *
     * @param historyTransQualityParams 查询参数
     * @return 办理成功率均值
     */
    Float getTranQualityDataAvg(HistoryTransQualityParams historyTransQualityParams);

    /**
     * 交易质量--图标信息查询
     *
     * @param historyTransQualityParams 查询参数
     * @return HistoryTransQualityInfo 返回实体
     */
    List<HistoryTransQualityInfo> getTranQualityDataForGraph(HistoryTransQualityParams historyTransQualityParams);

    /**
     * 网点交易质量详情查询
     *
     * @param historyTransQualityParams 查询参数
     * @return HistoryTransQualityInfo    返回实体
     */
    List<HistoryTransQualityInfo> getTranQualityDataForListByBranchDetail(
            HistoryTransQualityParams historyTransQualityParams);

    /**
     * 返回当前机构的机构数所有直属父节点（祖宗），顺序按照机构级别排序，总->分->营业兼管理->支行
     *
     * @param branchId 机构id
     * @return 返回当前机构的机构数所有直属父节点（祖宗）
     */
    List<String> selectOrgReportTitle(@Param("branchId") String branchId);


    /**
     * 柜员满意度评价报表查询
     *
     * @param branchId 机构号
     * @param satisfactionTellerParams 查询参数
     * @return getErrorAccountDataAvg
     */
    List<SatisfactionTellerInfo> getSatisfactionTellerInfoList(
            @Param("branchId") String branchId,
            @Param("satisfactionTellerParams") SatisfactionTellerParams satisfactionTellerParams);
    /**
     * 柜员满意度评价报表查询
     *
     * @param branchId 机构号
     * @param satisfactionOrgReportParams 查询参数
     * @return getErrorAccountDataAvg
     */
    List<SatisfactionOrgReportInfo> getsatisfactionOrgReportInfoList(
            @Param("branchId") String branchId,
            @Param("satisfactionOrgReportParams") SatisfactionOrgReportParams satisfactionOrgReportParams);

    /**
     * 总行视角机构交易处理时长报表
     *
     * @param branchId    机构id
     * @param tradeTimeZHParams 查询参数
     * @return 查询设备耗材（平均使用小时-均值）
     */
    List<TradeTimeZhInfo> getzhtradetimeInfoList(@Param("branchId") String branchId,
                                                 @Param("tradeTimeZhParams") TradeTimeZhParams tradeTimeZHParams);
    /**
     * 支行主管视角柜员交易代码处理时长
     *
     * @param branchId    机构id
     * @param tradeTimeLeaderParams 查询参数
     * @return 查询设备耗材（平均使用小时-均值）
     */
    List<TradeTimeLeaderInfo> getleadertradetimeInfoList(
            @Param("branchId") String branchId,
            @Param("tradeTimeLeaderParams") TradeTimeLeaderParams tradeTimeLeaderParams);

    /**
     * 总行视角等候时间数据报表
     * @param branchId    机构id
     * @param quWaitTimeParams 查询参数
     * @return 查询设备耗材（平均使用小时-均值）
     */
    List<QuWaitTimeInfo> getquWaitTimeInfoList(@Param("branchId") String branchId,
                                               @Param("quWaitTimeParams") QuWaitTimeParams quWaitTimeParams);


    /**
     * 支行视角客户等候时间
     * * @param branchId    机构id
     * @param waittingTimebranchParams 查询参数
     * @return 查询设备耗材（平均使用小时-均值）
     *
     */


    List<WaittingTimebranchReportInfo> getwaittingTimebranchReportInfoList(
            @Param("branchId") String branchId,
            @Param("waittingTimebranchParams") WaittingTimebranchParams waittingTimebranchParams);


    /**
     * 支行视角，柜员服务客户时间统计
     * * @param branchId    机构id
     * @param serviceTimeParams 查询参数
     * @return 查询设备耗材（平均使用小时-均值）
     *
     */
    List<ServiceTimeReportInfo> getServiceTimeReportInfoList(
            @Param("branchId") String branchId,
            @Param("serviceTimeParams") ServiceTimeParams serviceTimeParams);

    /**
     * 支行柜员视角柜员交易代码处理时长
     * * @param branchId    机构id
     * @param tradetimecounterclerkParams 查询参数
     * @return 查询设备耗材（平均使用小时-均值）
     *
     */
    List<TradeTimeCounterClerkReportInfo> getTradeTimeCounterClerkReportInfoList(
            @Param("branchId") String branchId,
            @Param("tradetimecounterclerkParams") TradetimecounterclerkParams tradetimecounterclerkParams);


    /**
     * 支行网点视角交易代码时长数据
     * * @param branchId    机构id
     * @param tradeTimeBranchParams 查询参数
     * @return 查询设备耗材（平均使用小时-均值）
     *
     */
    List<TradeTimeBranchReportInfo> getTradeTimeBranchReportInfoList(
            @Param("branchId") String branchId,
            @Param("tradeTimeBranchParams") TradeTimeBranchParams tradeTimeBranchParams
    );


    /**
     * 查询时间段的横坐标
     * @param branchNo 设备类型id
     * @return 设备类型信息集合
     */
    List<ClientFlowTimeInfo> getTime(String branchNo);

    /**
     * 查询票号数纵坐标
     *
     * @param branchNo 厂商id
     * @return 厂商信息集合
     */
    List<ClientFlowTimezInfo> queryCount(String branchNo);

    /**
     * 1.查询历史图表的信息
     *
     * @param branchId            机构号
     * @param clientFlowReportParams 查询参数
     * @return HistroyReportInfo 历史交易信息
     */
    List<ClientFlowReportInfo> getClientTimeGraphic(@Param("branchId") String branchId,
                                              @Param("clientFlowReportParams") ClientFlowReportParams clientFlowReportParams);
}
