package com.dcfs.smartaibank.manager.monitor.web.job.task;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 年报表定时任务
 * @date 2016年8月24日 下午3:14:52
 * @author ligg
 */
public class OtherYearReportTask extends BaseCommonReportTask {

    private static final Logger LOG = LoggerFactory
            .getLogger(OtherYearReportTask.class);

    @Override
    public void execute(String reportType) {
        try {

            // 获取查询条件
            Map<String, Object> queryMap = getQueryCondition();
            queryMap.put("REPORT_TYPE", reportType);

            // 查询所有季报表数据
            List<Map<String, Object>> reportInfoList = getDao()
                    .querySzsPeriodReportInfo(queryMap);

            if (reportInfoList.isEmpty()) {
                LOG.info("年报表定时任务执行完毕，无季报表故障数据，无法生成年报表!");
                return;
            }

            // 保存报表数据
            saveReport(reportInfoList, reportType);

            LOG.info("年报表定时任务执行完毕，报表生成成功!");
        } catch (Exception e) {
            LOG.error("年报表定时任务执行失败!", e);
        }
    }

    @Override
    protected void insertReport(List<Map<String, Object>> reportInfoList) {
        getDao().insertSzsYearReport(reportInfoList);
    }

    @Override
    protected void addItem(Map<String, Object> record) {
    }

    @Override
    protected void setStartDate(Calendar c) {
        c.set(c.get(Calendar.YEAR), 0, 1);
    }

    @Override
    public boolean dateCheck() {
        return false;
    }

    @Override
    public void callTask(String reportType) {
    }

    @Override
    protected void deleteReport(String reportType) {
        Map<String, Object> reportInfoMap = new HashMap<>(5);
        reportInfoMap.put("M_TYPE", reportType);
        int year = getYear();
        reportInfoMap.put("R_YEAR", year);
        getDao().deleteSzsYearReport(reportInfoMap);
    }
}
