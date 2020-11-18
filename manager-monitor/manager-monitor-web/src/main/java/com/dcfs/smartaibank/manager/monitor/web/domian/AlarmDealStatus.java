package com.dcfs.smartaibank.manager.monitor.web.domian;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * @author tanchen1
 * @date 2019/6/20 9:40
 * @since
 */
public enum AlarmDealStatus implements CodeDescEnum<String, String> {

    /**
     * 未处理
     */
    UNDEAL("1", "未处理"),

    /**
     * 处理中
     */
    DEAL("2", "处理中"),

    /**
     * 已报修
     */
    REPAIRS("3", "已报修"),

    /**
     * 已解除
     */
    RELIEVE("4", "已解除"),

    /**
     * 已关闭
     */
    CLOSED("5", "已关闭"),

    /**
     * 已挂起
     */
    SUBMIT("6", "已挂起");

    String code;
    String desc;

    AlarmDealStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    /**
     * 比较枚举值是否相等
     *
     * @param enumName 枚举名
     * @param alarmDealStatus 枚举
     * @return 相等-true，否则-false
     */
    public static boolean isEquals(String enumName, AlarmDealStatus alarmDealStatus) {
        if (alarmDealStatus == null) {
            return false;
        }
        return enumName.equals(alarmDealStatus.name());
    }

}
