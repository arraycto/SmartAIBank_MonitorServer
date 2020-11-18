package com.dcfs.smartaibank.manager.monitor.web.service.impl;

import com.dcfs.smartaibank.file.excel.ExcelExportException;
import com.dcfs.smartaibank.manager.monitor.web.constance.Constance;
import com.dcfs.smartaibank.manager.monitor.web.constance.ExcelTemplateConstants;
import com.dcfs.smartaibank.manager.monitor.web.dao.HistoryReportDao;
import com.dcfs.smartaibank.manager.monitor.web.domian.AlterNativeInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.ClientFlowReportInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.ClientFlowTimeInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.ClientFlowTimezInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.EquipmentConsumablesInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.HeaderColInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.HistoryErrotAccountInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.HistoryGraphInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.HistoryHeaderAndinfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.HistoryReportInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.HistoryResultList;
import com.dcfs.smartaibank.manager.monitor.web.domian.HistoryTransQualityInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.ManagersFaultHandlingSpeedAvgInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.ManagersFaultHandlingSpeedGraphicGatherInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.ManagersFaultHandlingSpeedGraphicInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.ManagersFaultHandlingSpeedInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.ManagersResponseTimeInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.ManagersSolveTimeInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.ManufFaultDataInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.ManufFaultInfo;
import com.dcfs.smartaibank.manager.monitor.web.domian.Operator;
import com.dcfs.smartaibank.manager.monitor.web.domian.PeripheralFaultRate;
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
import com.dcfs.smartaibank.manager.monitor.web.service.HistoryReportService;
import com.dcfs.smartaibank.manager.monitor.web.util.DateUtil;
import com.dcfs.smartaibank.manager.monitor.web.util.FloatCompareUtil;
import com.dcfs.smartaibank.manager.monitor.web.util.TableHeadersCreateUtil;
import com.dcfs.smartaibank.manager.monitor.web.util.excelutil.ExcelUtilForMonitor;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author wangtingo
 * @date 2019/7/11 10:58
 * @since
 */
@Service
public class HistoryReportServiceImpl implements HistoryReportService {

    private static final String DATATYPE_NORMAL = "NORMAL";

    private static final String DAY = "day";
    private static final String MONTH = "month";
    private static final String QUARTER = "quarter";
    private static final String YEAR = "year";

    @Autowired
    private HistoryReportDao historyReportDao;


    @Override
    public List<HistoryReportInfo> getHistroyReportInfo(String branchId, HistoryReportParams historyReportParams) {
        if (!StringUtils.isEmpty(historyReportParams.getPageSize())) {
            PageHelper.startPage(historyReportParams.getPageNum(), historyReportParams.getPageSize());
        }
        List<HistoryReportInfo> historyReportInfos =
                historyReportDao.getHistroyReportList(branchId, historyReportParams);
        return historyReportInfos;
    }

    @Override
    public HistoryGraphInfo getHistroyGraphic(String branchId, HistoryReportParams historyReportParams) {
        //查询横坐标的设备类型值
        List<AlterNativeInfo> deviceType = historyReportDao.getDeviceType(historyReportParams.getDevClassKey());
        //查询厂商的值
        List<ManufFaultInfo> manuf = historyReportDao.queryManuf(historyReportParams.getManufId());
        //数据信息
        List<HistoryReportInfo> historyReportInfos = historyReportDao.getHistroyGraphic(branchId, historyReportParams);

        for (ManufFaultInfo minfo : manuf) {
            List<ManufFaultDataInfo> mdata = new ArrayList<>();
            for (HistoryReportInfo dataInfo : historyReportInfos) {
                if (minfo.getManufId().equals(dataInfo.getManufId())) {
                    //获得每种厂商下所有设备的值
                    ManufFaultDataInfo data = new ManufFaultDataInfo();
                    data.setNormalTime(dataInfo.getNormalTime());
                    data.setDevFaultRate(dataInfo.getDevFaultRate());
                    data.setDeviceKey(dataInfo.getToolClassKey());
                    data.setFaultTime(dataInfo.getFaultTime());
                    mdata.add(data);
                }
            }
            minfo.setInfos(mdata);
        }
        HistoryGraphInfo info = new HistoryGraphInfo();
        info.setDeviceType(deviceType);
        info.setMInfos(manuf);
        return info;
    }

    @Override
    public List<HistoryGraphInfo> getPeripheralGraphic(
            String branchId,
            PeripheralFaultRateQueryParams historyReportParams) {
        //表头信息
        List<HistoryReportInfo> historyReportInfos =
                historyReportDao.getPeripheralGraphic(branchId, historyReportParams);
        //表头
        List<PeripheralModel> peripheralModelList =
                this.getPeripheralFaultRateHeader(branchId, historyReportParams.getDevClassKey());
        //返回结果集
        List<HistoryGraphInfo> resultList = new ArrayList<>();

        for (PeripheralModel header : peripheralModelList) {
            HistoryGraphInfo headerAndInfo = new HistoryGraphInfo();
            headerAndInfo.setDevClassKey(header.getPeripheralId());
            headerAndInfo.setDevClassName(header.getPeripheralName());
            List<ManufFaultInfo> infos = new ArrayList<>();
            if (historyReportInfos.size() > 0) {
                for (HistoryReportInfo info : historyReportInfos) {
                    ManufFaultInfo child = new ManufFaultInfo();
                    if (info.getDevClassKey().equals(header.getPeripheralId())) {
                        child.setManufId(info.getManufId());
                        child.setManufName(info.getManufName());
                        child.setDevFaultRate(info.getDevFaultRate());
                        infos.add(child);
                    }
                }
            }
            headerAndInfo.setManufFaultInfo(infos);
            resultList.add(headerAndInfo);
        }
        return resultList;
    }

    private List<HistoryGraphInfo> summaryData(HistoryReportParams historyReportParams,
                                               List<HistoryReportInfo> historyReportInfos) {
        String[] maufId = historyReportParams.getManufId();
        List<HistoryGraphInfo> list = new ArrayList<>();
        HistoryGraphInfo historyGraphInfo = new HistoryGraphInfo();
        float rate = 0;
        int failStr = 0;
        int normalStr = 0;
        historyGraphInfo.setDevClassKey("");
        List<ManufFaultInfo> listManuf = new ArrayList<>();
        for (int i = 0; i < maufId.length; i++) {
            for (HistoryReportInfo historyReportInfo : historyReportInfos) {
                if (maufId[i].equals(historyReportInfo.getManufId())) {
                    failStr = failStr + historyReportInfo.getFaultTime();
                    normalStr = normalStr + historyReportInfo.getNormalTime();
                }
            }
            DecimalFormat df = new DecimalFormat("##.##");
            if (normalStr != 0) {
                if (historyReportParams.getDataType().equals(DATATYPE_NORMAL)) {
                    rate = ((float) normalStr - (float) failStr) / (float) normalStr * 100;
                } else {
                    rate = (float) failStr / (float) normalStr * 100;
                }
            }
            ManufFaultInfo manufFaultInfo = new ManufFaultInfo();
            manufFaultInfo.setManufId(maufId[i]);
            manufFaultInfo.setFaultTime(failStr);
            manufFaultInfo.setNormalTime(normalStr);
            manufFaultInfo.setDevFaultRate(Float.parseFloat(df.format(rate)));
            listManuf.add(manufFaultInfo);
        }
        historyGraphInfo.setManufFaultInfo(listManuf);
        list.add(historyGraphInfo);
        return list;
    }

    private List<HistoryGraphInfo> peripheralData(PeripheralFaultRateQueryParams historyReportParams,
                                                  List<HistoryReportInfo> historyReportInfos) {
        String devClassKey = historyReportParams.getDevClassKey();
        List<PeripheralModel> list = TableHeadersCreateUtil.createTableHeader(devClassKey);
        List<HistoryGraphInfo> resultList = new ArrayList<>();
        for (PeripheralModel model : list) {
            HistoryGraphInfo historyGraphInfo = new HistoryGraphInfo();
            historyGraphInfo.setDevClassKey(model.getPeripheralId());
            historyGraphInfo.setDevClassName(model.getPeripheralName());
            List<ManufFaultInfo> listManuf = new ArrayList<>();
            for (HistoryReportInfo historyReportInfo : historyReportInfos) {
                if (model.getPeripheralId().equals(historyReportInfo.getDevClassKey())) {
                    ManufFaultInfo manufFaultInfo = new ManufFaultInfo();
                    manufFaultInfo.setManufId(historyReportInfo.getManufId());
                    manufFaultInfo.setFaultTime(historyReportInfo.getFaultTime());
                    manufFaultInfo.setNormalTime(historyReportInfo.getNormalTime());
                    manufFaultInfo.setDevFaultRate(historyReportInfo.getDevFaultRate());
                    listManuf.add(manufFaultInfo);
                }
            }
            historyGraphInfo.setManufFaultInfo(listManuf);
            resultList.add(historyGraphInfo);
        }
        return resultList;
    }

    @Override
    public List<PeripheralModel> getPeripheralFaultRateHeader(String branchId, String devClassKey) {
        // 1.查询指定设备类型的所有外设模块
        List<PeripheralModel> queryDeviceClass = historyReportDao.getDeviceClass(devClassKey);
        return queryDeviceClass;
    }

    @Override
    public List<HistoryResultList> getHistoryReportListAndPheral(
            String branchId,
            PeripheralFaultRateQueryParams peripheralParams,
            Boolean isExport) {
        if (!isExport) {
            PageHelper.startPage(peripheralParams.getPageNum(), peripheralParams.getPageSize());
        }
        //查询分页的信息
        List<HistoryResultList> historyDevicePageList =
                historyReportDao.getHistoryDevicePageList(branchId, peripheralParams);
        int size = historyDevicePageList.size();
        String[] mIds = new String[size];
        for (int i = 0; i < size; i++) {
            mIds[i] = historyDevicePageList.get(i).getMId();
        }
        //查询每一行对应的表头和信息详情
        List<HistoryHeaderAndinfo> historyHeaderInfo =
                historyReportDao.getHistoryHeaderInfo(branchId, mIds, peripheralParams);
        //首先将 分页的每一行 信息， 和每一行表头下相对应的信息进行组装
        for (HistoryResultList pageInfo : historyDevicePageList) {
            List<HistoryHeaderAndinfo> temp = new ArrayList<>();
            for (HistoryHeaderAndinfo info : historyHeaderInfo) {
                if (info.getMId().equals(pageInfo.getMId())) {
                    temp.add(info);
                }
            }
            pageInfo.setLists(revertInfo(temp, peripheralParams.getDevClassKey(), branchId));
        }
        return historyDevicePageList;
    }

    @Override
    public InputStream createExcelWithPeripheral(
            String branchId,
            PeripheralFaultRateQueryParams peripheralParams,
            String title) {
        //查询列表信息(包括表头和表头下的信息)
        List<HistoryResultList> infoLists =
                this.getHistoryReportListAndPheral(branchId, peripheralParams, Constance.IS_EXPORT);
        List<PeripheralModel> peripheralModelList =
                this.getPeripheralFaultRateHeader(branchId, peripheralParams.getDevClassKey());
        //获取统计时段
        String rangTime = createRangeTime(
                peripheralParams.getReportType(),
                peripheralParams.getYear(),
                peripheralParams.getQuarter(),
                peripheralParams.getMonth(),
                peripheralParams.getStartDate(),
                peripheralParams.getEndDate());
        List<HeaderColInfo> headerColInfos =
                Arrays.asList(HeaderColInfo.builder().key("normalTime").value("外设故障时间").build(),
                        HeaderColInfo.builder().key("rate").value("外设故障率").build());
        InputStream inputStream = null;
        try {
            inputStream = ExcelUtilForMonitor.createExcelFilesWithSecondLevelHeader(infoLists,
                    ExcelTemplateConstants.PERIPHERAL_FFAULT_DETAILS_TEMPLATE,
                    title, rangTime, headerColInfos, peripheralModelList);
        } catch (Exception e) {

        }
        return inputStream;
    }

    @Override
    public List<PeripheralFaultRateGatherInfo> getPeripheralFaultRateGather(
            String branchId,
            PeripheralFaultRateQueryParams peripheralParams,
            boolean pageOpening) {
        List<PeripheralModel> tableHeader =
                this.getPeripheralFaultRateHeader(branchId, peripheralParams.getDevClassKey());
        // 1.查询外设故障详情中的基础信息（机具类型及名称、机具型号及名称、厂商id及名称、开机时长）
        // 这里判断是否开启分页查询
        if (pageOpening) {
            PageHelper.startPage(peripheralParams.getPageNum(), peripheralParams.getPageSize());
        }
        List<PeripheralFaultRateGatherInfo> peripheralStartUpTimeGather =
                historyReportDao.getPeripheralStartUpTimeGather(branchId, peripheralParams);
        // 2.获取查询到的设备型号集合，如果设备型号集合大于0，则进行下一次查询
        List<String> modelList = new ArrayList<>();
        if (peripheralStartUpTimeGather != null && peripheralStartUpTimeGather.size() > 0) {
            peripheralStartUpTimeGather.stream().forEach(peripheralFaultRateGatherInfo -> {
                String devModelKey = peripheralFaultRateGatherInfo.getDevModelKey();
                if (!modelList.contains(devModelKey)) {
                    modelList.add(devModelKey);
                }
            });
        }
        if (modelList.size() > 0) {
            // 3.查询设故障详情中的外设故障率集合
            List<PeripheralFaultRateGatherInfo> peripheralFaultRateGatherInfo =
                    historyReportDao.getPeripheralFaultRateGatherInfo(branchId, peripheralParams, modelList);
            // 4.合并外设故障详情中的基础信息和的外设故障率集合信息
            // 注意事项：调用合并的方法必须在原始集合peripheralStartUpTimeGather上修改，原因是涉及到分页的操作
            peripheralStartUpTimeGather =
                    resetListResult(peripheralStartUpTimeGather, peripheralFaultRateGatherInfo, tableHeader);

        }
        return peripheralStartUpTimeGather;
    }

    @Override
    public List<ManagersFaultHandlingSpeedInfo> getManagersFaultHandlingSpeedInfo(
            String branchId,
            ManagersFaultHandlingSpeedParams queryParams,
            boolean pageOpening) {
        if (pageOpening) {
            PageHelper.startPage(queryParams.getPageNum(), queryParams.getPageSize());
        }
        // 1.查询设备管理人员的故障处理速度考核信息
        List<ManagersFaultHandlingSpeedInfo> managersFaultHandlingSpeedInfos =
                historyReportDao.selectManagersFaultHandlingSpeedInfo(branchId, queryParams);
        // 2.查询设备管理人员的故障处理速度考核平均值信息
        ManagersFaultHandlingSpeedAvgInfo managersFaultHandlingSpeedAvgInfo =
                historyReportDao.selectManagersFaultHandlingSpeedAVGInfo(branchId, queryParams);
        // 3.与平均值进行对比
        if (managersFaultHandlingSpeedInfos != null && managersFaultHandlingSpeedInfos.size() > 0) {
            float avgResponseTime = managersFaultHandlingSpeedAvgInfo.getAvgResponseTime();
            float avgSolveTime = managersFaultHandlingSpeedAvgInfo.getAvgSolveTime();
            for (ManagersFaultHandlingSpeedInfo speedInfo : managersFaultHandlingSpeedInfos) {
                // 设置响应时间-均值（分钟/个）
                speedInfo.setAvgResponseTime(avgResponseTime);
                // 设置预警等级处理速度
                speedInfo.setAvgSolveTime(avgSolveTime);
                // 设置响应速度对比结果
                speedInfo.setResponseTimeCompareResult(
                        FloatCompareUtil.getCompareResult(speedInfo.getResponseTime(), avgResponseTime));
                // 设置预警等级处理速度对比结果
                speedInfo.setSolveTimeCompareResult(
                        FloatCompareUtil.getCompareResult(speedInfo.getSolveTime(), avgSolveTime));

            }
        }
        return managersFaultHandlingSpeedInfos;
    }

    @Override
    public ManagersFaultHandlingSpeedGraphicGatherInfo getManagersFaultHandlingSpeedGraphicInfo(
            String branchId,
            ManagersFaultHandlingSpeedParams queryParams) {
        // 1.获取所有设备管理人员信息
        List<Operator> operators = historyReportDao.selectAllManagersInfo(branchId);
        // 2.获取设备管理人员故障处理速度考核图表中的柱状图信息
        List<ManagersFaultHandlingSpeedGraphicInfo> managersFaultHandlingSpeedGraphicInfoList =
                historyReportDao.selectManagersFaultHandlingSpeedGraphicInfo(branchId, queryParams);
        return ManagersFaultHandlingSpeedGraphicGatherInfo
                .builder()
                .operatorList(operators)
                .managersFaultHandlingSpeedGraphicInfoList(managersFaultHandlingSpeedGraphicInfoList)
                .build();
    }

    /**
     * 合并外设故障详情中的基础信息和的外设故障率集合信息
     *
     * @param peripheralStartUpTimeGathers   外设故障详情中的基础信息
     * @param peripheralFaultRateGatherInfos 外设故障率集合信息
     * @param tableHeader                    全部表头信息
     * @return 合并后的集合
     */
    private List<PeripheralFaultRateGatherInfo> resetListResult(
            List<PeripheralFaultRateGatherInfo> peripheralStartUpTimeGathers,
            List<PeripheralFaultRateGatherInfo> peripheralFaultRateGatherInfos,
            List<PeripheralModel> tableHeader) {
        // 1.对peripheralStartUpTimeGathers进行初始化，初始化外设故障率详情集合为tableHeader表头里面所有外设
        peripheralStartUpTimeGathers = this.init(peripheralStartUpTimeGathers, tableHeader);
        // 2.在peripheralStartUpTimeGathers上修改内容
        peripheralStartUpTimeGathers.forEach(peripheralStartUpTimeGather -> {
            // 3.找到机具类型和机具型号相同的外设故障率详情信息记录
            Optional<PeripheralFaultRateGatherInfo> optional =
                    peripheralFaultRateGatherInfos.parallelStream().filter(peripheralFaultRateGatherInfo ->
                            peripheralStartUpTimeGather.getDevClassKey()
                                    .equals(peripheralFaultRateGatherInfo.getDevClassKey())
                                    && peripheralStartUpTimeGather.getDevModelKey()
                                    .equals(peripheralFaultRateGatherInfo.getDevModelKey())
                    ).findAny();
            // 如果记录存在
            if (optional.isPresent()) {
                // 4.拿出从数据库中查询到的外设故障率详情记录
                List<PeripheralFaultRate> peripheralFaultRates = optional.get().getPeripheralFaultRates();
                // 5.获取之前初始化的外设故障率详情记录
                List<PeripheralFaultRate> peripheralFaultRatesSum =
                        peripheralStartUpTimeGather.getPeripheralFaultRates();
                // 6.替换初始化的记录为从数据库中查询的记录
                //7.具体改动代码位置
                List<PeripheralFaultRate> result = new ArrayList<>();
                for (PeripheralFaultRate resultRate : peripheralFaultRatesSum) {
                    PeripheralFaultRate falut = new PeripheralFaultRate();
                    falut.setPeripheralId(resultRate.getPeripheralId());
                    falut.setPeripheralName(resultRate.getPeripheralName());
                    for (PeripheralFaultRate faultRate : peripheralFaultRates) {
                        if (resultRate.getPeripheralId().equals(faultRate.getPeripheralId())) {
                            falut.setSumFaultTimeInt(faultRate.getSumFaultTimeInt());
                            falut.setFaultTimeIntRate(faultRate.getFaultTimeIntRate());
                            break;
                        }
                    }
                    result.add(falut);
                }
                peripheralStartUpTimeGather.setPeripheralFaultRates(result);
            }
        });
        return peripheralStartUpTimeGathers;
    }

    /**
     * 将表头和  相对应的数据进行组装
     *
     * @param infos       表头下的数据集
     * @param devClassKey (大类)设备类型
     * @return List<HistoryHeaderAndinfo>返回组装好的结果
     */
    private List<HistoryHeaderAndinfo> revertInfo(List<HistoryHeaderAndinfo> infos,
                                                  String devClassKey, String branchId) {
        List<PeripheralModel> peripheralModelList =
                this.getPeripheralFaultRateHeader(branchId, devClassKey);
        List<HistoryHeaderAndinfo> headers = new ArrayList<>();
        for (PeripheralModel header : peripheralModelList) {
            HistoryHeaderAndinfo temp = new HistoryHeaderAndinfo();
            temp.setDeviceClassKey(header.getPeripheralId());
            temp.setDeviceClassName(header.getPeripheralName());
            for (HistoryHeaderAndinfo info : infos) {
                if (header.getPeripheralId().equals(info.getDeviceClassKey())) {
                    temp.setMId(info.getMId());
                    temp.setNormalTime(info.getNormalTime());
                    temp.setRate(info.getRate());
                }
            }
            headers.add(temp);
        }
        return headers;
    }

    /**
     * 初始化外设故障率详情集合为tableHeader表头里面所有外设信息
     *
     * @param peripheralStartUpTimeGathers
     * @param tableHeaders
     * @return 初始化完成的集合
     */
    private List<PeripheralFaultRateGatherInfo> init(List<PeripheralFaultRateGatherInfo> peripheralStartUpTimeGathers,
                                                     List<PeripheralModel> tableHeaders) {
        List<PeripheralFaultRate> peripheralFaultRates = new ArrayList<>();
        for (PeripheralModel tableHeader : tableHeaders) {
            peripheralFaultRates.add(PeripheralFaultRate
                    .builder()
                    .peripheralId(tableHeader.getPeripheralId())
                    .peripheralName(tableHeader.getPeripheralName())
                    .sumFaultTimeInt(null)
                    .faultTimeIntRate(null)
                    .build());
        }
        for (PeripheralFaultRateGatherInfo peripheralFaultRateGatherInfo : peripheralStartUpTimeGathers) {
            List<PeripheralFaultRate> temp = new ArrayList<>(peripheralFaultRates);
            peripheralFaultRateGatherInfo.setPeripheralFaultRates(temp);
        }
        return peripheralStartUpTimeGathers;
    }

    @Override
    public List<EquipmentConsumablesInfo> getEquipmentSupplies(
            String branchId,
            EquipmentSuppliesQueryParams queryParams,
            boolean pageOpening) {
        if (pageOpening) {
            PageHelper.startPage(queryParams.getPageNum(), queryParams.getPageSize());
        }
        // 1.查询设备耗材信息
        List<EquipmentConsumablesInfo> equipmentConsumablesInfos =
                historyReportDao.getEquipmentSupplies(branchId, queryParams);
        // 2.查询设备耗材平均值信息
        Float equipmentSuppliesAvg = historyReportDao.selectEquipmentSuppliesAVGInfo(branchId, queryParams);
        // 3.平均使用时间进行对比（高于、低于、持平）
        if (equipmentConsumablesInfos != null && equipmentConsumablesInfos.size() > 0) {
            for (EquipmentConsumablesInfo equipmentConsumablesInfo : equipmentConsumablesInfos) {
                equipmentConsumablesInfo.setAllAvgUseTime(equipmentSuppliesAvg);
                // 设置响应速度对比结果
                equipmentConsumablesInfo.setUseTimeCompareResult(
                        FloatCompareUtil.getCompareResult(equipmentConsumablesInfo.getSelfAvgUseTime(),
                                equipmentSuppliesAvg));
            }
        }
        return equipmentConsumablesInfos;
    }


    @Override
    public List<EquipmentConsumablesInfo> getEquipmentSuppliesGraphicInfo(String branchId,
                                                                          EquipmentSuppliesQueryParams queryParams) {
        return historyReportDao.selectEquipmentSuppliesGraphicInfo(branchId, queryParams);
    }

    @Override
    public ResultWrongAccount getWrongAccountInfo(String branchId,
                                                  WrongAccountParams peripheralParams,
                                                  Boolean isExport) {
        ResultWrongAccount result = new ResultWrongAccount();
        //查询出均值
        Float errorAvg = historyReportDao.getErrorAccountDataAvg(branchId, peripheralParams);
        if (!isExport) {
            //查询出图表信息
            List<HistoryErrotAccountInfo> errorGraph =
                    historyReportDao.getErrorAccountDataForGraph(branchId, peripheralParams);
            result.setData(errorGraph);
            //查询出分页列表的信息
            PageHelper.startPage(peripheralParams.getPageNum(), peripheralParams.getPageSize());
        }
        List<HistoryErrotAccountInfo> errorList =
                historyReportDao.getErrorAccountDataList(branchId, peripheralParams);
        //判断是否 高于均值
        for (HistoryErrotAccountInfo errorAccount : errorList) {
            errorAccount.setDealTimeAvg(errorAvg);
            errorAccount.setDesc(FloatCompareUtil.getCompareResult(errorAccount.getWrongDealTime(), errorAvg));
        }
        if (isExport) {
            //说明  此时只是为了做报表导出方便
            result.setData(errorList);
        } else {
            result.setDataList(new PageInfo<>(errorList));
        }
        return result;
    }

    @Override
    public InputStream exportWrongAccountInfo(String branchId, WrongAccountParams peripheralParams, String title)
            throws ExcelExportException {
        ResultWrongAccount wrongAccountInfo =
                this.getWrongAccountInfo(branchId, peripheralParams, Constance.IS_EXPORT);
        String rangeTime = createRangeTime(
                peripheralParams.getReportType(),
                peripheralParams.getYear(),
                peripheralParams.getQuarter(),
                peripheralParams.getMonth(),
                peripheralParams.getStartDate(),
                peripheralParams.getEndDate());
        return ExcelUtilForMonitor.createExcelFilesWithFirstLevelHeader(
                wrongAccountInfo.getData(), ExcelTemplateConstants.FALSE_ACCOUNTING_TEMPLATE, title, rangeTime);
    }

    @Override
    public InputStream
        exportSatisfactionTellerParamsInfo(String branchId,
                                           SatisfactionTellerParams satisfactionTellerParams,
                                           String title)
            throws ExcelExportException {
        List<SatisfactionTellerInfo> resultList = this.getSatisfactionTellerInfo(branchId, satisfactionTellerParams);
        String rangeTime = createRangeTime(satisfactionTellerParams.getReportType(),
                satisfactionTellerParams.getYear(),
                satisfactionTellerParams.getQuarter(),
                satisfactionTellerParams.getMonth(),
                satisfactionTellerParams.getStartDate(),
                satisfactionTellerParams.getEndDate());
        InputStream inputStream = ExcelUtilForMonitor.createExcelFilesWithFirstLevelHeader(
                resultList,
                ExcelTemplateConstants.SATISFACTION_TELLER_TEMPLATE,
                title, rangeTime);
        return inputStream;
    }

    @Override
    public ResultTranQuality getHistoryTransQualityInfoAndGraphic(
            HistoryTransQualityParams historyTransQualityParams, Boolean isExport) {
        ResultTranQuality result = new ResultTranQuality();
        //查询的均值
        Float tranAvg = historyReportDao.getTranQualityDataAvg(historyTransQualityParams);

        if (!isExport) {
            //查询图表信息
            List<HistoryTransQualityInfo> tranQualityDataForGraph =
                    historyReportDao.getTranQualityDataForGraph(historyTransQualityParams);
            result.setData(tranQualityDataForGraph);
            //开启分页
            PageHelper.startPage(historyTransQualityParams.getPageNum(), historyTransQualityParams.getPageSize());
        }
        //查询列表数据
        List<HistoryTransQualityInfo> tranQualityDataList =
                historyReportDao.getTranQualityDataList(historyTransQualityParams);
        for (HistoryTransQualityInfo info : tranQualityDataList) {
            info.setDealSuccessAvgRate(tranAvg);
            //1修改注释，比较的时候应该用info.getDealSuccessRate() 和 tranAvg去比较
            info.setDesc(FloatCompareUtil.getCompareResult(info.getDealSuccessRate(), tranAvg));
        }
        if (isExport) {
            //说明： 此时表示的不是图表数据，只是方便起见暂时存储全部列表数据,用于导出
            result.setData(tranQualityDataList);
        } else {
            result.setDataList(new PageInfo<>(tranQualityDataList));
        }
        return result;
    }

    @Override
    public InputStream exportTranQuality(HistoryTransQualityParams params, String title)
            throws ExcelExportException {
        ResultTranQuality result = this.getHistoryTransQualityInfoAndGraphic(params, Constance.IS_EXPORT);
        String rangeTime = createRangeTime(
                params.getReportType(),
                params.getReportYear(),
                params.getReportQuarter(),
                params.getReportMonth(),
                params.getReportDateStart(),
                params.getReportDateEnd());
        return ExcelUtilForMonitor.createExcelFilesWithFirstLevelHeader(
                result.getData(), ExcelTemplateConstants.TRANSACTION_QUALITYY_TEMPLATE, title, rangeTime);
    }

    @Override
    public List<HistoryTransQualityInfo> getTranQualityDataForListByBranchDetail(
            HistoryTransQualityParams historyTransQualityParams,
            Boolean isExport) {
        //查询的均值
        Float tranAvg = historyReportDao.getTranQualityDataAvg(historyTransQualityParams);
        if (!isExport) {
            PageHelper.startPage(historyTransQualityParams.getPageNum(), historyTransQualityParams.getPageSize());
        }
        List<HistoryTransQualityInfo> infos =
                historyReportDao.getTranQualityDataForListByBranchDetail(historyTransQualityParams);
        for (HistoryTransQualityInfo info : infos) {
            info.setDealSuccessAvgRate(tranAvg);
            info.setDesc(FloatCompareUtil.getCompareResult(info.getDealSuccessRate(), tranAvg));
            float k = info.getDealSuccessRate() - tranAvg;
        }
        return infos;
    }

    @Override
    public InputStream exportTranQualityDetail(HistoryTransQualityParams params, String title)
            throws ExcelExportException {
        List<HistoryTransQualityInfo> infos =
                this.getTranQualityDataForListByBranchDetail(params, Constance.IS_EXPORT);
        String rangeTime = createRangeTime(
                params.getReportType(),
                params.getReportYear(),
                params.getReportQuarter(),
                params.getReportMonth(),
                params.getReportDateStart(),
                params.getReportDateEnd());
        return ExcelUtilForMonitor.createExcelFilesWithFirstLevelHeader(
                infos, ExcelTemplateConstants.TRANSACTION_QUALITYY_TEMPLATE, title, rangeTime);
    }

    @Override
    public InputStream createExcelWithPeripheralFaultRateGather(String branchId,
                                                                PeripheralFaultRateQueryParams peripheralParams,
                                                                String title) {
        // 1.获取所有外设故障情况报表统计-终端外设故障汇总统计结果
        List<PeripheralFaultRateGatherInfo> peripheralFaultRateGatherInfos =
                this.getPeripheralFaultRateGather(branchId, peripheralParams, false);
        // 2.获取表头数据
        List<PeripheralModel> peripheralModelList =
                this.getPeripheralFaultRateHeader(branchId, peripheralParams.getDevClassKey());
        String statisticalPeriod = createRangeTime(peripheralParams.getReportType(),
                peripheralParams.getYear(),
                peripheralParams.getQuarter(),
                peripheralParams.getMonth(),
                peripheralParams.getStartDate(),
                peripheralParams.getEndDate());
        List<HeaderColInfo> headerColInfos =
                Arrays.asList(HeaderColInfo.builder().key("sumFaultTimeInt").value("外设故障时间").build(),
                        HeaderColInfo.builder().key("faultTimeIntRate").value("外设故障率").build());
        try {
            return ExcelUtilForMonitor.createExcelFilesWithSecondLevelHeader(
                    peripheralFaultRateGatherInfos,
                    ExcelTemplateConstants.PERIPHERAL_FFAULT_GATHER_TEMPLATE,
                    title, statisticalPeriod, headerColInfos, peripheralModelList);
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public InputStream createExcelWithManagersFaultHandlingSpeedInfo(
            String branchId,
            ManagersFaultHandlingSpeedParams queryParams,
            String title) throws ExcelExportException {
        List<ManagersFaultHandlingSpeedInfo> managersFaultHandlingSpeedInfoPageInfo =
                this.getManagersFaultHandlingSpeedInfo(branchId, queryParams, false);
        String rangeTime = createRangeTime(queryParams.getReportType(),
                queryParams.getYear(),
                queryParams.getQuarter(),
                queryParams.getMonth(),
                queryParams.getStartDate(),
                queryParams.getEndDate());
        InputStream inputStream;
        // 判断上送的导出的报表类型是 处理时间-1，响应速度-2
        if (queryParams.getDataType() == Constance.RESOLVE_TIME) {
            List<ManagersSolveTimeInfo> managersSolveTimeInfos =
                    new ArrayList<>(managersFaultHandlingSpeedInfoPageInfo.size());
            managersFaultHandlingSpeedInfoPageInfo.parallelStream().forEach(managersFaultHandlingSpeedInfo -> {
                ManagersSolveTimeInfo managersSolveTimeInfo = new ManagersSolveTimeInfo();
                BeanUtils.copyProperties(managersFaultHandlingSpeedInfo, managersSolveTimeInfo);
                managersSolveTimeInfos.add(managersSolveTimeInfo);
            });
            inputStream = ExcelUtilForMonitor.createExcelFilesWithFirstLevelHeader(
                    managersSolveTimeInfos,
                    ExcelTemplateConstants.MANAGERS_FAULT_RESOLVE_TEMPLATE,
                    title, rangeTime);
        } else {
            List<ManagersResponseTimeInfo> managersResponseTimeInfos =
                    new ArrayList<>(managersFaultHandlingSpeedInfoPageInfo.size());
            managersFaultHandlingSpeedInfoPageInfo.parallelStream().forEach(managersFaultHandlingSpeedInfo -> {
                ManagersResponseTimeInfo managersResponseTimeInfo = new ManagersResponseTimeInfo();
                BeanUtils.copyProperties(managersFaultHandlingSpeedInfo, managersResponseTimeInfo);
                managersResponseTimeInfos.add(managersResponseTimeInfo);
            });
            inputStream = ExcelUtilForMonitor.createExcelFilesWithFirstLevelHeader(
                    managersResponseTimeInfos,
                    ExcelTemplateConstants.MANAGERS_FAULT_RESPONSE_TEMPLATE,
                    title, rangeTime);
        }
        return inputStream;
    }

    @Override
    public InputStream createExcelWithEquipmentSupplies(String branchId,
                                                        EquipmentSuppliesQueryParams queryParams,
                                                        String title) throws ExcelExportException {
        List<EquipmentConsumablesInfo> equipmentConsumablesInfos =
                this.getEquipmentSupplies(branchId, queryParams, false);
        String rangeTime = createRangeTime(queryParams.getReportType(),
                queryParams.getYear(),
                queryParams.getQuarter(),
                queryParams.getMonth(),
                queryParams.getStartDate(),
                queryParams.getEndDate());
        return ExcelUtilForMonitor.createExcelFilesWithFirstLevelHeader(
                equipmentConsumablesInfos,
                ExcelTemplateConstants.EQUIPMENT_CONSUMABLES_TEMPLATE,
                title, rangeTime);
    }

    @Override
    public InputStream createExcelWithHistroyReportInfo(String branchId,
                                                        HistoryReportParams historyReportParams,
                                                        String title) throws ExcelExportException {
        List<HistoryReportInfo> resultList = this.getHistroyReportInfo(branchId, historyReportParams);
        String rangeTime = createRangeTime(historyReportParams.getReportType(),
                historyReportParams.getYear(),
                historyReportParams.getQuarter(),
                historyReportParams.getMonth(),
                historyReportParams.getStartDate(),
                historyReportParams.getEndDate());
        InputStream inputStream;
        String templateUrl;
        if (Constance.NORMAL.equals(historyReportParams.getDataType())) {
            templateUrl = ExcelTemplateConstants.EQUIPMENT_FAILURE_TEMPLATE;
        } else if (Constance.TOOL.equals(historyReportParams.getDataType())) {
            templateUrl = ExcelTemplateConstants.NETWORK_FAILURE_RATE_TEMPLATE;
        } else {
            templateUrl = ExcelTemplateConstants.DEVICE_START_INFO_TEMPLATE;
        }
        inputStream = ExcelUtilForMonitor.createExcelFilesWithFirstLevelHeader(
                resultList,
                templateUrl,
                title, rangeTime);
        return inputStream;
    }

    @Override
    public List<SatisfactionTellerInfo>
                getSatisfactionTellerInfo(String branchId, SatisfactionTellerParams satisfactionTellerParams) {
        if (!StringUtils.isEmpty(satisfactionTellerParams.getPageSize())) {
            PageHelper.startPage(satisfactionTellerParams.getPageNum(), satisfactionTellerParams.getPageSize());
        }
        List<SatisfactionTellerInfo> satisfactionTellerInfos =
                historyReportDao.getSatisfactionTellerInfoList(branchId, satisfactionTellerParams);
        return satisfactionTellerInfos;

    }

    @Override
    public List<SatisfactionOrgReportInfo>
            getsatisfactionOrgReportInfo(String branchId, SatisfactionOrgReportParams satisfactionOrgReportParams) {
        if (!StringUtils.isEmpty(satisfactionOrgReportParams.getPageSize())) {
            PageHelper.startPage(satisfactionOrgReportParams.getPageNum(), satisfactionOrgReportParams.getPageSize());
        }
        List<SatisfactionOrgReportInfo> satisfactionOrgReportInfos =
                historyReportDao.getsatisfactionOrgReportInfoList(branchId, satisfactionOrgReportParams);
        return satisfactionOrgReportInfos;

    }

    @Override
    public InputStream
            exportSatisfactionOrgReportInfo(String branchId,
                                            SatisfactionOrgReportParams satisfactionOrgReportParams,
                                            String title)
                    throws ExcelExportException {
        List<SatisfactionOrgReportInfo>
                resultList = this.getsatisfactionOrgReportInfo(branchId, satisfactionOrgReportParams);
        String rangeTime = createRangeTime(satisfactionOrgReportParams.getReportType(),
                satisfactionOrgReportParams.getYear(),
                satisfactionOrgReportParams.getQuarter(),
                satisfactionOrgReportParams.getMonth(),
                satisfactionOrgReportParams.getStartDate(),
                satisfactionOrgReportParams.getEndDate());
        InputStream inputStream = ExcelUtilForMonitor.createExcelFilesWithFirstLevelHeader(
                resultList,
                ExcelTemplateConstants.SATISFACTION_ORG_TEMPLATE,
                title, rangeTime);
        return inputStream;
    }



    @Override
    public List<TradeTimeZhInfo> getTradeTimeZhInfo(String branchId, TradeTimeZhParams tradeTimeZhParams) {
        if (!StringUtils.isEmpty(tradeTimeZhParams.getPageSize())) {
            PageHelper.startPage(tradeTimeZhParams.getPageNum(), tradeTimeZhParams.getPageSize());
        }
        List<TradeTimeZhInfo> tradeTimeLeaderInfos =
                historyReportDao.getzhtradetimeInfoList(branchId, tradeTimeZhParams);
        return tradeTimeLeaderInfos;
    }

    @Override
    public InputStream
            exportTradeTimeZHParamsInfo(String branchId,
                                        TradeTimeZhParams tradeTimeZhParams,
                                        String title) throws Exception {
        List<TradeTimeZhInfo> resultList = this.getTradeTimeZhInfo(branchId, tradeTimeZhParams);
        String rangeTime = createRangeTime(tradeTimeZhParams.getReportType(),
                tradeTimeZhParams.getYear(),
                tradeTimeZhParams.getQuarter(),
                tradeTimeZhParams.getMonth(),
                tradeTimeZhParams.getStartDate(),
                tradeTimeZhParams.getEndDate());
        InputStream inputStream = ExcelUtilForMonitor.createExcelFilesWithFirstLevelHeader(
                resultList,
                ExcelTemplateConstants.ZH_VIEW_INFO_TEMPLATE,
                title, rangeTime);
        return inputStream;
    }



    @Override
    public List<TradeTimeLeaderInfo>
            getTradeTimeLeaderInfo(String branchId, TradeTimeLeaderParams tradeTimeLeaderParams) {
        if (!StringUtils.isEmpty(tradeTimeLeaderParams.getPageSize())) {
            PageHelper.startPage(tradeTimeLeaderParams.getPageNum(), tradeTimeLeaderParams.getPageSize());
        }
        List<TradeTimeLeaderInfo> tradeTimeLeaderInfos =
                historyReportDao.getleadertradetimeInfoList(branchId, tradeTimeLeaderParams);
        return tradeTimeLeaderInfos;
    }


    @Override
    public InputStream
            exportTradeTimeLeaderParamsInfo(String branchId,
                                            TradeTimeLeaderParams tradeTimeLeaderParams,
                                            String title) throws Exception {
        List<TradeTimeLeaderInfo> resultList = this.getTradeTimeLeaderInfo(branchId, tradeTimeLeaderParams);
        String rangeTime = createRangeTime(tradeTimeLeaderParams.getReportType(),
                tradeTimeLeaderParams.getYear(),
                tradeTimeLeaderParams.getQuarter(),
                tradeTimeLeaderParams.getMonth(),
                tradeTimeLeaderParams.getStartDate(),
                tradeTimeLeaderParams.getEndDate());
        InputStream inputStream = ExcelUtilForMonitor.createExcelFilesWithFirstLevelHeader(
                resultList,
                ExcelTemplateConstants.LEADER_VIEW_INFO_TEMPLATE,
                title, rangeTime);
        return inputStream;
    }

    @Override
    public List<QuWaitTimeInfo>
                getQuWaitTimeInfo(String branchId, QuWaitTimeParams quWaitTimeParams) {
        if (!StringUtils.isEmpty(quWaitTimeParams.getPageSize())) {
            PageHelper.startPage(quWaitTimeParams.getPageNum(), quWaitTimeParams.getPageSize());
        }
        List<QuWaitTimeInfo> quWaitTimeInfos =
                historyReportDao.getquWaitTimeInfoList(branchId, quWaitTimeParams);
        return quWaitTimeInfos;
    }


    @Override
    public InputStream
                exportQuWaitTimeParamsInfo(String branchId,
                                           QuWaitTimeParams quWaitTimeParams,
                                           String title) throws Exception {
        List<QuWaitTimeInfo> resultList = this.getQuWaitTimeInfo(branchId, quWaitTimeParams);
        String rangeTime = createRangeTime(quWaitTimeParams.getReportType(),
                quWaitTimeParams.getYear(),
                quWaitTimeParams.getQuarter(),
                quWaitTimeParams.getMonth(),
                quWaitTimeParams.getStartDate(),
                quWaitTimeParams.getEndDate());
        InputStream inputStream = ExcelUtilForMonitor.createExcelFilesWithFirstLevelHeader(
                resultList,
                ExcelTemplateConstants.QU_WAITTIME_INFO_TEMPLATE,
                title, rangeTime);
        return inputStream;
    }


    @Override
    public List<WaittingTimebranchReportInfo>
                getWaittingTimebranchReportInfo(String branchId, WaittingTimebranchParams waittingTimebranchParams) {
        if (!StringUtils.isEmpty(waittingTimebranchParams.getPageSize())) {
            PageHelper.startPage(waittingTimebranchParams.getPageNum(), waittingTimebranchParams.getPageSize());
        }
        List<WaittingTimebranchReportInfo> waittingTimebranchReportInfos =
                historyReportDao.getwaittingTimebranchReportInfoList(branchId, waittingTimebranchParams);
        return waittingTimebranchReportInfos;
    }

    @Override
    public InputStream exportwaittingTimebranc(
            String branchId,
            WaittingTimebranchParams waittingTimebranchParams,
            String title) throws ExcelExportException {
        List<WaittingTimebranchReportInfo>
                resultList = this.getWaittingTimebranchReportInfo(branchId, waittingTimebranchParams);
        String rangeTime = createRangeTime(
                waittingTimebranchParams.getReportType(),
                waittingTimebranchParams.getYear(),
                waittingTimebranchParams.getQuarter(),
                waittingTimebranchParams.getMonth(),
                waittingTimebranchParams.getStartDate(),
                waittingTimebranchParams.getEndDate());
        InputStream inputStream = ExcelUtilForMonitor.createExcelFilesWithFirstLevelHeader(
                resultList,
                ExcelTemplateConstants.BRANCH_WAIT_TIME_TEMPLATE,
                title, rangeTime);
        return inputStream;
    }


    @Override
    public List<ServiceTimeReportInfo>
            getServiceTimeReportInfo(String branchId, ServiceTimeParams serviceTimeParams) {
        if (!StringUtils.isEmpty(serviceTimeParams.getPageSize())) {
            PageHelper.startPage(serviceTimeParams.getPageNum(), serviceTimeParams.getPageSize());
        }
        List<ServiceTimeReportInfo> serviceTimeReportInfos =
                historyReportDao.getServiceTimeReportInfoList(branchId, serviceTimeParams);
        return serviceTimeReportInfos;
    }

    @Override
    public InputStream exportserviceTime(
            String branchId,
            ServiceTimeParams serviceTimeParams,
            String title) throws ExcelExportException {
        List<ServiceTimeReportInfo> resultList = this.getServiceTimeReportInfo(branchId, serviceTimeParams);
        String rangeTime = createRangeTime(
                serviceTimeParams.getReportType(),
                serviceTimeParams.getYear(),
                serviceTimeParams.getQuarter(),
                serviceTimeParams.getMonth(),
                serviceTimeParams.getStartDate(),
                serviceTimeParams.getEndDate());
        InputStream inputStream = ExcelUtilForMonitor.createExcelFilesWithFirstLevelHeader(
                resultList,
                ExcelTemplateConstants.TIME_SERVICE_TEMPLATE,
                title, rangeTime);
        return inputStream;
    }


    @Override
    public List<TradeTimeCounterClerkReportInfo>
            getTradeTimeCounterClerkReportInfo(String branchId,
                                               TradetimecounterclerkParams tradetimecounterclerkParams) {
        if (!StringUtils.isEmpty(tradetimecounterclerkParams.getPageSize())) {
            PageHelper.startPage(tradetimecounterclerkParams.getPageNum(),
                    tradetimecounterclerkParams.getPageSize());
        }
        List<TradeTimeCounterClerkReportInfo> tradeTimeCounterClerkReportInfos =
                historyReportDao.getTradeTimeCounterClerkReportInfoList(branchId, tradetimecounterclerkParams);
        return tradeTimeCounterClerkReportInfos;
    }

    @Override
    public InputStream exporttradetimecounterclerk(
            String branchId,
            TradetimecounterclerkParams tradetimecounterclerkParams,
            String title) throws ExcelExportException {
        List<TradeTimeCounterClerkReportInfo>
                resultList = this.getTradeTimeCounterClerkReportInfo(branchId, tradetimecounterclerkParams);
        String rangeTime = createRangeTime(
                tradetimecounterclerkParams.getReportType(),
                tradetimecounterclerkParams.getYear(),
                tradetimecounterclerkParams.getQuarter(),
                tradetimecounterclerkParams.getMonth(),
                tradetimecounterclerkParams.getStartDate(),
                tradetimecounterclerkParams.getEndDate());
        InputStream inputStream = ExcelUtilForMonitor.createExcelFilesWithFirstLevelHeader(
                resultList,
                ExcelTemplateConstants.TRADETIME_COUNTERCLERK_TEMPLATE,
                title, rangeTime);
        return inputStream;
    }


    @Override
    public ClientFlowReportInfo getClientFlowGraphic(String branchNo, ClientFlowReportParams clientFlowReportParams) {
        //查询横坐标的时间段
        List<ClientFlowTimeInfo> time = historyReportDao.getTime(branchNo);
        //查询纵坐标票号数
        List<ClientFlowTimezInfo> ticket = historyReportDao.queryCount(branchNo);
//        //数据信息
//        List<ClientFlowReportInfo> historyReportInfos = historyReportDao.getClientTimeGraphic(branchId,
//        clientFlowReportParams);
//
//        for (ClientFlowTimezInfo minfo : manuf) {
//            List<ClientFlowTimezInfo> mdata = new ArrayList<>();
//            for (ClientFlowReportInfo dataInfo : clientFlowReportParams) {
//                if (minfo.getReceiveCount().equals(dataInfo.getUnitName())) {
//                    //获得每种厂商下所有设备的值
//                    ClientFlowTimezInfo data = new ClientFlowTimezInfo();
//                    data.setReceiveCount(dataInfo.getBranchNo());
//                    mdata.add(data);
//                }
//            }
//            minfo.setBranchNo(manuf);
//        }
        ClientFlowReportInfo info = new ClientFlowReportInfo();
        info.setApplyTimeStep(time);
        info.setReceiveCount(ticket);
        return info;
    }

    @Override
    public List<TradeTimeBranchReportInfo>
            getTradeTimeBranchReportInfo(String branchId, TradeTimeBranchParams tradeTimeBranchParams) {
        if (!StringUtils.isEmpty(tradeTimeBranchParams.getPageSize())) {
            PageHelper.startPage(tradeTimeBranchParams.getPageNum(), tradeTimeBranchParams.getPageSize());
        }
        List<TradeTimeBranchReportInfo> tradeTimeBranchReportInfos =
                historyReportDao.getTradeTimeBranchReportInfoList(branchId, tradeTimeBranchParams);
        return tradeTimeBranchReportInfos;
    }

    @Override
    public InputStream exporttradeTimeBranch(
            String branchId,
            TradeTimeBranchParams tradeTimeBranchParams,
            String title) throws ExcelExportException {
        List<TradeTimeBranchReportInfo> resultList = this.getTradeTimeBranchReportInfo(branchId, tradeTimeBranchParams);
        String rangeTime = createRangeTime(
                tradeTimeBranchParams.getReportType(),
                tradeTimeBranchParams.getYear(),
                tradeTimeBranchParams.getQuarter(),
                tradeTimeBranchParams.getMonth(),
                tradeTimeBranchParams.getStartDate(),
                tradeTimeBranchParams.getEndDate());
        InputStream inputStream = ExcelUtilForMonitor.createExcelFilesWithFirstLevelHeader(
                resultList,
                ExcelTemplateConstants.TIME_TRADE_TEMPLATE,
                title, rangeTime);
        return inputStream;
    }




    @Override
    public StringBuilder getOrgReportTitle(String branchId) {
        List<String> orgList = historyReportDao.selectOrgReportTitle(branchId);
        StringBuilder reportTitle = new StringBuilder(64);
        orgList.forEach(orgStr -> reportTitle.append(orgStr));
        return reportTitle;
    }

    /**
     * 报表导出的时间段，根据reportType（day，month，quarter，year）给出对应时间段
     * 1.day --- 例如：2018年07月07日 至 2018年07月07日
     * 2.month --- 例如：2018年07月
     * 3.quarter --- 例如：2018年第3季度
     * 3.year --- 例如：2019年
     *
     * @param reportType 报表周期
     * @param year       年份
     * @param quarter    季度
     * @param month      月份
     * @param beginDate  开始时间
     * @param endDate    结束时间
     * @return 导出的时间段
     */
    private static String createRangeTime(String reportType,
                                          String year,
                                          String quarter,
                                          String month,
                                          String beginDate,
                                          String endDate) {
        StringBuilder statisticalPeriod = new StringBuilder(16);
        switch (reportType) {
            case DAY:
                statisticalPeriod = statisticalPeriod.append(DateUtil.getStrChineseDates(beginDate))
                        .append(" 至 ").append(DateUtil.getStrChineseDates(endDate));
                break;
            case MONTH:
                statisticalPeriod = statisticalPeriod.append(year).append("年").append(month).append("月");
                break;
            case QUARTER:
                statisticalPeriod = statisticalPeriod.append(year).append("年第").append(quarter).append("季度");
                break;
            case YEAR:
                statisticalPeriod = statisticalPeriod.append(year).append("年");
                break;
            default:
                break;
        }
        return statisticalPeriod.toString();
    }



}
