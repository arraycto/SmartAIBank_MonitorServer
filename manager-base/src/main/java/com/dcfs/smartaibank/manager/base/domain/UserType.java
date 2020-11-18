package com.dcfs.smartaibank.manager.base.domain;


import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 性别
 *
 * @author jiazw
 */
public enum UserType implements CodeDescEnum<String, String> {
	/**
	 * 真实用户
	 */
	REAL("A", "真实用户"),

	/**
	 * 虚拟用户
	 */
	VIRTUAL("B", "虚拟用户");

	String code;
	String desc;

	UserType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}


}
