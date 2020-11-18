package com.dcfs.smartaibank.manager.monitor.web.job;

import com.dcfs.smartaibank.manager.monitor.web.util.ReportUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;

import java.util.Date;
import java.util.Map;


/**
 * @desc 设备故障的定时任务
 * @date 2020年4月30日 下午1:35:19
 * @author qiuch
 */
@DisallowConcurrentExecution
@Slf4j
public class EquipmentReportJob extends AbstractDayReportJob {
	@Override
	protected void addDataToGroup(
			Map<String, Map<String, Object>> faultMap,
			Map<String, Object> record, String groupKey, Date startDate,
			Date endDate) {
		log.info("设备故障率报表Job执行开始");
		String branchNo = ReportUtil.getValue(record, "BRANCH_NO");
		// 机具编号
		String mid = ReportUtil.getValue(record, "MID");
		String type = ReportUtil.getValue(record, "M_TYPE");
		if (ReportUtil.DEV_DEVICE.equals(type)) {
			String baseKey = ReportUtil.getKey(branchNo, getReportType(), mid);
			addFaultData(faultMap, record, baseKey, startDate, endDate);
		}
	}

	@Override
	protected String getReportType() {
		return ReportUtil.DEV_EQUIPMENT;
	}

}
