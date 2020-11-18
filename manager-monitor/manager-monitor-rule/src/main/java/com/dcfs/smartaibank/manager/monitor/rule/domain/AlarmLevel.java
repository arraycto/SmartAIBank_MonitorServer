package com.dcfs.smartaibank.manager.monitor.rule.domain;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 预警级别定义
 *
 * @author jiazw
 */
public enum AlarmLevel implements CodeDescEnum<Integer, String> {

	/**
	 * 无预警
	 */
	NO(0, "无"),

	/**
	 * 黄色预警
	 */
	LOW(1, "低"),

	/**
	 * 橙色预警
	 */
	MIDDLE(2, "中"),

	/**
	 * 红色预警
	 */
	HIGH(3, "高");

	Integer code;
	String desc;

	AlarmLevel(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
