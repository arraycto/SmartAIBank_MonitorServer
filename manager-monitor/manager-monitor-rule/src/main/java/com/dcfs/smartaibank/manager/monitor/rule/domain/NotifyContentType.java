package com.dcfs.smartaibank.manager.monitor.rule.domain;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 通知内容类型
 *
 * @author jiazw
 */
public enum NotifyContentType implements CodeDescEnum<String, String> {

	/**
	 * 监控内容通知
	 */
	MONITOR("MONITOR", "监控内容通知"),

	/**
	 * 预警内容通知
	 */
	ALARM("ALARM", "预警内容通知");

	String code;
	String desc;

	NotifyContentType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
