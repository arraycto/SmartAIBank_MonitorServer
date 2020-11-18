package com.dcfs.smartaibank.manager.monitor.rule.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 设备状态码
 *
 * @author jiazw
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DeviceModelCode {
	/**
	 * 外设型号
	 */
	private String deviceModelKey;

	/**
	 * 外设类型
	 */
	private String deviceClasskey;

	/**
	 * 外设状态码
	 */
	private String statusCode;

	/**
	 * 外设状态描述
	 */
	private String statusDesc;

	/**
	 * 外设状态
	 */
	private MonitorStatus status;

	/**
	 * 机具状态
	 */
	private MachineStatus mStatus;

	/**
	 * 预警级别
	 */
	private AlarmLevel alarmLevel;

	/**
	 * 预警规则ID
	 */
	private String alarmRuleId;

	/**
	 * 是否激活
	 */
	private boolean active;

	/**
	 * 修复难度等级
	 */
	private int repairLevel;
}
