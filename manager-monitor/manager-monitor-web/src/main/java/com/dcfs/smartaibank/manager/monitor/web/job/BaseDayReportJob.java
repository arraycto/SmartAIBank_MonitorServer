package com.dcfs.smartaibank.manager.monitor.web.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dcfs.smartaibank.manager.monitor.core.util.SpringContextUtil;
import com.dcfs.smartaibank.manager.monitor.web.dao.ReportDao;
import com.dcfs.smartaibank.manager.monitor.web.job.task.IReportTask;
import com.dcfs.smartaibank.manager.monitor.web.job.task.OtherMonthReportTask;
import com.dcfs.smartaibank.manager.monitor.web.util.DateUtil;
import com.dcfs.smartaibank.manager.monitor.web.util.ReportUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;


/**
 * @desc 日报表定时任务基类
 * @date 2017年06月27日
 * @author wangitngo
 */
@Slf4j
@DisallowConcurrentExecution
public abstract class BaseDayReportJob extends QuartzJobBean implements IReportTask {

    private static final Logger LOG = LoggerFactory
            .getLogger(BaseDayReportJob.class);

    private ReportDao reportDao;

    @Override
    public void executeInternal(JobExecutionContext context) {
        if (checkDayReport()) {
            makeDayReport();
        } else {
            LOG.info("当前报表" + getReportType() + "不需要执行日报表");
        }


        // 调用月报表生成任务
        if (dateCheck()) {
            callTask(getReportType());
        }

    }

    private void makeDayReport() {
        LOG.debug("开始执行日报表定时任务<>>>>>> ");
        if (reportDao == null) {
            reportDao = (ReportDao) SpringContextUtil.getBean("reportDao");
        }
        try {
            // 获得当前时间
            Date date = ReportUtil.getReportDate();
            String dateStr = DateUtil.getStrDate(date);
            Map<String, Object> queryMap = new HashMap<>(5);
            queryMap.put("REPORT_DATE", dateStr);
            // 查询对用的数据
            List<Map<String, Object>> result = exeQuery(getReportType(), queryMap);
            // 计算响应时间-备注:key:机构号_类型_设备编号;value:数组timelist[]，存放每一次的时间差值
            Map<String, Map<String, Object>> faultTimeMap = mergeTime(result);
            // 保存数据
            calcAndSaveData(faultTimeMap, date);
            LOG.info(">>>>>> 日报表定时任务执行完毕,报表生成成功!");
        } catch (Exception e) {
            LOG.error(">>>>>> 日报表定时任务执行失败:", e);
        }

    }


    /**
     * @param timeList  存放开始时间和结束时间的列表容器
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @desc 将开始时间和结束时间添加到时间列表中
     * @author ligg
     * @date 2016年8月19日 上午10:26:21
     */
    private void addTime(List<Date[]> timeList, Date startDate, Date endDate) {
        Date[] dateObj = new Date[2];
        dateObj[0] = startDate;
        dateObj[1] = endDate;
        timeList.add(dateObj);
    }


    /**
     * @param startDate
     * @param endDate
     * @return [0] 开始时间 [1]结束时间
     * @desc 获得日期的数字格式
     * @author ligg
     * @date 2016年8月19日 下午3:02:30
     */
    private long[] getDateNum(Date startDate, Date endDate) {
        // 当前故障开始时间和结束时间
        long startTime = DateUtil.getTime(startDate);
        long endTime = DateUtil.getTime(ReportUtil.getReportDate());
        // 不为空表示故障已恢复,取故障结束时间,否则取最大值
        if (endDate != null) {
            endTime = DateUtil.getTime(endDate);
        }
        return new long[]{startTime, endTime};
    }

    /**
     * @param date 报表生成日期
     * @return
     * @desc 计算故障时间和应工作时间，并对数据进行加工处理
     * @author ligg
     * @date 2016年8月9日 上午11:58:01
     * 故障信息
     */

    private void calcAndSaveData(Map<String, Map<String, Object>> faultTimeMap,
                                 Date date) {
        String mtype = "";
        for (String key : faultTimeMap.keySet()) {
            String[] keys = key.split(ReportUtil.STR_SPLIT);
            mtype = keys[1];
            break;
        }

        Map<String, Object> reportInfoMap = new HashMap<>(5);
        reportInfoMap.put("M_TYPE", mtype);
        reportInfoMap.put("CREATE_TIME", date);
        reportDao.deleteSzsDayReport(reportInfoMap);
        LOG.info("删除原有日报表数据！报表类型为： " + mtype + ",报表日期为：" + date);
        // 待保存的数据容器
        List<Map<String, Object>> faultTimeList = new ArrayList<>(100);
        // 遍历故障数据，对其进行加工处理
        for (String key : faultTimeMap.keySet()) {
            String[] keys = key.split(ReportUtil.STR_SPLIT);
            Map<String, Object> innerMap = faultTimeMap.get(key);
            //设置默认的innerMap
            for (int i = 0; i < ReportUtil.COLUMN_VALUE.length; i++) {
                if (innerMap.get(ReportUtil.COLUMN_VALUE[i]) == null) {
                    innerMap.put(ReportUtil.COLUMN_VALUE[i], "");
                }
            }
            List<Date[]> errorDateList = (List<Date[]>) innerMap
                    .get("TIME_LIST");
            // 计算总时间的差值
            long differTime = calcTime(errorDateList);
            // 时间差值
            innerMap.put("DIFFER_TIME_INT", differTime);
            // 当前次数
            if (ReportUtil.ACCOUNT_DEAL.equals(keys[1])) {
                innerMap.put("OCCUR_COUNT", innerMap.get("ACCOUNT_NUM"));
            } else if (ReportUtil.DEV_COST.equals(keys[1])) {
                innerMap.put("OCCUR_COUNT", innerMap.get("COST"));
            } else {
                innerMap.put("OCCUR_COUNT", errorDateList.size());
            }

            // 类型
            innerMap.put("M_TYPE", keys[1]);

            // 报表生成时间
            innerMap.put("CREATE_TIME", date);
            // 移除掉无用的数据项
            innerMap.remove("TIME_LIST");
            faultTimeList.add(innerMap);
        }

        // 批量保存日报表数据
        if (!faultTimeList.isEmpty()) {
            reportDao.insertSzsDayReport(faultTimeList);
            faultTimeList.clear();
        }
    }

    /**
     * @param dateList 时间段列表
     * @return 时间 （秒）
     * @desc 计算时间
     * @author ligg
     * @date 2016年8月24日 上午11:43:34
     */
    private long calcTime(List<Date[]> dateList) {
        long totalTime = 0;
        for (Date[] dates : dateList) {
            long[] dateTimes = getDateNum(dates[0], dates[1]);
            totalTime = totalTime + (dateTimes[1] - dateTimes[0]);
        }
        return totalTime;
    }

    /**
     * @param
     * @desc 合并故障时间
     * @author ligg
     * @date 2016年8月8日 下午6:04:57
     */
    private Map<String, Map<String, Object>> mergeTime(
            List<Map<String, Object>> faultList) {
        // 故障时间容器——key: 机构号_报表类型_机具编号 value: 报表信息Map
        Map<String, Map<String, Object>> faultMap = new HashMap<>(100);
        for (Map<String, Object> record : faultList) {
            // 故障开始时间和结束日期
            Date startDate = ReportUtil.getValue(record, "START_TIME");
            Date endDate = ReportUtil.getValue(record, "END_TIME");
            String branchNo = ReportUtil.getValue(record, "BRANCH_NO");
            addDataToGroup(faultMap, record, branchNo, startDate, endDate);
        }
        return faultMap;
    }

    /**
     * 数据组装
     * @param faultMap
     * @param record
     * @param groupKey
     * @param startDate
     * @param endDate
     */
    protected abstract void addDataToGroup(
            Map<String, Map<String, Object>> faultMap,
            Map<String, Object> record, String groupKey, Date startDate,
            Date endDate);

    /**
     * 获取报表类型
     * @return
     */
    protected abstract String getReportType();

    /**
     * @param faultMap  故障时间容器
     * @param record    当前故障
     * @param groupKey  故障类型
     * @param startDate 故障开始时间
     * @param endDate   故障结束时间
     * @desc 合并故障时间并将其放入到新容器中
     * @author ligg
     * @date 2016年8月9日 上午10:49:25
     */

    protected void addFaultData(Map<String, Map<String, Object>> faultMap,
                                Map<String, Object> record, String groupKey, Date startDate,
                                Date endDate) {
        // 外设信息Map
        Map<String, Object> innerMap = null;
        // 根据key值获取故障信息
        innerMap = faultMap.get(groupKey);
        if (innerMap == null) {
            innerMap = new HashMap<>(20);
            innerMap.putAll(record);
            // 时间列表，存放开始时间和结束时间
            List<Date[]> timeList = new ArrayList<>();
            addTime(timeList, startDate, endDate);
            innerMap.put("TIME_LIST", timeList);
            faultMap.put(groupKey, innerMap);
        } else {
            // 获取故障时间
            List<Date[]> timeList = (List<Date[]>) innerMap.get("TIME_LIST");
            addTime(timeList, startDate, endDate);
            innerMap.put("TIME_LIST", timeList);
            faultMap.put(groupKey, innerMap);
        }
    }

    @Override
    public boolean dateCheck() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        return day == 1;
    }

    @Override
    public void callTask(String reportType) {
        OtherMonthReportTask monthTask = new OtherMonthReportTask();
        monthTask.execute(reportType);
    }

    /**
     * 查询统计的数据
     * @param type
     * @param map
     * @return
     */
    public List<Map<String, Object>> exeQuery(String type, Map<String, Object> map) {
        List<Map<String, Object>> result = null;
        try {
            if (type.equals(ReportUtil.ACCOUNT_DEAL)) {
                LOG.info("查询原始数据>>>错账处理处理速度报表 ");
                result = reportDao.queryAccountDeal(map);
            } else if (type.equals(ReportUtil.DEV_TRAN)) {
                LOG.info("查询原始数据>>>自助机具交易质量报表 ");
                result = reportDao.queryDevTran(map);
            } else if (type.equals(ReportUtil.MANAGER_ANSWER)) {
                LOG.info("查询原始数据>>>设备管理员响应速度报表 ");
                result = reportDao.queryManagerAnswer(map);
            } else if (type.equals(ReportUtil.MANAGER_DEAL)) {
                LOG.info("查询原始数据>>>设备管理员处理速度报表 ");
                result = reportDao.queryManagerDeal(map);
            } else if (type.equals(ReportUtil.DEV_COST)) {
                LOG.info("查询原始数据>>>设备费用报表 ");
                result = reportDao.queryDevCost(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.info("执行报表" + type + "时，查询原始数据出现异常 ");
        }
        return result;
    }

    /**
     * 检查是否需要执行日报表
     *
     * @return
     */
    private boolean checkDayReport() {
        boolean flag = true;
        //如果是耗材报表，不需要执行日报表
        if (getReportType().equals(ReportUtil.DEV_SUPPLY)) {
            flag = false;
        }
        return flag;
    }

}
