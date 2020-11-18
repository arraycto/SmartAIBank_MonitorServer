package com.dcfs.smartaibank.manager.monitor.web.job;

import com.dcfs.smartaibank.manager.monitor.web.util.ReportUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;

import java.util.Date;
import java.util.Map;


/**
 * @desc 网络故障定时任务
 * @date 2016年8月30日 下午1:34:34
 * @author wangtingo
 */
@DisallowConcurrentExecution
@Slf4j
public class NetworkReportJob extends AbstractDayReportJob {

	@Override
	protected void addDataToGroup(
			Map<String, Map<String, Object>> faultMap,
			Map<String, Object> record, String baseKey, Date startDate,
			Date endDate) {
		log.info("网络故障率报表Job执行开始");
		String type = ReportUtil.getValue(record, "M_TYPE");
		if (getReportType().equals(type)) {
			addFaultData(faultMap, record, baseKey, startDate, endDate);
		}
	}

	@Override
	protected String getReportType() {
		return ReportUtil.DEV_NETWORK;
	}

}
