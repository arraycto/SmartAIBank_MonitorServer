package com.dcfs.smartaibank.manager.monitor.rule.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 定时规则
 * @author jiazw
 *
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuppressWarnings("serial")
public class TimedRule extends Rule {

	/**
	 * 监控项id
	 */
	private MonitorItem monitorItem;

	/**
	 * 执行表达式
	 */
	private String cron;

	/**
	 * 执行表达式描述
	 */
	private String cronDesc;

	/**
	 * 系统标识
	 */
	private String systemId;

	/**
	 * 系统模块
	 */
	private String messageType;
}
