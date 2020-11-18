package com.dcfs.smartaibank.manager.monitor.analyzer.executor;

import com.dcfs.smartaibank.manager.monitor.core.context.ContextHelper;
import com.dcfs.smartaibank.manager.monitor.analyzer.dao.AutoAlarmCloseDao;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.executor.ExecuteDefine;
import com.dcfs.smartaibank.manager.monitor.core.executor.Executor;
import com.dcfs.smartaibank.manager.monitor.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.ExecutorException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预警自动关闭批处理执行器
 *
 * @author jiazw
 */
@Slf4j
@Component
public class AutoAlarmCloseBatchExecutor implements Executor, Constants, InitializingBean {
	@Autowired
	private AutoAlarmCloseDao autoAlarmCloseDao;

	@SuppressWarnings("unchecked")
	@Override
	public Boolean execute(MonitorContext context, ExecuteDefine target)
		throws ExecutorException {
		try {
			Map<String, Object> querymap = new HashMap<>(16);
			String nowTime = DateUtil.getStrTime(new Date());
			querymap.put("nowTime", nowTime);
			List<Map<String, Object>> alarminfolist = autoAlarmCloseDao.queryAlarmInfo(querymap);
			for (Map<String, Object> map : alarminfolist) {
				map.put("CLOSE_TIME", new Date());
				map.put(Constants.ALARM_STATUS, ContextHelper.ALARM_CLOSE);
				map.put("CLOSE_TYPE", ContextHelper.AUTO_CLOSE);
				autoAlarmCloseDao.updateAlarm(map);
			}
			if (log.isDebugEnabled()) {
				log.debug("预警信息自动关闭执行成功!");
			}
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug("预警信息自动关闭执行失败!", e);
			}
			return false;
		}
		return true;
	}

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(this.autoAlarmCloseDao, "autoAlarmCloseDao must be not null");
	}
}
