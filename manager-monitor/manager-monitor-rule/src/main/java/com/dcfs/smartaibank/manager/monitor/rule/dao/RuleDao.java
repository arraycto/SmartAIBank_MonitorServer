package com.dcfs.smartaibank.manager.monitor.rule.dao;

import com.dcfs.smartaibank.manager.monitor.core.template.TemplateDefine;
import com.dcfs.smartaibank.manager.monitor.rule.domain.AlarmRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.FilterRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.MonitorItem;
import com.dcfs.smartaibank.manager.monitor.rule.domain.NotifyRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.PrepareRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.RealRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.TimedRule;

import java.util.List;

/**
 * 规则DAO
 *
 * @author jiazw
 */
public interface RuleDao {
	/**
	 * 获取全部的规则项
	 *
	 * @return List<MonitorItem>
	 */
	List<MonitorItem> getAllMonitorItem();

	/**
	 * 获取全部过滤规则
	 *
	 * @return List<FilterRule>
	 */
	List<FilterRule> getAllFilterRule();

	/**
	 * 获取全部实时规则
	 *
	 * @return List<RealRule>
	 */
	List<RealRule> getAllRealRule();

	/**
	 * 获取全部预警规则
	 * @return 全部预警规则
	 */
	List<AlarmRule> getAllAlarmRule();

	/**
	 * 获取全部定时规则
	 * @return 全部定时规则
	 */
	List<TimedRule> getAllTimedRule();

	/**
	 * 获取全部通知规则
	 *
	 * @return 通知规则集合
	 */
	List<NotifyRule> getAllNotifyRule();

	/**
	 * 获取全部预处理规则
	 *
	 * @return 预处理规则集合
	 */
	List<PrepareRule> getAllPrepareRule();

	/**
	 * 获取全部的模板定义
	 * @return 模板定义集合
	 */
	List<TemplateDefine> getAllTemplateDefine();
}
