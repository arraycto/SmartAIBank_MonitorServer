package com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern.TemplateVarConverter;

/**
 * 日期变量转换器
 *
 * @author jiazw
 */
public class DateTemplateVarConverter implements TemplateVarConverter {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 变量转换
     *
     * @param date 源数据
     * @return 转换后结果
     */
    @Override
    public String convert(Object date) {
        return date != null && date instanceof Date ? format.format(date) : "";
    }
}
