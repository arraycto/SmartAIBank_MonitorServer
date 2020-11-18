package com.dcfs.smartaibank.manager.monitor.rule.domain;

import com.dcfs.smartaibank.springboot.core.base.CodeDescEnum;

/**
 * 外设状态定义
 *
 * @author jiazw
 */
public enum MachineStatus implements CodeDescEnum<Integer, String> {

	/**
	 * 未监控
	 */
	NOMONITOR(0, "未监控"),

	/**
	 * 正常
	 */
	NORMAL(1, "正常"),

	/**
	 * 应用未启动
	 */
	UNSTART(2, "应用未启动"),

	/**
	 * 告警
	 */
	WARN(3, "告警"),

	/**
	 * 外设异常
	 */
	DEVICE_ERROR(4, "外设异常"),

	/**
	 * 网络异常
	 */
	NETWORK_ERROR(5, "网络异常");

	Integer code;
	String desc;

	MachineStatus(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
