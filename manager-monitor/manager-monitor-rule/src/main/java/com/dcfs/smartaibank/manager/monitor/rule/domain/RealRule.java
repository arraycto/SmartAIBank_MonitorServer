package com.dcfs.smartaibank.manager.monitor.rule.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 实时规则
 *
 * @author jiazw
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RealRule extends Rule {

	/**
	 * 监控项id
	 */
	private MonitorItem monitorItem;

	/**
	 * 系统标识
	 */
	private String systemId = "";

	/**
	 * 系统模块
	 */
	private String messageType = "";
}
