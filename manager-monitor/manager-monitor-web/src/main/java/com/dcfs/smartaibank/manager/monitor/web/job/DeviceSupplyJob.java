package com.dcfs.smartaibank.manager.monitor.web.job;

import java.util.Date;
import java.util.Map;

import com.dcfs.smartaibank.manager.monitor.web.util.ReportUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;

/**
 * @desc 设备耗材的定时任务
 * @date 2019年8月7日 下午1:35:19
 * @author wangtingo
 */
@DisallowConcurrentExecution
@Slf4j
public class DeviceSupplyJob extends BaseDayReportJob {

	@Override
	protected void addDataToGroup(
			Map<String, Map<String, Object>> faultMap,
			Map<String, Object> record, String branchNo, Date startDate,
			Date endDate) {
		// 机具编号
		String mid = ReportUtil.getValue(record, "MID");

		String key = ReportUtil.getKey(branchNo, getReportType(), mid);
		addFaultData(faultMap, record, key, startDate, endDate);

	}


	@Override
	protected String getReportType() {
		return ReportUtil.DEV_SUPPLY;
	}

}
