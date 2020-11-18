package com.dcfs.smartaibank.manager.monitor.web.job.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dcfs.smartaibank.manager.monitor.web.util.ReportUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 月报表定时任务
 *
 * @author ligg
 * @date 2016年8月23日 下午6:44:01
 */
public class OtherMonthReportTask extends BaseCommonReportTask {

    private static final Logger LOG = LoggerFactory
            .getLogger(OtherMonthReportTask.class);

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
            List<Map<String, Object>> dayReportList = new ArrayList<Map<String, Object>>();
            if (reportType.equals(ReportUtil.DEV_SUPPLY)) {
                dayReportList = getDao().queryDevSupply(queryMap);
            } else {
                // 查询所有日报表数据
                dayReportList = getDao()
                        .querySzsDayReportInfo(queryMap);
            }
            if (dayReportList.isEmpty()) {
                LOG.info("石嘴山月报表定时任务执行完毕，无日报表数据，无法生成月报表!");
                return;
            }

            // 保存报表数据
            saveReport(dayReportList, reportType);
            LOG.info("石嘴山月报表定时任务执行完毕，报表生成成功!");


        } catch (Exception e) {
            LOG.error("石嘴山月报表定时任务执行失败!", e);
        }

    }


    /**
     * @return
     * @desc 获得月份
     * @author ligg
     * @date 2016年8月30日 上午9:53:49
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
        getDao().insertSzsMonthReport(reportInfoList);
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
        OtherPeriodReportTask periodTask = new OtherPeriodReportTask();
        periodTask.execute(reportType);
    }

    @Override
    protected void deleteReport(String reportType) {
        Map<String, Object> reportInfoMap = new HashMap<>(5);
        reportInfoMap.put("M_TYPE", reportType);
        int year = getYear();
        reportInfoMap.put("R_YEAR", year);
        addItem(reportInfoMap);
        getDao().deleteSzsMonthReport(reportInfoMap);
    }
}
