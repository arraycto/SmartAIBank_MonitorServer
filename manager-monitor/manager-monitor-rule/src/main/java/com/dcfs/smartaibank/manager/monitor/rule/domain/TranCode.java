package com.dcfs.smartaibank.manager.monitor.rule.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 交易状态码
 *
 * @author jiazw
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TranCode {
	/**
	 * 交易码
	 */
	private String tranCode;

	/**
	 * 交易返回码
	 */
	private String tranRetCode;

	/**
	 * 状态描述
	 */
	private String statusDesc;

	/**
	 * 外设状态
	 */
	private MonitorStatus status;

	/**
	 * 预警级别
	 */
	private AlarmLevel alarmLevel;

	/**
	 * 是否激活
	 */
	private boolean active;

	/**
	 * 连续出现次数
	 */
	private int count;
}
