package com.dcfs.smartaibank.manager.monitor.web.job;

import com.dcfs.smartaibank.manager.monitor.web.util.ReportUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;

import java.util.Date;
import java.util.Map;



/**
 * @desc 所有外设故障的定时任务
 * @date 2019年7月30日 下午1:35:19
 * @author wangtingo
 */
@DisallowConcurrentExecution
@Slf4j
public class DeviceReportJob extends AbstractDayReportJob {
	@Override
	protected void addDataToGroup(
			Map<String, Map<String, Object>> faultMap,
			Map<String, Object> record, String groupKey, Date startDate,
			Date endDate) {
		log.info("外设故障率报表Job执行开始");
		String type = ReportUtil.getValue(record, "M_TYPE");
		if (getReportType().equals(type)) {
			addFaultData(faultMap, record, groupKey, startDate, endDate);
		}
	}

	@Override
	protected String getReportType() {
		return ReportUtil.DEV_DEVICE;
	}

}
