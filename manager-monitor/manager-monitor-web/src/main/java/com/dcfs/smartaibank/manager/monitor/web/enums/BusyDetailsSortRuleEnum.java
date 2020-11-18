package com.dcfs.smartaibank.manager.monitor.web.enums;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;
import org.springframework.util.StringUtils;

/**
 * 繁忙度详情列表排序规则枚举类
 *
 * @author wangjzm
 * @data 2019/06/19 15.01
 * @since 1.0.0
 */
public enum BusyDetailsSortRuleEnum implements CodeDescEnum {
    /**
     * 繁忙度状态排序
     */
    BUSY_STATUS("1", "繁忙度状态"),
    /**
     * 等待客户数排序
     */
    WAIT_COUNT("2", "等待客户数排序"),
    /**
     * 窗口平均等待客户数排序
     */
    AVG_WAIT_COUNT_WIN("3", "窗口平均等待客户数排序"),
    /**
     * 客户平均等待时间排序
     */
    AVG_WAIT_TIME("4", "客户平均等待时间排序"),
    /**
     * 客户最长等待时间排序
     */
    MAX_WAIT_TIME("5", "客户平均等待时间排序");

    String code;
    String desc;

    BusyDetailsSortRuleEnum(String code, String desc) {
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
    public static boolean isEquals(String enumName, BusyDetailsSortRuleEnum ruleEnum) {
        if (ruleEnum == null || StringUtils.isEmpty(enumName)) {
            return false;
        }
        for (BusyDetailsSortRuleEnum busyDetailsSortRuleEnum : BusyDetailsSortRuleEnum.values()) {
            if (enumName.equals(busyDetailsSortRuleEnum.name())) {
                BusyDetailsSortRuleEnum convertEnum
                        = BusyDetailsSortRuleEnum.valueOf(BusyDetailsSortRuleEnum.class, enumName);
                return ruleEnum.getCode().equals(convertEnum.getCode());
            }
        }
        return false;
    }

}
