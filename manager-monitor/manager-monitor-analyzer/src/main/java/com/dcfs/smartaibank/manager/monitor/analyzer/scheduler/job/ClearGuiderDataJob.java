package com.dcfs.smartaibank.manager.monitor.analyzer.scheduler.job;

import com.dcfs.smartaibank.manager.monitor.analyzer.dao.GuiderMonitorBatchDao;
import com.dcfs.smartaibank.manager.monitor.analyzer.dao.GuiderMonitorDao;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 清理引导分流数据任务
 *
 * @author jiazw
 */
@Slf4j
@DisallowConcurrentExecution
public class ClearGuiderDataJob extends QuartzJobBean {
	@Autowired
	private GuiderMonitorDao guiderMonitorDao;
	@Autowired
	private GuiderMonitorBatchDao guiderMonitorBatchDao;

	@Override
	protected void executeInternal(JobExecutionContext cxt) {
		try {
			guiderMonitorDao.clearData();
			//清理机构繁忙度数据
			guiderMonitorBatchDao.clearBranchBusyData();

			//清理村镇繁忙度数据
			guiderMonitorBatchDao.clearCountyBusyData();

			//清理市繁忙度数据
			guiderMonitorBatchDao.clearCityBusyData();

			//清理省繁忙度数据
			guiderMonitorBatchDao.clearProvinceBusyData();

			//清理国家繁忙度数据
			guiderMonitorBatchDao.clearCountryBusyData();

			//清理机构服务质量数据
			guiderMonitorBatchDao.clearBranchQualData();

			//清理村镇服务质量数据
			guiderMonitorBatchDao.clearCountyQualData();

			//清理市服务质量数据
			guiderMonitorBatchDao.clearCityQualData();

			//清理省服务质量数据
			guiderMonitorBatchDao.clearProvinceQualData();

			//清理国家服务质量数据
			guiderMonitorBatchDao.clearCountryQualData();

			if (log.isDebugEnabled()) {
				log.debug("清理排队业务数据!");
			}
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug("清理排队业务数据失败!", e);
			}
		}
	}
}
