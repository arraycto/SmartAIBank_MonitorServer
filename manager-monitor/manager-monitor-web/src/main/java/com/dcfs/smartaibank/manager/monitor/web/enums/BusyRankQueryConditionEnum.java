package com.dcfs.smartaibank.manager.monitor.web.enums;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;
import org.springframework.util.StringUtils;

/**
 * 繁忙度排名查询条件枚举类
 *
 * @author wangjzm
 * @data 2019/06/19 15.01
 * @since 1.0.0
 */
public enum BusyRankQueryConditionEnum implements CodeDescEnum<Integer, String> {
    /**
     * 等待客户数
     */
    WAIT_COUNT(1, "等待客户数"),
    /**
     * 客户平均等待时间
     */
    AVG_WAIT_TIME(2, "客户平均等待时间");

    Integer code;
    String desc;

    BusyRankQueryConditionEnum(Integer code, String desc) {
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
    public static boolean isEquals(String enumName, BusyRankQueryConditionEnum ruleEnum) {
        if (ruleEnum == null || StringUtils.isEmpty(enumName)) {
            return false;
        }
        return enumName.equals(ruleEnum.name());
    }
}
