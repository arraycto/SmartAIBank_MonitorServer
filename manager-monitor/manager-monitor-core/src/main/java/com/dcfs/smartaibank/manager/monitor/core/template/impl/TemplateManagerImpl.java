package com.dcfs.smartaibank.manager.monitor.core.template.impl;

import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.exception.TemplateException;
import com.dcfs.smartaibank.manager.monitor.core.template.Template;
import com.dcfs.smartaibank.manager.monitor.core.template.TemplateDefine;
import com.dcfs.smartaibank.manager.monitor.core.template.TemplateManager;
import com.dcfs.smartaibank.manager.monitor.core.template.TemplateType;

import java.util.HashMap;
import java.util.Map;

/**
 * 模板管理实现类
 *
 * @author jiazw
 */
public class TemplateManagerImpl implements TemplateManager {
	private Map<TemplateType, Template> templates = new HashMap<>();

	/**
	 * 根据模板类型获取模板
	 *
	 * @param type 模板类型
	 * @return 模板
	 */
	@Override
	public Template getTemplate(TemplateType type) {
		return this.templates.get(type);
	}

	/**
	 * 模板渲染
	 *
	 * @param context 上下文
	 * @param define  模板定义
	 * @return 渲染后结果
	 */
	@Override
	public String render(MonitorContext context, TemplateDefine define) throws TemplateException {
		if (define != null) {
			Template template = this.templates.get(define.getType());
			if (template != null) {
				return template.render(context, define);
			}
		}
		return null;
	}

	/**
	 * 添加模板
	 *
	 * @param type     模板类型
	 * @param template 模板
	 */
	public void addTemplate(TemplateType type, Template template) {
		this.templates.put(type, template);
	}
}
