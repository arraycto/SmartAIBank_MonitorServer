package com.dcfs.smartaibank.manager.monitor.web.enums;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * @author wangtingo
 * @date 2019/6/26 9:35
 * @since
 */
public enum AlarmCloseType implements CodeDescEnum<String, String> {

    /**
     * 自动关闭
     */
    AUTOMATIC("1", "自动关闭"),

    /**
     * 登记关闭
     */
    REGISTRATION("2", "登记关闭");


    String code;
    String desc;

    AlarmCloseType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
