package com.dcfs.smartaibank.manager.monitor.analyzer.scheduler;

import com.dcfs.smartaibank.manager.monitor.rule.RuleCenterAccess;
import com.dcfs.smartaibank.manager.monitor.rule.domain.TimedRule;
import com.dcfs.smartaibank.springboot.schedule.JobProperties.JobDefinition;
import com.dcfs.smartaibank.springboot.schedule.ScheduleManager;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时规则任务加载
 *
 * @author jiazw
 */
@Slf4j
public class TimedRuleJobLoader implements InitializingBean {
	private static final String TIMED_JOB_GROUP = "MONITOR_TIMED_RULE";

	@Autowired
	private RuleCenterAccess ruleCenter;

	@Autowired
	private ScheduleManager schedulerManager;

	/**
	 * 加载定时任务
	 */
	public void load() {
		List<TimedRule> list = ruleCenter.getTimedRules();
		if (list != null) {
			for (TimedRule rule : list) {
				try {
					addJob(rule);
				} catch (SchedulerException e) {
					String msg = String.format("添加定时规则[ID=%s]失败", rule.getId());
					log.error(msg, e);
				}
			}
		}
	}

	private void addJob(TimedRule rule) throws SchedulerException {
		Map<String, Object> data = new HashMap<>(16);
		data.put("timedRule", rule);
		JobDefinition jobDefinition = new JobDefinition();
		jobDefinition.setActive(rule.isActive());
		jobDefinition.setName(rule.getId());
		jobDefinition.setDescription(rule.getDescription());
		jobDefinition.setClazz(TimedRuleJob.class);
		jobDefinition.setCron(rule.getCron());
		jobDefinition.setGroup(TIMED_JOB_GROUP);
		jobDefinition.setData(data);
		schedulerManager.addNewJob(jobDefinition);
	}

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(this.ruleCenter, "ruleCenter must be not null");
		Assert.notNull(this.schedulerManager, "scheduleManager must be not null");
	}

	public void setRuleCenter(RuleCenterAccess ruleCenter) {
		this.ruleCenter = ruleCenter;
	}

	public void setSchedulerManager(ScheduleManager schedulerManager) {
		this.schedulerManager = schedulerManager;
	}
}
