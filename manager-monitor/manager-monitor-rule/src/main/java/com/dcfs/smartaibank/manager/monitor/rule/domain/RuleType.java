package com.dcfs.smartaibank.manager.monitor.rule.domain;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 规则类型
 *
 * @author jiazw
 */
public enum RuleType implements CodeDescEnum<String, String> {
	/**
	 * 实时规则
	 */
	REAL("REAL", "实时规则"),
	/**
	 * 定时规则
	 */
	TIMED("TIMED", "定时规则"),
	/**
	 * 预警规则
	 */
	ALARM("ALARM", "预警规则"),
	/**
	 * 过滤器规则
	 */
	FILTER("FILTER", "过滤器规则"),
	/**
	 * 通知规则
	 */
	NOTIFY("NOTIFY", "通知规则"),
	/**
	 * 预处理规则
	 */
	PREPARE("PREPARE", "预处理规则");

	String code;
	String desc;

	RuleType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
