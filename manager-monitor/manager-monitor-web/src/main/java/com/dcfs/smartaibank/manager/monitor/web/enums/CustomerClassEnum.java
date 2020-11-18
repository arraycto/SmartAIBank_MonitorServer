package com.dcfs.smartaibank.manager.monitor.web.enums;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 客户类别（公司客户、个人客户）
 *
 * @author wangjzm
 * @data 2019/06/19 15.01
 * @since 1.0.0
 */
public enum CustomerClassEnum implements CodeDescEnum<String, String> {
    /**
     * 公司客户
     */
    COMPANY("0", "公司客户"),
    /**
     * 个人客户
     */
    PERSONAL("1", "个人客户");

    String code;
    String desc;

    CustomerClassEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
