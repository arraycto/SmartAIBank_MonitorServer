package com.dcfs.smartaibank.manager.base.domain;


import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 显示状态枚举
 *
 * @author
 */
public enum ShowFlag implements CodeDescEnum<String, String> {
	/**
	 * 启用
	 */
	ENABLED("0", "启用"),

	/**
	 * 停用
	 */
	DISABLED("1", "停用");

	String code;
	String desc;

	ShowFlag(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
