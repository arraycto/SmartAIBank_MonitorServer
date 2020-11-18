package com.dcfs.smartaibank.manager.monitor.web.job;

import com.dcfs.smartaibank.manager.monitor.web.util.ReportUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;

import java.util.Date;
import java.util.Map;

/**
 * @desc 网点错账定时任务基类
 * @date 2019年8月7日 下午2:29:22
 * @author wangtingo
 */
@DisallowConcurrentExecution
@Slf4j
public class AccountDealJob extends BaseDayReportJob {

	@Override
	protected void addDataToGroup(
			Map<String, Map<String, Object>> faultMap,
			Map<String, Object> record, String branchNo, Date startDate,
			Date endDate) {
		// 机具编号
		String mid = ReportUtil.getValue(record, "MID");
		//周期批次号
		String cycleId = ReportUtil.getValue(record, "CYCLE_ID");

		String key = ReportUtil.getKey(branchNo, getReportType(), mid, cycleId);
		addFaultData(faultMap, record, key, startDate, endDate);

	}


	@Override
	protected String getReportType() {
		return ReportUtil.ACCOUNT_DEAL;
	}

}
