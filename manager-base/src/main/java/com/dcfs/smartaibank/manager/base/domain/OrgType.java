package com.dcfs.smartaibank.manager.base.domain;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 机构类型枚举
 *
 * @author jiazw
 * @since 1.0.0
 */
public enum OrgType implements CodeDescEnum<String, String> {
	/**
	 * 营业机构
	 */
	BUSINESS("1", "营业机构"),
	/**
	 * 管理机构
	 */
	MANAGE("3", "管理机构"),
	/**
	 * 营业兼管理机构
	 */
	BUSANDMAN("5", "营业兼管理机构"),
	/**
	 * 管理机构部门
	 */
	MANAGEDEPART("6", "管理机构部门");

	String code;
	String desc;

	OrgType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
