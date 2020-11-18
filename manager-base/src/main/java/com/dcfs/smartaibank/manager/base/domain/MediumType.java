package com.dcfs.smartaibank.manager.base.domain;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 性别
 *
 * @author jiazw
 */
public enum MediumType implements CodeDescEnum<String, String> {
	/**
	 * 密码
	 */
	PASSWORD("1", "密码"),

	/**
	 * 指纹
	 */
	FINGER("2", "指纹"),

	/**
	 * IC卡
	 */
	CARD("3", "IC卡");

	String code;
	String desc;

	MediumType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
