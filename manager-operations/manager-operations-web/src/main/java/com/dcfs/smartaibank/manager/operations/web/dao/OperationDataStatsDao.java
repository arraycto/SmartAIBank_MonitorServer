package com.dcfs.smartaibank.manager.operations.web.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 操作数据状态DAO
 *
 * @author
 */
@Mapper
public interface OperationDataStatsDao {

	/**
	 * 厅堂营销状态
	 *
	 * @param dateType     day-日,month-月,quarter-季,year-年
	 * @param date         日期
	 * @param superiorDate 上一天
	 */
	void hallMarketStats(@Param("dateType") String dateType,
						 @Param("date") String date,
						 @Param("superiorDate") String superiorDate);

	/**
	 * 厅堂营销用户状态
	 *
	 * @param dateType     day-日,month-月,quarter-季,year-年
	 * @param date         日期
	 * @param superiorDate 上一天
	 */
	void hallMarketUserStats(@Param("dateType") String dateType,
							 @Param("date") String date,
							 @Param("superiorDate") String superiorDate);

	/**
	 * 客户动线状态
	 *
	 * @param dateType     day-日,month-月,quarter-季,year-年
	 * @param date         日期
	 * @param superiorDate 上一天
	 */
	void custRouteStats(@Param("dateType") String dateType,
						@Param("date") String date,
						@Param("superiorDate") String superiorDate);

	/**
	 * 客流   按  年龄  性别  时间  资产 三个维度
	 *
	 * @param dateType     day-日,month-月,quarter-季,year-年
	 * @param date         日期
	 * @param superiorDate 上一天
	 */
	void cusFlowByTime(@Param("date") String date,
					   @Param("dateType") String dateType,
					   @Param("superiorDate") String superiorDate);

	/**
	 * 客流 按 资产信息
	 *
	 * @param dateType     day-日,month-月,quarter-季,year-年
	 * @param date         日期
	 * @param superiorDate 上一天
	 */
	void cusFlowByAsset(@Param("date") String date,
						@Param("dateType") String dateType,
						@Param("superiorDate") String superiorDate);

	/**
	 * 客流 按 性别
	 *
	 * @param dateType     day-日,month-月,quarter-季,year-年
	 * @param date         日期
	 * @param superiorDate 上一天
	 */
	void cusFlowBySex(@Param("date") String date,
					  @Param("dateType") String dateType,
					  @Param("superiorDate") String superiorDate);

	/**
	 * 客流 按 年龄
	 *
	 * @param dateType     day-日,month-月,quarter-季,year-年
	 * @param date         日期
	 * @param superiorDate 上一天
	 */
	void cusFlowByAge(@Param("date") String date,
					  @Param("dateType") String dateType,
					  @Param("superiorDate") String superiorDate);


	/**
	 * 业务效率   按  机构
	 *
	 * @param dateType     day-日,month-月,quarter-季,year-年
	 * @param date         日期
	 * @param superiorDate 上一天
	 */
	void busEffic(@Param("date") String date,
				  @Param("dateType") String dateType,
				  @Param("superiorDate") String superiorDate);

	/**
	 * 业务效率   按  交易
	 *
	 * @param dateType     day-日,month-月,quarter-季,year-年
	 * @param date         日期
	 * @param superiorDate 上一天
	 */
	void busEfficTran(@Param("date") String date,
					  @Param("dateType") String dateType,
					  @Param("superiorDate") String superiorDate);

	/**
	 * 机构汇总数据
	 *
	 * @param dateType  day-日,month-月,quarter-季,year-年
	 * @param timeValue 日期
	 */
	void orgGather(@Param("dateType") String dateType, @Param("date") String timeValue);

	/**
	 * 柜员汇总数据
	 *
	 * @param dateType  day-日,month-月,quarter-季,year-年
	 * @param timeValue 日期
	 */
	void userGather(@Param("dateType") String dateType, @Param("date") String timeValue);

	/**
	 * 柜员数据 按交易
	 *
	 * @param dateType  day-日,month-月,quarter-季,year-年
	 * @param timeValue 日期
	 */
	void userTrans(@Param("dateType") String dateType, @Param("date") String timeValue);

	/**
	 * 排队数据 按客户类型
	 *
	 * @param dateType  day-日,month-月,quarter-季,year-年
	 * @param timeValue 日期
	 */
	void queueByCust(@Param("dateType") String dateType, @Param("date") String timeValue);

	/**
	 * 排队数据 按时间段类型
	 *
	 * @param dateType  day-日,month-月,quarter-季,year-年
	 * @param timeValue 日期
	 */
	void queueByTimeStep(@Param("dateType") String dateType, @Param("date") String timeValue);

	/**
	 * 排队数据 按时长分组类型
	 *
	 * @param dateType  day-日,month-月,quarter-季,year-年
	 * @param timeValue 日期
	 */
	void queueByTimeGroup(@Param("dateType") String dateType, @Param("date") String timeValue);

	/**
	 * 根据日期删除交易数据
	 *
	 * @param date 日期
	 */
	void deleteTradeDataByDate(@Param("date") String date);
}
