package com.dcfs.smartaibank.manager.monitor.web.job;

import com.dcfs.smartaibank.manager.monitor.analyzer.dao.FaultRecordDao;
import com.dcfs.smartaibank.manager.monitor.core.util.SpringContextUtil;
import com.dcfs.smartaibank.manager.monitor.web.dao.ReportDao;
import com.dcfs.smartaibank.manager.monitor.web.job.task.IReportTask;
import com.dcfs.smartaibank.manager.monitor.web.job.task.MonthReportTask;
import com.dcfs.smartaibank.manager.monitor.web.util.DateUtil;
import com.dcfs.smartaibank.manager.monitor.web.util.ReportUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @desc 日报表定时任务基类
 * @date 2019年7月24日 下午2:29:22
 * @author wangtingo
 */
@Slf4j
@DisallowConcurrentExecution
public abstract class AbstractDayReportJob extends QuartzJobBean implements IReportTask {

	private static final Logger LOG = LoggerFactory
			.getLogger(AbstractDayReportJob.class);
	@Autowired
	private FaultRecordDao faultDao;
	@Autowired
	private ReportDao reportDao;

	@Override
	public void executeInternal(JobExecutionContext context) {

		makeDayReport();

		// 调用月报表生成任务
		if (dateCheck()) {
			callTask(getReportType());
		}

	}

	private void makeDayReport() {
		LOG.debug("开始执行日报表定时任务>>>>>> ");
		if (faultDao == null) {
			faultDao = (FaultRecordDao) SpringContextUtil.getBean("faultDao");
			reportDao = (ReportDao) SpringContextUtil.getBean("reportDao");
		}
		try {
				// 获得当前时间
				Date date = ReportUtil.getReportDate();
				String dateStr = DateUtil.getStrSplitDate(date);
				Map<String, Object> queryMap = new HashMap<>(3);
				queryMap.put("REPORT_DATE", dateStr);
				// 查询故障信息，故障信息以故障开始时间的升序排列
				List<Map<String, Object>> result = faultDao.queryAllInfo(queryMap);
				// 合并故障时间
				Map<String, Map<String, Object>> faultTimeMap = mergeFaultTime(result);

				// 以下逻辑待完善
				if (!faultTimeMap.isEmpty()) {
					// 查询计划工作时间列表，结果以设备ID和工作开始时间的升序排序
					List<Map<String, Object>> workTimeList = reportDao
							.queryWorkTime(queryMap);
					if (workTimeList.isEmpty()) {
						LOG.info("计划工作时间为空，生成日报表失败！");
						return;
					}
					// 查询计划停机时间列表，结果以设备ID和工作开始时间的升序排序
					List<Map<String, Object>> shutDownTimeList = reportDao
							.queryShutDownTime(queryMap);

					// 从计划工作时间中过滤掉计划停机时间，重新计算计划工作时间
					Map<String, Object> workTime = filterWorkTime(workTimeList,
							shutDownTimeList);

					// 重置故障时间
					Map<String, Map<String, Object>> newFaultTimeMap = resetFaultTime(
							faultTimeMap, workTime);

					// 计算并保存故障时间
					calcAndSaveData(newFaultTimeMap, date);
					LOG.info(">>>>>> 日报表定时任务执行完毕,报表生成成功!");

				} else {
					LOG.info(">>>>>> 日报表定时任务执行完毕,无故障数据，无法生成日报表数据！");
				}
			} catch (Exception e) {
				LOG.error(">>>>>> 日报表定时任务执行失败:", e);
			}

	}

	/**
	 * 将计划停机时间从工作时间中排出掉
	 * @author ligg
	 * @date 2016年8月19日 上午11:26:23
	 * @param workTimeList
	 *            计划工作时间容器
	 * @param shutDownTimeList
	 *            计划停机时间容器
	 * @return
	 */
	private Map<String, Object> filterWorkTime(
			List<Map<String, Object>> workTimeList,
			List<Map<String, Object>> shutDownTimeList) {
		// 存放实际工作时间
		Map<String, Object> workTimeMap = new HashMap<>(100);

		for (Map<String, Object> workMap : workTimeList) {
			// 计划工作开始时间和结束时间
			Date startDate = ReportUtil.getValue(workMap, "START_TIME");
			Date endDate = ReportUtil.getValue(workMap, "END_TIME");
			String orgNo = ReportUtil.getValue(workMap, "BRANCH_NO");
			String mid = ReportUtil.getValue(workMap, "DEVICEID");
			String groupKey = ReportUtil.getKey(orgNo, mid);
			List<Date[]> timeList = new ArrayList<>();
			// 如果计划停机时间为空，则直接将计划工作时间放入新容器
			addTime(timeList, startDate, endDate);
			workTimeMap.put(groupKey, timeList);
			timeList = timeDate(timeList, shutDownTimeList, workTimeMap,
					groupKey, startDate, endDate);
			workTimeMap.put(groupKey, timeList);
		}
		return workTimeMap;
	}
	private List<Date[]> timeDate(List<Date[]> timeList,
			List<Map<String, Object>> shutDownTimeList, Map<String, Object> workTimeMap,
			String groupKey, Date startDate, Date endDate) {
		long startTime = DateUtil.getTime(startDate);
		long endTime = DateUtil.getTime(endDate);
		int len = shutDownTimeList.size();
		// 计划停机开始时间基准变量，计划停机结束时间基准变量
		long startDownTime = 0; long endDownTime = 0;
		// 计划停机时间段索引
		int index = 0;
		// 循环计划停机时间，将其排除在工作时间之外
		for (Map<String, Object> downMap : shutDownTimeList) {
			index = index + 1;
			String branchNo = ReportUtil.getValue(downMap, "BRANCH_NO");
			String deviceId = ReportUtil.getValue(downMap, "DEVICEID");
			String tempKey = ReportUtil.getKey(branchNo, deviceId);
			// 判断是否同一个机具
			if (groupKey.equals(tempKey)) {
				// 计划停机开始时间和结束时间
				Date startDownDate = ReportUtil.getValue(downMap, "START_TIME");
				Date endDownDate = ReportUtil.getValue(downMap, "END_TIME");
				// 计划停机当前开始时间
				long[] tempTimes = getDateNum(startDownDate, endDownDate);
				if (tempTimes[0] < startDownTime) {
					continue;
				}
				// 将当前时间赋值给基准时间变量
				startDownTime = tempTimes[0];
				endDownTime = tempTimes[1];
				// 获得工作时间
				if (timeList == null) {
					timeList = new ArrayList<>();
				}
				/**T1: 计划工作开始时间; T2: 计划工作结束时间; T3：  计划停机开始时间; T4： 计划停机结束时间;*/
				if (endDownTime <= startTime) {
					/** T3|________|T4;  T1|_______|T2 */
					if (len == index) {
						addTime(timeList, startDate, endDate);
						workTimeMap.put(groupKey, timeList);
					} else {
						continue;
					}
				} else if (startDownTime >= endTime) {
					/**T1|___________|T2; T3|________|T4; */
					addTime(timeList, startDate, endDate);
					workTimeMap.put(groupKey, timeList);
					break;
				} else if (startDownTime >= startTime && endDownTime <= endTime) {
					/** T1|__________________|T2
					 *  	T3|____________|T4 */
					addTime(timeList, startDate, startDownDate);
					startDate = endDownDate;
					startTime = endDownTime;
					// 如果是最后一条停机记录，则应将T4——T2时间段也添加到容器中
					if (len == index && startTime != endTime) {
						addTime(timeList, startDate, endDate);
					}
				} else if (startDownTime < startTime && endDownTime >= startTime
						&& endDownTime <= endTime) {
					/**       T1|______________|T2
					 * T3|_________|T4  T5|___________|T6 */
					if (index < len) {
						startDate = endDownDate;
						startTime = endDownTime;
					} else {
						addTime(timeList, endDownDate, endDate);
					}
				} else if (startDownTime > startTime && startDownTime < endTime
						&& endDownTime > endTime) {
					/** T1|_________|T2
					 *  	 T3|_________|T4 */
					addTime(timeList, startDate, startDownDate);
					endDate = startDownDate;
					endTime = startDownTime;
				}
			}
		}
		return timeList;
	}

	/**
	 * @desc 将开始时间和结束时间天津到时间列表中
	 * @author ligg
	 * @date 2016年8月19日 上午10:26:21
	 * @param timeList
	 *            存放开始时间和结束时间的列表容器
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 */
	private void addTime(List<Date[]> timeList, Date startDate, Date endDate) {
		Date[] dateObj = new Date[2];
		dateObj[0] = startDate;
		dateObj[1] = endDate;
		timeList.add(dateObj);
	}

	/**
	 * @desc 重新设置故障时间
	 * @author ligg
	 * @date 2016年8月9日 上午11:57:47
	 * @return
	 */
	private Map<String, Map<String, Object>> resetFaultTime(
			Map<String, Map<String, Object>> faultTimeMap,
			Map<String, Object> workTimeMap) {

		Map<String, Map<String, Object>> errorTimeMap = new HashMap<>(100);

		for (String type : faultTimeMap.keySet()) {
			String[] types = type.split(ReportUtil.STR_SPLIT);
			String groupKey = ReportUtil.getKey(types[0], types[2]);
			// 判断是否配置工作时间
			List<Date[]> workTimeList = (List<Date[]>) workTimeMap
					.get(groupKey);
			boolean findFlag = workTimeList != null ? true : false;
			if (findFlag) {
				Map<String, Object> dataMap = faultTimeMap.get(type);
				List<Date[]> timeList = (List<Date[]>) dataMap
						.get("TIME_LIST");
				List<Date[]> newDateList = new ArrayList<>();

				// 循环故障时间
				for (Date[] dates : timeList) {
					long[] datesNum = getDateNum(dates[0], dates[1]);
					// 当前故障开始时间和结束时间
					long startTime = datesNum[0];
					long endTime = datesNum[1];
					for (Date[] workDates : workTimeList) {
						long[] workDatesNum = getDateNum(workDates[0],
								workDates[1]);
						long workStartTime = workDatesNum[0];
						long workEndTime = workDatesNum[1];
						/** T1：  故障开始时间; T2： 故障结束时间;  T3: 计划工作开始时间; T4: 计划工作结束时间 */
						/** 	T1|_________|T2
						 *  T3|________________|T4
						 *  故障开始时间 >= 计划工作开始时间 && 故障结束时间 <= 计划工作结束时间 */
						if (startTime >= workStartTime
								&& endTime <= workEndTime) {
							newDateList.add(dates);
							break;
						} else if (endTime < workStartTime
								|| startTime > workEndTime) {
							/**  T1|_________|T2               T5|______|T6
							 *                 T3|_______|T4
							 *  故障结束时间 < 计划工作开始时间 || 故障开始时间 > 计划工作结束时间 */
							continue;
						} else if (startTime < workStartTime
								&& endTime >= workStartTime
								&& endTime <= workEndTime) {
							/** T1|_________|T2
							 *       T3|__________|T4
							 *  故障开始时间 < 计划工作开始时间 && 故障结束时间 >= 计划工作开始时间 && 故障结束时间 <=计划工作结束时间 */
							addTime(newDateList, workDates[0], dates[1]);
						} else if (startTime >= workStartTime
								&& startTime <= workEndTime
								&& endTime > workEndTime) {
							/**             T1|_________|T2
							 *       T3|__________|T4
							 *  故障开始时间 >= 计划工作开始时间 && 故障开始时间 <= 计划工作开始时间 && 故障结束时间 > 计划工作结束时间 */
							addTime(newDateList, dates[0], workDates[1]);
						} else if (startTime <= workStartTime
								&& endTime >= workEndTime) {
							/**	 T1|__________________|T2
							 *       T3|__________|T4
							 *  故障开始时间 <= 计划工作开始时间 && 故障结束时间 >= 计划工作结束时间 */
							addTime(newDateList, workDates[0], workDates[1]);
						}
					}
				}
				// 故障时间列表
				dataMap.put("TIME_LIST", newDateList);
				// 工作时间列表
				dataMap.put("WORK_TIME_LIST", workTimeList);
				errorTimeMap.put(type, dataMap);
			}
		}
		return errorTimeMap;
	}

	/**
	 * @desc 获得日期的数字格式
	 * @author ligg
	 * @date 2016年8月19日 下午3:02:30
	 * @param startDate
	 * @param endDate
	 * @return [0] 开始时间 [1]结束时间
	 */
	private long[] getDateNum(Date startDate, Date endDate) {
		// 当前故障开始时间和结束时间
		long startTime = DateUtil.getTime(startDate);
		long endTime = Long.MAX_VALUE;
		// 不为空表示故障已恢复,取故障结束时间,否则取最大值
		if (endDate != null) {
			endTime = DateUtil.getTime(endDate);
		}
		return new long[] {startTime, endTime };
	}

	/**
	 * @desc 计算故障时间和应工作时间，并对数据进行加工处理
	 * @author ligg
	 * @date 2016年8月9日 上午11:58:01
	 * @param date
	 *            报表生成日期
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private void calcAndSaveData(Map<String, Map<String, Object>> faultTimeMap,
			Date date) {
		String mtype = "";
		for (String key : faultTimeMap.keySet()) {
			String[] keys = key.split(ReportUtil.STR_SPLIT);
			mtype =  keys[1];
			break;
		}

		Map<String, Object>  reportInfoMap = new HashMap<>(5);
		reportInfoMap.put("M_TYPE", mtype);
		reportInfoMap.put("CREATE_TIME", date);
		reportDao.deleteDayReport(reportInfoMap);
		// 待保存的数据容器
		List<Map<String, Object>> faultTimeList = new ArrayList<>();
		// 遍历故障数据，对其进行加工处理
		for (String key : faultTimeMap.keySet()) {
			String[] keys = key.split(ReportUtil.STR_SPLIT);
			Map<String, Object> innerMap = faultTimeMap.get(key);
			List<Date[]> errorDateList = (List<Date[]>) innerMap
					.get("TIME_LIST");
			List<Date[]> workDateList = (List<Date[]>) innerMap
					.get("WORK_TIME_LIST");
			// 计算故障总时间
			long errorTime = calcTime(errorDateList);
			// 计算应工作时间
			long workTime = calcTime(workDateList);
			// 故障时间
			innerMap.put("FAULT_TIME_INT", errorTime);
			// 工作时间
			innerMap.put("NORMAL_TIME_INT", workTime);
			// 故障类型
			innerMap.put("M_TYPE", keys[1]);
			/**
			 * DEVICE: 设备， NETWORK: 网络， PERIPHERAL:外设， APP：应用， TOOL:机具
			 */
			if (!ReportUtil.DEV_DEVICE.equals(keys[1])) {
				innerMap.put("DEVMODELKEY", "");
				innerMap.put("DEVCLASSKEY", "");
				innerMap.put("DEVCLASSNAME", "");
			}
			// 报表生成时间
			innerMap.put("CREATE_TIME", date);

			// 移除掉无用的数据项
			innerMap.remove("TIME_LIST");
			innerMap.remove("WORK_TIME_LIST");
			innerMap.remove("SEQ_NO");
			if (!innerMap.containsKey("MANUF_ID")) {
				innerMap.put("MANUF_ID", "");
			}
			if (!innerMap.containsKey("MANUF_NAME")) {
				innerMap.put("MANUF_NAME", "");
			}
			faultTimeList.add(innerMap);
			// 批量保存日报表数据,每1000条记录批量保存一次
			if (faultTimeList.size() == ReportUtil.BATCH_SAVE_SIZE) {
				reportDao.insertDayReport(faultTimeList);
				faultTimeList.clear();
			}
		}

		// 批量保存日报表数据
		if (!faultTimeList.isEmpty()) {
			reportDao.insertDayReport(faultTimeList);
		}
	}

	/**
	 * @desc 计算时间
	 * @author ligg
	 * @date 2016年8月24日 上午11:43:34
	 * @param dateList
	 *            时间段列表
	 * @return 时间 （秒）
	 */
	private long calcTime(List<Date[]> dateList) {
		long totalTime = 0;
		for (Date[] dates : dateList) {
			long[] dateTimes = getDateNum(dates[0], dates[1]);
			totalTime = totalTime + (dateTimes[1] - dateTimes[0]);
		}
		totalTime = totalTime / 1000;
		return totalTime;
	}

	/**
	 * @desc 合并故障时间
	 * @author ligg
	 * @date 2016年8月8日 下午6:04:57
	 * @param
	 */
	private Map<String, Map<String, Object>> mergeFaultTime(
			List<Map<String, Object>> faultList) {
		// 故障时间容器——key: 机构号_报表类型_机具编号 value: 报表信息Map
		Map<String, Map<String, Object>> faultMap = new HashMap<>(100);

		for (Map<String, Object> record : faultList) {
			// 故障开始时间和结束日期
			Date startDate = ReportUtil.getValue(record, "START_TIME");
			Date endDate = ReportUtil.getValue(record, "END_TIME");
			/**
			 * 故障类型 DEVICE: 设备， NETWORK: 网络， PERIPHERAL:外设， APP：应用， TOOL:机具
			 */
			String type = ReportUtil.getValue(record, "M_TYPE");
			String branchNo = ReportUtil.getValue(record, "BRANCH_NO");
			// 机具编号
			String mid = ReportUtil.getValue(record, "MID");
			String devclasskey = ReportUtil.getValue(record, "DEVCLASSKEY");
			// 基本键
			String baseKey = ReportUtil.getKey(branchNo, type, mid);
			if (ReportUtil.DEV_DEVICE.equals(type)) {
				baseKey = ReportUtil.getKey(branchNo, type, mid, devclasskey);
			}


			addDataToGroup(faultMap, record, baseKey, startDate, endDate);
		}
		return faultMap;
	}

	/**
	 * 对数据进行分组
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
	 * @desc 合并故障时间并将其放入到新容器中
	 * @author ligg
	 * @date 2016年8月9日 上午10:49:25
	 * @param faultMap
	 *            故障时间容器
	 * @param record
	 *            当前故障
	 * @param groupKey
	 *            故障类型
	 * @param startDate
	 *            故障开始时间
	 * @param endDate
	 *            故障结束时间
	 */
	@SuppressWarnings("unchecked")
	protected void addFaultData(Map<String, Map<String, Object>> faultMap,
			Map<String, Object> record, String groupKey, Date startDate,
			Date endDate) {
		// 外设信息Map
		Map<String, Object> innerMap = null;
		long[] currentTimes = getDateNum(startDate, endDate);
		// 当前故障开始时间和结束时间
		long currentStartTime = currentTimes[0];
		long currentEndTime = currentTimes[1];
		// 根据key值获取故障信息
		innerMap = faultMap.get(groupKey);
		if (innerMap == null) {
			innerMap = new HashMap<>(20);
			innerMap.putAll(record);
			// 时间列表，存放开始时间和结束时间
			List<Date[]> timeList = new ArrayList<Date[]>();
			addTime(timeList, startDate, endDate);
			innerMap.put("TIME_LIST", timeList);
			faultMap.put(groupKey, innerMap);
		} else {
			// 获取故障时间
			List<Date[]> timeList = (List<Date[]>) innerMap.get("TIME_LIST");
			Date[] faultDates = timeList.get(timeList.size() - 1);
			Date baseStartDate = ReportUtil.getValue(innerMap, "START_TIME");
			Date baseEndDate = ReportUtil.getValue(innerMap, "END_TIME");
			// 基准故障时间（开始时间和结束时间）
			long[] preTimes = getDateNum(baseStartDate, baseEndDate);
			long baseStartTime = preTimes[0];
			long baseEndTime = preTimes[1];
			// 当前故障开始时间 >= 基准故障开始时间 && 当前故障开始时间 <= 基准故障结束时间 && 当前故障结束时间 >
			// 基准故障结束时间
			if (currentStartTime >= baseStartTime
					&& currentStartTime <= baseEndTime
					&& currentEndTime > baseEndTime) {
				innerMap.put("END_TIME", endDate);
				faultDates[1] = endDate;
			} else if (currentStartTime > baseEndTime) {
				// 当前故障开始时间 > 基准故障结束时间
				innerMap.put("START_TIME", startDate);
				innerMap.put("END_TIME", endDate);
				addTime(timeList, startDate, endDate);
			}
			// 其余情况保持不变
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
		MonthReportTask monthTask = new MonthReportTask();
		monthTask.execute(reportType);
	}
}
