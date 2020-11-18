package com.dcfs.smartaibank.manager.monitor.web.domian;


import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * @author tanchen1
 * @date 2019/6/20 9:40
 * @since
 */
public enum ShowFlag implements CodeDescEnum<String, String> {
    /**
     * 启用
     */
    ENABLED("0", "启用"),

    /**
     * 停用
     */
    DISABLED("1", "停用");

    String code;
    String desc;

    ShowFlag(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
