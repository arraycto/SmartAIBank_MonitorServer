package com.dcfs.smartaibank.manager.monitor.analyzer.executor;

import com.dcfs.smartaibank.manager.monitor.analyzer.dao.GuiderMonitorBatchDao;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.executor.ExecuteDefine;
import com.dcfs.smartaibank.manager.monitor.core.executor.Executor;
import com.dcfs.smartaibank.manager.monitor.core.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.ExecutorException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 引导分流监控批处理执行器
 *
 * @author jiazw
 */
@Slf4j
@Component
public class GuiderMonitorBusiBatchExecutor implements Executor, Constants, InitializingBean {

	@Autowired
	private GuiderMonitorBatchDao guiderMonitorBatchDao;

	@SuppressWarnings("unchecked")
	@Override
	public Boolean execute(MonitorContext context, ExecuteDefine target)
		throws ExecutorException {
		try {
			if (guiderMonitorBatchDao == null) {
				guiderMonitorBatchDao = (GuiderMonitorBatchDao) SpringContextUtil.getBean("guiderMonitorBatchDao");
			}
			Map<String, Object> tempmap = new HashMap<>(16);

			//清理并生成机构繁忙度数据
			guiderMonitorBatchDao.clearBranchBusyData();
			guiderMonitorBatchDao.insertBranchBusyData(tempmap);
			guiderMonitorBatchDao.updateBranchBusyStatus(tempmap);

			//清理并生成村镇繁忙度数据
			guiderMonitorBatchDao.clearCountyBusyData();
			guiderMonitorBatchDao.insertCountyBusyData(tempmap);

			//清理并生成市繁忙度数据
			guiderMonitorBatchDao.clearCityBusyData();
			guiderMonitorBatchDao.insertCityBusyData(tempmap);
			guiderMonitorBatchDao.updateCityBusyStatus(tempmap);

			//清理并生成省繁忙度数据
			guiderMonitorBatchDao.clearProvinceBusyData();
			guiderMonitorBatchDao.insertProvinceBusyData(tempmap);
			guiderMonitorBatchDao.updateProvinceBusyStatus(tempmap);

			//清理并生成国家繁忙度数据
			guiderMonitorBatchDao.clearCountryBusyData();
			guiderMonitorBatchDao.insertCountryBusyData(tempmap);
			guiderMonitorBatchDao.updateCountryBusyStatus(tempmap);

			if (log.isDebugEnabled()) {
				log.debug("繁忙度批处理执行成功!");
			}
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug("繁忙度批处理执行失败!", e);
			}
			return false;
		}
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.guiderMonitorBatchDao, "guiderMonitorBatchDao must be not null");
	}
}
