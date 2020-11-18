package com.dcfs.smartaibank.manager.base.domain;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 机构级别枚举
 *
 * @author jiazw
 * @since 1.0.0
 */
public enum OrgLevel implements CodeDescEnum<String, String> {
	/**
	 * 总行
	 */
	HEAD("1", "总行"),
	/**
	 * 分行
	 */
	BRANCH("2", "分行"),
	/**
	 * 支行
	 */
	SUBBRANCH("3", "支行"),
	/**
	 * 网点
	 */
	NETWORK("4", "二级支行");

	String code;
	String desc;

	OrgLevel(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
