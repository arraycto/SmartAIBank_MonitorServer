package com.dcfs.smartaibank.manager.monitor.rule.domain;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 外设状态定义
 *
 * @author jiazw
 */
public enum MonitorStatus implements CodeDescEnum<Integer, String> {

	/**
	 * 未监控
	 */
	NOMONITOR(0, "未监控"),

	/**
	 * 正常
	 */
	NORMAL(1, "正常"),

	/**
	 * 警告
	 */
	WARN(2, "警告"),

	/**
	 * 异常
	 */
	ERROR(3, "异常");

	Integer code;
	String desc;

	MonitorStatus(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
