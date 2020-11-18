package com.dcfs.smartaibank.manager.base.domain;


import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 性别
 *
 * @author jiazw
 */
public enum Gender implements CodeDescEnum<String, String> {
    /**
     * 男
     */
    MALE("M", "男"),

    /**
     * 女
     */
    FEMALE("F", "女"),

    /**
     * 未知
     */
    UNKNOWN("U", "未知");

    String code;
    String desc;

    Gender(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
