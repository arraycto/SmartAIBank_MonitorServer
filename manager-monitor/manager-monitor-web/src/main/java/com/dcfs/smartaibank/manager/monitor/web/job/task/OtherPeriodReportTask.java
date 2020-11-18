package com.dcfs.smartaibank.manager.monitor.web.job.task;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 季报表定时任务
 * @date 2016年8月24日 下午1:57:27
 * @author ligg
 */
public class OtherPeriodReportTask extends BaseCommonReportTask {

    private static final Logger LOG = LoggerFactory
            .getLogger(OtherPeriodReportTask.class);

    /**
     * 季度常量
     */
    private static Map<Integer, Integer> peroiodMap = new HashMap<>();

    static {
        peroiodMap.put(3, 1);
        peroiodMap.put(6, 2);
        peroiodMap.put(9, 3);
        peroiodMap.put(12, 4);
    }

    @Override
    public void execute(String reportType) {
        makePeriodReport(reportType);

        // 调用年报表生成任务
        if (dateCheck()) {
            callTask(reportType);
        }
    }


    private void makePeriodReport(String reportType) {
        try {

            // 获取查询条件
            Map<String, Object> queryMap = getQueryCondition();
            queryMap.put("REPORT_TYPE", reportType);

            // 查询所有月报表数据
            List<Map<String, Object>> monthReportList = getDao()
                    .querySzsMonthReportInfo(queryMap);

            if (monthReportList.isEmpty()) {
                LOG.info("季报表定时任务执行完毕，无月报表数据，无法生成季报表!");
                return;
            }

            // 保存报表数据
            saveReport(monthReportList, reportType);
            LOG.info("季报表定时任务执行完毕，报表生成成功!");


        } catch (Exception e) {
            LOG.error("季报表定时任务执行失败!", e);
        }

    }


    @Override
    protected void insertReport(List<Map<String, Object>> reportInfoList) {
        getDao().insertSzsPeriodReport(reportInfoList);
    }


    @Override
    protected void addItem(Map<String, Object> record) {
        Integer period = getPeriod();
        record.put("R_PERIOD", period);
    }

    @Override
    protected void setStartDate(Calendar c) {
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH) - 2, 1);
    }

    @Override
    public boolean dateCheck() {
        Calendar c = Calendar.getInstance();
        Integer day = c.get(Calendar.DAY_OF_MONTH);
        Integer month = c.get(Calendar.MONTH) + 1;
        return month == 1 && day == 1;
    }

    @Override
    public void callTask(String reportType) {
        OtherYearReportTask yearTask = new OtherYearReportTask();
        yearTask.execute(reportType);
    }

    /**
     * @return
     * 获得季度
     * @author ligg
     * @date 2016年8月30日 上午9:54:21
     */
    private Integer getPeriod() {
        String endDate = getEndDateStr();
        String[] dates = endDate.split("-");
        // 获得月份
        Integer month = Integer.parseInt(dates[1]);
        Integer period = peroiodMap.get(month);
        // 季度为空，默认第一季度
        if (period == null) {
            period = 1;
        }
        return period;
    }


    @Override
    protected void deleteReport(String reportType) {
        Map<String, Object> reportInfoMap = new HashMap<>(5);
        reportInfoMap.put("M_TYPE", reportType);
        Integer year = getYear();
        reportInfoMap.put("R_YEAR", year);
        addItem(reportInfoMap);
        getDao().deleteSzsPeriodReport(reportInfoMap);
    }
}
