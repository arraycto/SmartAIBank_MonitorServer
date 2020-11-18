package com.dcfs.smartaibank.manager.monitor.core.template;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 模板类型
 *
 * @author jiazw
 */
public enum TemplateType implements CodeDescEnum<String, String> {
	/**
	 * 模式匹配
	 */
	PATTERN("PATTERN", "模式匹配"),

	/**
	 * VELOCITY
	 */
	VELOCITY("VELOCITY", "VELOCITY");

	String code;
	String desc;

	TemplateType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
