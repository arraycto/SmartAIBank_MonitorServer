package com.dcfs.smartaibank.manager.monitor.analyzer.scheduler;

import com.dcfs.smartaibank.handler.context.Context;
import com.dcfs.smartaibank.handler.exception.HandlerException;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.AnalyzerEngine;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.util.DateUtil;
import com.dcfs.smartaibank.manager.monitor.rule.domain.TimedRule;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 定时任务
 *
 * @author jiazw
 */
@Slf4j
@DisallowConcurrentExecution
public class TimedRuleJob extends QuartzJobBean {

	@Autowired
	private AnalyzerEngine analyzerEngine;

	@Override
	protected void executeInternal(JobExecutionContext cxt) {
		TimedRule rule = (TimedRule) cxt.getMergedJobDataMap().get("timedRule");
		if (rule.isActive()) {
			Context context = createContext(rule);
			try {
				analyzerEngine.handle(context);
			} catch (HandlerException e) {
				log.error("执行定时规则[" + rule.getId() + "]发生异常!", e);
			}
		}
	}

	private MonitorContext createContext(TimedRule rule) {
		//创建消息头
		Map<String, String> header = createHeander(rule);
		//创建消息体
		Map<String, Object> body = new HashMap<>(16);
		MonitorContext context = new MonitorContext(header, body);
		//设置MODE为定时执行
		context.put(Constants.MODE, Constants.MODE_TIMED);
		//设置当前分析规则
		context.put(Constants.CURRENT_ANALYZER_RULE, rule);
		return context;
	}

	private Map<String, String> createHeander(TimedRule rule) {
		Map<String, String> header = new HashMap<>(16);
		header.put(Constants.SYSTEM, rule.getSystemId());
		header.put(Constants.TYPE, rule.getMessageType());
		header.put(Constants.OPERATOR, Constants.OPERATOR_ADDORUPDATE);
		String timeStr = DateUtil.getStrTime(new Date());
		header.put(Constants.CREATE_TIME, timeStr);
		return header;
	}
}
