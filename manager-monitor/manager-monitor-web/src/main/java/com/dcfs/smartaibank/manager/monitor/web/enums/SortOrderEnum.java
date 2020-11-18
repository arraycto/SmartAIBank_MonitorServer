package com.dcfs.smartaibank.manager.monitor.web.enums;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 设备详情列表排序顺序枚举类
 *
 * @author wangjzm
 * @data 2019/06/19 15.01
 * @since 1.0.0
 */
public enum SortOrderEnum implements CodeDescEnum<String, String> {
    /**
     * 设备总状态排序
     */
    DESC("1", "降序"),
    /**
     * 网络通讯状态排序
     */
    ASC("2", "升序");
    String code;
    String desc;

    SortOrderEnum(String code, String desc) {
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
    public static boolean isEquals(String enumName, SortOrderEnum ruleEnum) {
        if (ruleEnum == null) {
            return false;
        }
        return enumName.equals(ruleEnum.name());
    }
}
