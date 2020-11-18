package com.dcfs.smartaibank.manager.monitor.alarmurge;

import com.dcfs.smartaibank.manager.monitor.alarmurge.dao.AutoAlarmUrgeDao;
import com.dcfs.smartaibank.manager.monitor.rule.RuleCenterAccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 预警督促处理
 *
 * @author jiazw
 */
@Slf4j
@Component
public class AutoAlarmUrgeBatchExecutor {

	@Autowired
	private AutoAlarmUrgeDao autoAlarmUrgeDao;

	@Autowired
	private RuleCenterAccess ruleCenterAccess;

	/**
	 * 发送
	 *
	 * @param alarmid  预警ID
	 * @param delay    耽搁时间
	 * @param urgetype 督促类型
	 */
	public void send(String alarmid, long delay, String urgetype) {
		//TODO 未实现
	}
}
