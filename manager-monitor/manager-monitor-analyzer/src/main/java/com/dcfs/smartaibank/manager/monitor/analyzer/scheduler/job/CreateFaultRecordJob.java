package com.dcfs.smartaibank.manager.monitor.analyzer.scheduler.job;

import com.dcfs.smartaibank.manager.monitor.core.util.DateUtil;
import com.dcfs.smartaibank.manager.monitor.analyzer.dao.FaultRecordDao;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建故障记录
 *
 * @author jiazw
 */
@Slf4j
@DisallowConcurrentExecution
public class CreateFaultRecordJob extends QuartzJobBean {
	@Autowired
	private FaultRecordDao faultDao;

	private static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	private static final String FORMAT_DATE = "yyyy-MM-dd";

	@Override
	protected void executeInternal(JobExecutionContext cxt) {
		try {
			Map<String, Object> queryMap = new HashMap<>(16);
			SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE);
			String reportDate = dateFormat.format(new Date());
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat(FORMAT_DATE_TIME);
			Date d = dateTimeFormat.parse(reportDate + "00:00:00");
			long now = DateUtil.getTime(d);
			queryMap.put("REPORT_DATE", reportDate);
			queryMap.put("NOWL", now);
			faultDao.createFaultRecord(queryMap);
			if (log.isDebugEnabled()) {
				log.debug("自动生成昨日未解决的错误信息!");
			}
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug("自动生成昨日未解决的错误信息!", e);
			}
		}
	}
}
