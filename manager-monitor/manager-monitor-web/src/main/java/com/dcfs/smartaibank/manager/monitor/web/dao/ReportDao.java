package com.dcfs.smartaibank.manager.monitor.web.dao;

import java.util.List;
import java.util.Map;

/**
 * @desc 故障历史记录DAO
 * @date 2019年8月4日 上午9:33:14
 * @author wangtingo
 */
public interface ReportDao {
	/**
	 * 查询计划工作时间
	 * @author ligg
	 * @date 2016年8月4日 上午9:41:59
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryWorkTime(Map<String, Object> map);

	/**
	 * 查询计划停机时间
	 * @author ligg
	 * @date 2016年8月22日 下午2:05:53
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryShutDownTime(Map<String, Object> map);


	/**
	 * 查询日报表数据
	 * @author ligg
	 * @date 2016年8月23日 下午3:14:20
	 * @param map 参数容器
	 * @return  日报表结果数据
	 */
	List<Map<String, Object>> queryDayReportInfo(Map<String, Object> map);

	/**
	 *  查询月报表数据
	 * @author ligg
	 * @date 2016年8月23日 下午3:15:07
	 * @param map 参数容器
	 * @return  月报表结果数据
	 */
	List<Map<String, Object>> queryMonthReportInfo(Map<String, Object> map);

	/**
	 * 查询季报表数据
	 * @author ligg
	 * @date 2016年8月23日 下午3:15:36
	 * @param map 参数容器
	 * @return  季报表结果数据
	 */
	List<Map<String, Object>> queryPeriodReportInfo(Map<String, Object> map);


	/**
	 * 插入日报表数据
	 * @author wangtingo
	 * @date 2016年8月22日 下午2:07:11
	 * @param dataList
	 */
	void insertDayReport(List<Map<String, Object>> dataList);

	/**
	 * 插入月报表数据
	 * @author wangtingo
	 * @date 2016年8月22日 下午6:09:48
	 * @param dataList
	 */
	void insertMonthReport(List<Map<String, Object>> dataList);

	/**
	 * 插入季报表数据
	 * @author wangtingo
	 * @date 2016年8月22日 下午6:09:56
	 * @param dataList
	 */
	void insertPeriodReport(List<Map<String, Object>> dataList);

	/**
	 * 插入年报表数据
	 * @author wangtingo
	 * @date 2016年8月22日 下午6:10:02
	 * @param dataList
	 */
	void insertYearReport(List<Map<String, Object>> dataList);

	/**
	 * 删除日报表数据
	 * @param reportInfoMap
	 */
	void deleteDayReport(Map<String, Object> reportInfoMap);

	/**
	 * 删除月报表数据
	 * @param reportInfoMap
	 */
	void deleteMonthReport(Map<String, Object> reportInfoMap);

	/**
	 * 删除季报表数据
	 * @param reportInfoMap
	 */
	void deletePeriodReport(Map<String, Object> reportInfoMap);

	/**
	 * 删除年报表数据
	 * @param reportInfoMap
	 */
	void deleteYearReport(Map<String, Object> reportInfoMap);


	/**
	 * 查询错账处理记录
	 * @date 2017年5月13日 上午9:41:59
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryAccountDeal(Map<String, Object> map);

	/**
	 * 查询自助机具的交易数据
	 * @date 2017年6月27日
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryDevTran(Map<String, Object> map);

	/**
	 * 查询设备管理员响应记录
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryManagerAnswer(Map<String, Object> map);

	/**
	 * 查询设备管理员处理记录
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryManagerDeal(Map<String, Object> map);

	/**
	 * 查询设备费用
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryDevCost(Map<String, Object> map);

	/**
	 * 查询设备耗材
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> queryDevSupply(Map<String, Object> map);
	/**
	 * 查询日报表数据（SZS版本）
	 * @date 2017年7月9日 下午3:14:20
	 * @param map 参数容器
	 * @return  日报表结果数据
	 */
	List<Map<String, Object>> querySzsDayReportInfo(Map<String, Object> map);

	/**
	 * 查询月报表数据（SZS版本）
	 * @date 2017年7月9日 下午3:14:20
	 * @param map 参数容器
	 * @return  日报表结果数据
	 */
	List<Map<String, Object>> querySzsMonthReportInfo(Map<String, Object> map);

	/**
	 * 查询季报表数据（SZS版本）
	 * @date 2017年7月9日 下午3:14:20
	 * @param map 参数容器
	 * @return  日报表结果数据
	 */
	List<Map<String, Object>> querySzsPeriodReportInfo(Map<String, Object> map);

	/**
	 * 插入日报表数据（SZS版本）
	 * @param dataList
	 * @author qiuch
	 */
	void insertSzsDayReport(List<Map<String, Object>> dataList);

	/**
	 * 插入月报表数据（SZS版本）
	 * @date 2016年8月22日 下午6:09:48
	 * @param dataList
	 */
	void insertSzsMonthReport(List<Map<String, Object>> dataList);

	/**
	 * 插入季报表数据（SZS版本）
	 * @date 2016年8月22日 下午6:09:48
	 * @param dataList
	 */
	void insertSzsPeriodReport(List<Map<String, Object>> dataList);

	/**
	 * 插入年报表数据（SZS版本）
	 * @author wangtingo
	 * @date 2016年8月22日 下午6:09:48
	 * @param dataList
	 */
	void insertSzsYearReport(List<Map<String, Object>> dataList);

	/**
	 * 删除日报表数据（SZS版本）
	 * @param reportInfoMap
	 */
	void deleteSzsDayReport(Map<String, Object> reportInfoMap);

	/**
	 * 删除月报表数据（SZS版本）
	 * @param reportInfoMap
	 */
	void deleteSzsMonthReport(Map<String, Object> reportInfoMap);

	/**
	 * 删除季报表数据（SZS版本）
	 * @param reportInfoMap
	 */
	void deleteSzsPeriodReport(Map<String, Object> reportInfoMap);

	/**
	 * 删除年报表数据（SZS版本）
	 * @param reportInfoMap
	 */
	void deleteSzsYearReport(Map<String, Object> reportInfoMap);

}
