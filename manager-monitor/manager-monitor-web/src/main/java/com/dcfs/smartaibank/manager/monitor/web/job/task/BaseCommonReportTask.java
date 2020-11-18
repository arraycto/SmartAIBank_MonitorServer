package com.dcfs.smartaibank.manager.monitor.web.job.task;

import com.dcfs.smartaibank.manager.monitor.core.util.SpringContextUtil;
import com.dcfs.smartaibank.manager.monitor.web.dao.ReportDao;
import com.dcfs.smartaibank.manager.monitor.web.util.DateUtil;
import com.dcfs.smartaibank.manager.monitor.web.util.ReportUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 报表抽象类
 *
 * @author ligg
 * @date 2016年8月24日 下午2:51:02
 */
public abstract class BaseCommonReportTask implements IReportTask {

    private ReportDao dao;

    private static final String OCCUR_COUNT = "OCCUR_COUNT";
    private static final String DIFFER_TIME_INT = "DIFFER_TIME_INT";

    private String startDateStr;
    private String endDateStr;


    /**
     * 执行报表生成任务
     *
     * @param reportType
     * @date 2016年8月30日 上午9:25:02
     */
    public abstract void execute(String reportType);

    /**
     * 向数据库中持久化报表数据
     *
     * @param reportInfoList
     * @date 2016年8月24日 上午11:49:45
     */
    protected abstract void insertReport(
            List<Map<String, Object>> reportInfoList);

    /**
     * 删除数据
     *
     * @param reportType
     * @date 2016年11月15日
     */
    protected abstract void deleteReport(String reportType);

    /**
     * 添加报表数据项，该方法可以为空
     *
     * @param record
     * @date 2016年8月24日 上午11:50:13
     */
    protected abstract void addItem(Map<String, Object> record);


    /**
     * 设置开始时间，如果报表开始日期和结束日期相同，该方法可以为空
     *
     * @param c
     * @date 2016年8月24日 上午11:50:13
     */
    protected abstract void setStartDate(Calendar c);


    protected ReportDao getDao() {
        if (dao == null) {
            dao = (ReportDao) SpringContextUtil.getBean("reportDao");
        }
        return dao;
    }


    /**
     * @return 获得报表查询条件
     * @author ligg
     * @date 2016年8月24日 下午3:13:08
     */
    protected Map<String, Object> getQueryCondition() {
        // 获得当前月报表的结束时间
        Date endDate = ReportUtil.getReportDate();
        String endDateString = DateUtil.getStrSplitDate(endDate);

        // 获得当前月报表的开始时间
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        setStartDate(c);
        Date starDate = c.getTime();
        String startDateString = DateUtil.getStrSplitDate(starDate);

        // 将开始日期和结束日期放入容器中
        Map<String, Object> queryMap = new HashMap<>(5);
        queryMap.put("START_DATE", startDateString);
        queryMap.put("END_DATE", endDateString);

        this.startDateStr = startDateString;
        this.endDateStr = endDateString;
        return queryMap;
    }

    protected int getYear() {
        Map<String, Object> queryMap = getQueryCondition();
        String endDate = ReportUtil.getValue(queryMap, "END_DATE");
        String[] dates = endDate.split("-");
        // 获得年份
        int year = Integer.parseInt(dates[0]);
        return year;
    }

    /**
     * @param reportInfoList
     * @return 获得需要持久化的数据信息
     * @author ligg
     * @date 2016年8月24日 上午11:50:40
     */
    private Map<String, Map<String, Object>> getReportInfo(
            List<Map<String, Object>> reportInfoList) {
        // key: 机构ID_故障类型_机具ID 或者 key:机构ID_故障类型_机具ID_外设ID
        Map<String, Map<String, Object>> reportInfoMap = new HashMap<>(100);
        // 临时时间容器
        Map<String, Object> timeMap = new HashMap<>(100);

        // 遍历报表数据
        for (Map<String, Object> record : reportInfoList) {
            String branchNo = ReportUtil.getValue(record, "BRANCH_NO");
            String mid = ReportUtil.getValue(record, "MID");
            String type = ReportUtil.getValue(record, "M_TYPE");
            String devClassKey = ReportUtil.getValue(record, "DEVCLASSKEY");
            String alarmLevel = ReportUtil.getValue(record, "ALARM_LEVEL");
            String cycleId = ReportUtil.getValue(record, "CYCLE_ID");
            String resourceCode = ReportUtil.getValue(record, "RESOURCE_CODE");
            String tranStatus = ReportUtil.getValue(record, "TRAN_STATUS");
            String userNo = ReportUtil.getValue(record, "USER_NO");
            String resourceChanel = ReportUtil.getValue(record, "RESOURCE_CHANEL");
            String key = ReportUtil.getKey(branchNo, type);

            if (type.equals(ReportUtil.ACCOUNT_DEAL)) {
                key = ReportUtil.getKey(branchNo, type, mid, cycleId);
            } else if (type.equals(ReportUtil.MANAGER_ANSWER)) {
                key = ReportUtil.getKey(branchNo, type, mid, alarmLevel);
            } else if (type.equals(ReportUtil.MANAGER_DEAL)) {
                key = ReportUtil.getKey(branchNo, type, mid, alarmLevel);
            } else if (type.equals(ReportUtil.DEV_TRAN)) {
                key = ReportUtil.getKey(branchNo, type, resourceCode, tranStatus);
            } else if (type.equals(ReportUtil.DEV_COST)) {
                key = ReportUtil.getKey(branchNo, type, mid);
            } else if (type.equals(ReportUtil.DEV_SUPPLY)) {
                key = ReportUtil.getKey(branchNo, type, mid);
            }


            // 获得原始报表时间
            Date preReportDate = ReportUtil.getValue(record, "CREATE_TIME");
            String dayDateStr = DateUtil.getStrSplitDate(preReportDate);

            int year = getYear();
            record.put("R_YEAR", year);
            // 设置新报表生成时间
            record.put("CREATE_TIME", ReportUtil.getReportDate());

            // 新增数据项
            addItem(record);

            record.remove("SEQ_NO");

            // 计算故障总时间
            calcFaultTime(reportInfoMap, record, key);

            // 获取当前工作时间
            BigDecimal workTime = ReportUtil.getValue(record, DIFFER_TIME_INT);
            // key: 机构ID_机具ID_日前
            String timeKey = ReportUtil.getKey(key, dayDateStr);
            // 计算应工作总时间时，同一个机构的同一台机具同一天日期只计算一次，
            BigDecimal preWorkTime = ReportUtil.getValue(timeMap, timeKey);
            if (preWorkTime == null) {
                timeMap.put(timeKey, workTime);
            } else {
                timeMap.put(timeKey, workTime.add(preWorkTime));
            }
        }

        // 计算每个机具的总工作时间
        Map<String, Object> totalTimeMap = calcTotalTime(timeMap);


        //  将应工作总时间放入容器
        reportInfoMap.put(ReportUtil.DIFFER_TIME_INT, totalTimeMap);

        return reportInfoMap;
    }

    /**
     * @param timeMap
     * @return 计算工作总时间
     * @author ligg
     * @date 2017年1月1日 下午4:32:07
     */
    private Map<String, Object> calcTotalTime(Map<String, Object> timeMap) {
        Map<String, Object> totalTimeMap = new HashMap<>(100);
        for (String timeKey : timeMap.keySet()) {
            String[] keys = timeKey.split(ReportUtil.STR_SPLIT);
            // key: 机构ID_机具ID
            String tempKey = ReportUtil.getArrayKey(keys);
            BigDecimal currentTime = ReportUtil.getValue(timeMap, timeKey);
            BigDecimal total = ReportUtil.getValue(totalTimeMap, tempKey);
            if (total == null) {
                total = currentTime;
            } else {
                total = total.add(currentTime);
            }
            totalTimeMap.put(tempKey, total);
        }
        return totalTimeMap;
    }


    /**
     * @param reportType
     * @param reportType
     * @throws Exception 保存报表数据
     * @author ligg
     * @date 2016年8月24日 上午11:45:27
     */
    protected void saveReport(List<Map<String, Object>> reportInfoList, String reportType) throws Exception {

        // 获得需要保存的报表信息  key: 机构ID_故障类型_机具ID 或者 key:机构ID_故障类型_机具ID_外设ID
        Map<String, Map<String, Object>> reportInfoMap = getReportInfo(reportInfoList);

        deleteReport(reportType);

        List<Map<String, Object>> saveInfoList = new ArrayList<Map<String, Object>>();
        // 获取应工作总时间
        Map<String, Object> timeMap = reportInfoMap.get(ReportUtil.DIFFER_TIME_INT);
        for (String key : reportInfoMap.keySet()) {
            if (ReportUtil.DIFFER_TIME_INT.equals(key)) {
                continue;
            }
            Map<String, Object> innerMap = reportInfoMap.get(key);

            //设置默认的innerMap
            for (int i = 0; i < ReportUtil.COLUMN_VALUE.length; i++) {
                if (innerMap.get(ReportUtil.COLUMN_VALUE[i]) == null) {
                    innerMap.put(ReportUtil.COLUMN_VALUE[i], "");
                }
            }

            BigDecimal total = ReportUtil.getValue(timeMap, key);
            long totalTime = total.longValue();

            innerMap.put(DIFFER_TIME_INT, totalTime);
            saveInfoList.add(innerMap);

        }
        // 批量保存报表数据
        if (!saveInfoList.isEmpty()) {
            insertReport(saveInfoList);
            saveInfoList.clear();
        }

    }

    /**
     * @param reportInfoMap
     * @param record
     * @param key           计算故障总时间
     * @author ligg
     * @date 2016年8月23日 下午6:44:53
     */
    private void calcFaultTime(Map<String, Map<String, Object>> reportInfoMap,
                               Map<String, Object> record, String key) {
        Map<String, Object> innerMap = reportInfoMap.get(key);
        if (innerMap == null) {
            innerMap = new HashMap<>(100);
            innerMap.putAll(record);
            reportInfoMap.put(key, innerMap);
        } else {
            BigDecimal preErrorTime = ReportUtil.getValue(innerMap,
                    OCCUR_COUNT);
            BigDecimal errorTime = ReportUtil
                    .getValue(record, OCCUR_COUNT);
            // 对故障时间进行累加
            errorTime = errorTime.add(preErrorTime);
            // 将故障时间重新设置到容器中
            innerMap.put(OCCUR_COUNT, errorTime);
        }
    }

    protected String getStartDateStr() {
        return startDateStr;
    }

    protected String getEndDateStr() {
        return endDateStr;
    }

}
