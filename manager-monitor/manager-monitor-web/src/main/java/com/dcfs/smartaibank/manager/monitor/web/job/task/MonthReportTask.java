package com.dcfs.smartaibank.manager.monitor.web.job.task;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 月报表定时任务
 * @date 2016年8月23日 下午6:44:01
 * @author wangtingo
 */
public class MonthReportTask extends AbstractCommonReportTask {

	private static final Logger LOG = LoggerFactory
			.getLogger(MonthReportTask.class);

	@Override
	public void execute(String reportType) {
		makeMonthReport(reportType);
		// 调用季报表生成任务
		if (dateCheck()) {
			callTask(reportType);
		}

	}



	private void makeMonthReport(String reportType) {
		try {
			// 获取查询条件
			Map<String, Object> queryMap = getQueryCondition();
			queryMap.put("REPORT_TYPE", reportType);

			// 查询所有日报表数据
			List<Map<String, Object>> dayReportList = getDao()
					.queryDayReportInfo(queryMap);
			if (dayReportList.isEmpty()) {
				LOG.info("月报表定时任务执行完毕，无日报表数据，无法生成月报表!");
				return;
			}

			// 保存报表数据
			saveReport(dayReportList, reportType);
			LOG.info("月报表定时任务执行完毕，报表生成成功!");


		} catch (Exception e) {
			LOG.error("月报表定时任务执行失败!", e);
		}

	}



	/**
	 * @desc 获得月份
	 * @author ligg
	 * @date 2016年8月30日 上午9:53:49
	 * @return
	 */
	private int getMonth() {
		String endDate = getEndDateStr();
		String[] dates = endDate.split("-");
		// 获得月份
		int month = Integer.parseInt(dates[1]);
		return month;
	}

	@Override
	protected void insertReport(List<Map<String, Object>> reportInfoList) {
		getDao().insertMonthReport(reportInfoList);
	}

	@Override
	protected void addItem(Map<String, Object> record) {
		int month = getMonth();
		record.put("R_MONTH", month);
	}

	@Override
	protected void setStartDate(Calendar c) {
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
	}

	@Override
	public boolean dateCheck() {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH) + 1;
		return (month == 1 || month == 4 || month == 7 || month == 10)
				&& day == 1;
	}

	@Override
	public void callTask(String reportType) {
		PeriodReportTask periodTask = new PeriodReportTask();
		periodTask.execute(reportType);
	}

	@Override
	protected void deleteReport(String reportType) {
		Map<String, Object>  reportInfoMap = new HashMap<>(5);
		reportInfoMap.put("M_TYPE", reportType);
		int year = getYear();
		reportInfoMap.put("R_YEAR", year);
		addItem(reportInfoMap);
		getDao().deleteMonthReport(reportInfoMap);
	}
}
