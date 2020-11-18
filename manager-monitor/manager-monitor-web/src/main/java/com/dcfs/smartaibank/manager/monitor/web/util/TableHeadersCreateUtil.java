package com.dcfs.smartaibank.manager.monitor.web.util;

import com.dcfs.smartaibank.manager.monitor.web.domian.PeripheralModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangjzm
 * @data 2019/07/23 16:12
 * @since 1.0.0
 */
public final class TableHeadersCreateUtil {
    private TableHeadersCreateUtil() {
    }

    private static final String ATM = "120000";
    private static final String STM = "130000";
    private static final String FILLER = "667";
    private static final String QUEUE = "100000";
    private static List<PeripheralModel> atmTableHeaders, stmTableHeaders, fillerTableHeaders, queueTableHeaders;

    static {
        atmTableHeaders = Arrays.asList(
                PeripheralModel.builder().peripheralId("X27").peripheralName("现金模块").build(),
                PeripheralModel.builder().peripheralId("X02").peripheralName("读卡器（IC卡，磁条卡）").build(),
                PeripheralModel.builder().peripheralId("30").peripheralName("密码键盘").build(),
                PeripheralModel.builder().peripheralId("X18").peripheralName("凭条打印机").build(),
                PeripheralModel.builder().peripheralId("X29").peripheralName("读卡器（非接）").build()
        );
        stmTableHeaders = Arrays.asList(
                PeripheralModel.builder().peripheralId("X14").peripheralName("发卡机设备").build(),
                PeripheralModel.builder().peripheralId("X26").peripheralName("发UKEY器").build(),
                PeripheralModel.builder().peripheralId("X17").peripheralName("读卡器(身份证)").build(),
                PeripheralModel.builder().peripheralId("30").peripheralName("密码键盘").build(),
                PeripheralModel.builder().peripheralId("X18").peripheralName("凭条打印机").build(),
                PeripheralModel.builder().peripheralId("X27").peripheralName("现金模块").build()
        );

        fillerTableHeaders = Arrays.asList(
                PeripheralModel.builder().peripheralId("X19").peripheralName("现金模块").build(),
                PeripheralModel.builder().peripheralId("X02").peripheralName("读卡器（IC卡，磁条卡）").build(),
                PeripheralModel.builder().peripheralId("30").peripheralName("密码键盘").build()
        );
        queueTableHeaders = Arrays.asList(
                PeripheralModel.builder().peripheralId("20").peripheralName("打印机").build(),
                PeripheralModel.builder().peripheralId("60").peripheralName("接触式IC卡读写器").build(),
                PeripheralModel.builder().peripheralId("70").peripheralName("身份证识别器").build(),
                PeripheralModel.builder().peripheralId("40").peripheralName("磁条读写器").build(),
                PeripheralModel.builder().peripheralId("150").peripheralName("语音提示器").build(),
                PeripheralModel.builder().peripheralId("140").peripheralName("交互式屏幕").build()
        );
    }

    /**
     * 根据设备类型查询外设模块信息
     *
     * @param deviceClassKey 设备类型
     * @return 外设信息集合
     */
    public static List<PeripheralModel> createTableHeader(String deviceClassKey) {
        List<PeripheralModel> list;
        switch (deviceClassKey) {
            case ATM:
                list = new ArrayList<>(atmTableHeaders);
                break;
            case STM:
                list = new ArrayList<>(stmTableHeaders);
                break;
            case QUEUE:
                list = new ArrayList<>(queueTableHeaders);
                break;
            case FILLER:
                list = new ArrayList<>(fillerTableHeaders);
                break;
            default:
                list = new ArrayList<>();
                break;
        }
        return list;
    }

}
