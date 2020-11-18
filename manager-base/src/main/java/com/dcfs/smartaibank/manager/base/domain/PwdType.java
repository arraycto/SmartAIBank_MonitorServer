package com.dcfs.smartaibank.manager.base.domain;


import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 密码类型枚举
 *
 * @author
 */
public enum PwdType implements CodeDescEnum<String, String> {

	/**
	 * 正常
	 */
	NORMAL("0", "正常"),
	/**
	 * 锁定
	 */
	LOCK("1", "锁定");

	String code;
	String desc;

	PwdType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
