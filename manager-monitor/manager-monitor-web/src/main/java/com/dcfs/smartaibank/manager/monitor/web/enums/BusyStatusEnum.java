package com.dcfs.smartaibank.manager.monitor.web.enums;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;
import org.springframework.util.StringUtils;

/**
 * 繁忙度状态枚举类
 *
 * @author wangjzm
 * @data 2019/06/19 15.01
 * @since 1.0.0
 */
public enum BusyStatusEnum implements CodeDescEnum<String, String> {
    /**
     * 未营业
     */
    UNOPENED("0", "未营业"),

    /**
     * 空闲
     */
    IDLE("1", "空闲"),

    /**
     * 正常
     */
    NORMAL("2", "正常"),

    /**
     * 忙碌
     */
    BUSY("3", "忙碌"),

    /**
     * 饱和
     */
    SATURATION("4", "饱和"),

    /**
     * 全部
     */
    ALL("5", "全部");

    String code;
    String desc;

    BusyStatusEnum(String code, String desc) {
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
    public static boolean isEquals(String enumName, BusyStatusEnum ruleEnum) {
        if (ruleEnum == null || StringUtils.isEmpty(enumName)) {
            return false;
        }
        for (BusyStatusEnum busyStatusEnum : BusyStatusEnum.values()) {
            if (enumName.equals(busyStatusEnum.name())) {
                BusyStatusEnum convertEnum
                        = BusyStatusEnum.valueOf(BusyStatusEnum.class, enumName);
                return ruleEnum.getCode().equals(convertEnum.getCode());
            }
        }
        return false;
    }

}
