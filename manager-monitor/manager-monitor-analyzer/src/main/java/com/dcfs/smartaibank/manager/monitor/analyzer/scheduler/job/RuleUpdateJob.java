package com.dcfs.smartaibank.manager.monitor.analyzer.scheduler.job;

import com.dcfs.smartaibank.manager.monitor.rule.RuleLoader;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 规则定时更新
 *
 * @author jiazw
 */
@Slf4j
@DisallowConcurrentExecution
public class RuleUpdateJob extends QuartzJobBean {
	@Autowired
	private RuleLoader ruleLoader;

	@Override
	protected void executeInternal(JobExecutionContext cxt) throws JobExecutionException {
		try {
			ruleLoader.load();
			if (log.isDebugEnabled()) {
				log.debug("规则及参数定时更新成功!");
			}
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug("规则及参数定时更新失败!", e);
			}
			throw new JobExecutionException(e);
		}
	}
}
