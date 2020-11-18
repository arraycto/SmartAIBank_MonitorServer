package com.dcfs.smartaibank.manager.monitor.web.enums;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 设备详情列表排序规则枚举类
 *
 * @author wangjzm
 * @data 2019/06/19 15.01
 * @since 1.0.0
 */
public enum DeviceDetailsSortRuleEnum implements CodeDescEnum<String, String> {
    /**
     * 设备总状态排序
     */
    TOTAL_STATUS("1", "设备总状态排序"),
    /**
     * 网络通讯状态排序
     */
    COMM_STATUS("2", "网络通讯状态排序"),
    /**
     * 应用运行状态排序
     */
    APP_STATUS("3", "应用运行状态排序"),
    /**
     * 外设运行状态排序
     */
    PERIPHERAL_STATUS("4", "外设运行状态排序");

    String code;
    String desc;

    DeviceDetailsSortRuleEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 比较枚举值是否相等
     *
     * @param enumName 枚举名
     * @param ruleEnum 枚举
     * @return 相等-true，否则-false
     */
    public static boolean isEquals(String enumName, DeviceDetailsSortRuleEnum ruleEnum) {
        if (ruleEnum == null) {
            return false;
        }
        return enumName.equals(ruleEnum.name());
    }
}
