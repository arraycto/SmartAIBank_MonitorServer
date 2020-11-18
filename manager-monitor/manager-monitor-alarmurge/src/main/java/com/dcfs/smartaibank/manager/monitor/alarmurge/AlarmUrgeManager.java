package com.dcfs.smartaibank.manager.monitor.alarmurge;

import java.util.concurrent.DelayQueue;

/**
 * 预警催促管理
 * @author jiazw
 */
public interface AlarmUrgeManager {

	/**
	 * 添加预警催促信息
	 *
	 * @param urge 预警催促信息
	 */
	void addAlarmUrge(AlarmUrge urge);

	/**
	 * 获取预警催促队列
	 *
	 * @return
	 */
	DelayQueue<AlarmUrge> getQueue();
}
