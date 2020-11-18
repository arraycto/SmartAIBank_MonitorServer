package com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern;

import com.dcfs.smartaibank.manager.monitor.core.template.Template;

import java.util.Map;

/**
 * 模式匹配模板
 *
 * @author jiazw
 */
public interface PatternTemplate extends Template {

	/**
	 * 设置正则表达式,如果不想用默认正则表达式，可以通过此方法自行定义
	 *
	 * @param patternHandle 模式处理器
	 */
	void setPatternHandle(TemplatePatternDefine patternHandle);

	/**
	 * 添加模板变量提供者
	 *
	 * @param varProvider 变量提供者
	 */
	void setTemplateVarProviders(Map<String, TemplateVarProvider> varProvider);

	/**
	 * 添加模板变量提供者
	 *
	 * @param prefix         前缀
	 * @param formatProvider 模板变量提供者
	 */
	void addTemplateVarProvider(String prefix, TemplateVarProvider formatProvider);

	/**
	 * 添加模板变量转换器
	 *
	 * @param converters 模板变量转换器
	 */
	void setTemplateVarConverters(Map<Class, TemplateVarConverter> converters);

	/**
	 * 添加模板变量转换器
	 *
	 * @param clazz     名称
	 * @param converter 转换器
	 */
	void addTemplateVarConverter(Class clazz, TemplateVarConverter converter);

}
