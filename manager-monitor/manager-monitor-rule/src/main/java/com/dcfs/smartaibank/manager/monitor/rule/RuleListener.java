package com.dcfs.smartaibank.manager.monitor.rule;

/**
 * 规则监听器
 *
 * @author jiazw
 */
public interface RuleListener {
	/**
	 * 更新
	 *
	 * @param event 规则事件
	 */
	void update(RuleEvent event);
}
