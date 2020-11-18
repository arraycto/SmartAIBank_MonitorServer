package com.dcfs.smartaibank.manager.monitor.core.template;

/**
 * 模板管理器
 *
 * @author jiazw
 */
public interface TemplateManager extends Template {
    /**
     * 根据模板类型获取模板
     *
     * @param type 模板类型
     * @return 模板
     */
    Template getTemplate(TemplateType type);
}
