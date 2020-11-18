package com.dcfs.smartaibank.manager.monitor.core.template;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 模板定义
 *
 * @author jiazw
 */
@NoArgsConstructor
@Setter
@Getter
public class TemplateDefine {
    /**
     * 模板ID
     */
    private String id;
    /**
     * 模板名称
     */
    private String name;
    /**
     * 模板描述
     */
    private String description;
    /**
     * 模板类型
     */
    private TemplateType type;
    /**
     * 模板内容
     */
    private String content;
}
