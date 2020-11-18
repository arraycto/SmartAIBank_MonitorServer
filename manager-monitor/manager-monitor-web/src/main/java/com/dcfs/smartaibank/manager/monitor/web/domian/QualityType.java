package com.dcfs.smartaibank.manager.monitor.web.domian;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 服务质量类别
 *
 * @author jiazw
 */
public enum QualityType implements CodeDescEnum<String, String> {
    /**
     * 优
     */
    GOOD("4", "优"),

    /**
     * 良
     */
    FINE("3", "良"),

    /**
     * 中
     */
    MEDIUM("2", "中"),

    /**
     * 差
     */
    BAD("1", "差");

    String code;
    String desc;

    QualityType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
