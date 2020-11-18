package com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern;

/**
 * 转换器
 *
 * @author jiazw
 */
public interface TemplateVarConverter {

    /**
     * 变量转换
     *
     * @param obj 源数据
     * @return 转换后结果
     */
    String convert(Object obj);
}
