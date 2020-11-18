package com.dcfs.smartaibank.manager.monitor.web.job;

import com.dcfs.smartaibank.manager.monitor.web.util.ReportUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;

import java.util.Date;
import java.util.Map;

/**
 * @desc 交易质量任务基类
 * @date 2017年8月7日
 * @author wangitngo
 */
@Slf4j
@DisallowConcurrentExecution
public class DeviceTranJob extends BaseDayReportJob {

	@Override
	protected void addDataToGroup(
			Map<String, Map<String, Object>> faultMap,
			Map<String, Object> record, String branchNo, Date startDate,
			Date endDate) {
		//交易编号
		String resourceCode = ReportUtil.getValue(record, "RESOURCE_CODE");
		//交易渠道
		String  tranStatus = ReportUtil.getValue(record, "TRAN_STATUS");

		String key = ReportUtil.getKey(branchNo, getReportType(), resourceCode, tranStatus);
		addFaultData(faultMap, record, key, startDate, endDate);

	}


	@Override
	protected String getReportType() {
		return ReportUtil.DEV_TRAN;
	}

}
