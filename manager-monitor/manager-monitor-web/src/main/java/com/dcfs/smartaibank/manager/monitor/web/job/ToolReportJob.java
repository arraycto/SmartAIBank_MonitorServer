package com.dcfs.smartaibank.manager.monitor.web.job;

import java.util.Date;
import java.util.Map;

import com.dcfs.smartaibank.manager.monitor.web.util.ReportUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;

/**
 * @desc 机具故障定时任务
 * @date 2016年8月30日 下午1:33:22
 * @author wangtingo
 */
@DisallowConcurrentExecution
@Slf4j
public class ToolReportJob extends AbstractDayReportJob {

	@Override
	protected void addDataToGroup(
			Map<String, Map<String, Object>> faultMap,
			Map<String, Object> record, String baseKey, Date startDate,
			Date endDate) {
        log.info("机具故障率报表Job执行开始");
		String branchNo = ReportUtil.getValue(record, "BRANCH_NO");
		// 机具编号
		String mid = ReportUtil.getValue(record, "MID");
		String groupKey = ReportUtil.getKey(branchNo, getReportType(), mid);
		addFaultData(faultMap, record, groupKey, startDate, endDate);
	}

	@Override
	protected String getReportType() {
		return ReportUtil.DEV_TOOL;
	}

}
