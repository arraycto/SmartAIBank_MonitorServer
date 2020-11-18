package com.dcfs.smartaibank.manager.monitor.core.common;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 主键类型
 *
 * @author jiazw
 */
public enum PrimaryKeyType implements CodeDescEnum<Integer, String> {
    /**
     * 预警
     */
    ALARM(1, "预警");
    Integer code;
    String desc;

    PrimaryKeyType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
