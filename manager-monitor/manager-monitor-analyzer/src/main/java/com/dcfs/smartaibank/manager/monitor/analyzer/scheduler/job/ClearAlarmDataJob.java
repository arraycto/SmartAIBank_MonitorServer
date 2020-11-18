package com.dcfs.smartaibank.manager.monitor.analyzer.scheduler.job;

import com.dcfs.smartaibank.manager.monitor.analyzer.dao.AutoAlarmCloseDao;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 请处理预警信息任务
 *
 * @author jiazw
 */
@Slf4j
@DisallowConcurrentExecution
public class ClearAlarmDataJob extends QuartzJobBean {
	@Autowired
	private AutoAlarmCloseDao autoAlarmCloseDao;

	@Override
	protected void executeInternal(JobExecutionContext cxt) {
		try {
			//清理设备预警数据
			autoAlarmCloseDao.clearDeviceAlarmData();
			//清理交易预警数据
			autoAlarmCloseDao.clearTranAlarmData();
			//清理故障数据
			autoAlarmCloseDao.clearFaultRecordData();

			if (log.isDebugEnabled()) {
				log.debug("清理预警数据!");
			}
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug("清理预警数据失败!", e);
			}
		}
	}
}
