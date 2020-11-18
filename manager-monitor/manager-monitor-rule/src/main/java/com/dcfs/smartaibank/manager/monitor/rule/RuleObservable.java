package com.dcfs.smartaibank.manager.monitor.rule;

/**
 * 规则变更观察者接口
 *
 * @author jiazw
 */
public interface RuleObservable {
	/**
	 * 添加监听器
	 *
	 * @param listener 监听器
	 */
	void addListener(RuleListener listener);

	/**
	 * 删除监听器
	 *
	 * @param listener 监听器
	 */
	void deleteListener(RuleListener listener);

	/**
	 * 删除所有监听器
	 */
	void deleteListeners();

	/**
	 * 通知所有监听器
	 *
	 * @param event 监听事件
	 */
	void notifyListener(RuleEvent event);
}
