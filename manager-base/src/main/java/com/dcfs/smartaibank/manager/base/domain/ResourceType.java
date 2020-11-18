package com.dcfs.smartaibank.manager.base.domain;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 资源类型枚举类
 *
 * @author qiuch wangjzm
 * @since 1.0.0
 */
public enum ResourceType implements CodeDescEnum<String, String> {
	/**
	 * 系统
	 */
	SYSTEM("01", "系统"),

	/**
	 * 菜单
	 */
	MENU("02", "菜单"),

	/**
	 * 模块
	 */
	MODULE("03", "模块");

	String code;
	String desc;

	ResourceType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
