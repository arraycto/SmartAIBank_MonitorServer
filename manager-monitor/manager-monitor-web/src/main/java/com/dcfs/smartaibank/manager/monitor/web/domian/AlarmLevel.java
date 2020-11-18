package com.dcfs.smartaibank.manager.monitor.web.domian;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * @author tanchen1
 * @date 2019/6/20 9:35
 * @since
 */
public enum AlarmLevel implements CodeDescEnum<String, String> {

    /**
     * 黄色预警
     */
    YELLOW("1", "黄色预警"),

    /**
     * 橙色预警
     */
    ORANGE("2", "橙色预警"),

    /**
     * 红色预警
     */
    RED("3", "红色预警");

    String code;
    String desc;

    AlarmLevel(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
