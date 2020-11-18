package com.dcfs.smartaibank.manager.monitor.web.enums;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 客户类型（VIP客户、普通客户）
 *
 * @author wangjzm
 * @data 2019/06/19 15.01
 * @since 1.0.0
 */
public enum CustomerTypeEnum implements CodeDescEnum<String, String> {
    /**
     * VIP客户
     */
    VIP("01", "VIP客户"),
    /**
     * 普通客户
     */
    CUSTOM("02", "普通客户");

    String code;
    String desc;

    CustomerTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
