package com.dcfs.smartaibank.manager.monitor.web.job;

import com.dcfs.smartaibank.manager.monitor.web.util.ReportUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;

import java.util.Date;
import java.util.Map;

/**
 * @desc 设备管理员响应速度任务基类
 * @date 2019年8月7日 下午2:29:22
 * @author wangtingo
 */
@DisallowConcurrentExecution
@Slf4j
public class ManagerAnswerJob extends BaseDayReportJob {

	@Override
	protected void addDataToGroup(
			Map<String, Map<String, Object>> faultMap,
			Map<String, Object> record, String branchNo, Date startDate,
			Date endDate) {

		// 机具编号
		String mid = ReportUtil.getValue(record, "MID");
		//预警等级
		String alarmLevel = ReportUtil.getValue(record, "ALARM_LEVEL").toString();

		String key = ReportUtil.getKey(branchNo, getReportType(), mid, alarmLevel);
		addFaultData(faultMap, record, key, startDate, endDate);
	}


	@Override
	protected String getReportType() {
		return ReportUtil.MANAGER_ANSWER;
	}

}
