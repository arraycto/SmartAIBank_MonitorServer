package com.dcfs.smartaibank.manager.monitor.rule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 规则事件
 *
 * @author jiazw
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RuleEvent {
	private RuleEventType type;

	private String content;
}
