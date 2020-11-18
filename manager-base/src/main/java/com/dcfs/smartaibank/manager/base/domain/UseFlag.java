package com.dcfs.smartaibank.manager.base.domain;


import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 用户标志枚举
 *
 * @author
 */
public enum UseFlag implements CodeDescEnum<String, String> {
	/**
	 * 启用
	 */
	ENABLED("00", "启用"),

	/**
	 * 停用
	 */
	DISABLED("10", "停用");

	String code;
	String desc;

	UseFlag(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
