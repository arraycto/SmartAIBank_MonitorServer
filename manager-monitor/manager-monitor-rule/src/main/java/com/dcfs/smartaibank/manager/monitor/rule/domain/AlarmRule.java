package com.dcfs.smartaibank.manager.monitor.rule.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 预警规则定义
 *
 * @author jiazw
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AlarmRule extends Rule {

	/**
	 * 监控项id
	 */
	private MonitorItem monitorItem;

	/**
	 * 预警级别
	 */
	private AlarmLevel level;
}
