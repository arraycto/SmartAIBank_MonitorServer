package com.dcfs.smartaibank.manager.operations.web.dao;

import com.dcfs.smartaibank.manager.operations.web.domain.CommonDistributionDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonGroupDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonRankDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户排队时长DAO
 *
 * @Author qiuch
 * @Since 1.0.0
 */
@Mapper
public interface QueueCustDao {
	/**
	 * 获取排队时间分布汇总信息
	 *
	 * @param orgId           机构id
	 * @param dateType        日期类型
	 * @param timeValue       日期值
	 * @param branchStatsType 机构数据统计类型
	 * @return 时间分布实体集合
	 */
	List<CommonDistributionDomain> getQueueTimeGatherList(@Param("ORG_ID") String orgId,
														  @Param("DATE_TYPE") String dateType,
														  @Param("TIME_VALUE") String timeValue,
														  @Param("BRANCH_STATS_TYPE") Integer branchStatsType);

	/**
	 * 获取排队市场分组信息
	 *
	 * @param orgId           机构id
	 * @param dateType        日期类型
	 * @param timeValue       日期值
	 * @param branchStatsType 机构数据统计类型
	 * @param type            客户类型
	 * @return 时长分组统计实体
	 */
	List<CommonGroupDomain> getQueueTimeGroupList(@Param("ORG_ID") String orgId,
												  @Param("DATE_TYPE") String dateType,
												  @Param("TIME_VALUE") String timeValue,
												  @Param("CUSTOMER_TYPE") String type,
												  @Param("BRANCH_STATS_TYPE") Integer branchStatsType);

	/**
	 * 获取排队时间分布信息
	 *
	 * @param orgId           机构id
	 * @param dateType        日期类型
	 * @param timeValue       日期值
	 * @param type            客户类型
	 * @param branchStatsType 机构数据统计类型
	 * @return 时间分布实体
	 */
	List<CommonDistributionDomain> getTimeDistributionByCustTypeList(
		@Param("ORG_ID") String orgId,
		@Param("DATE_TYPE") String dateType,
		@Param("TIME_VALUE") String timeValue,
		@Param("CUSTOMER_TYPE") String type,
		@Param("BRANCH_STATS_TYPE") Integer branchStatsType);

	/**
	 * 获取排队时长分组统计信息
	 *
	 * @param orgId           机构id
	 * @param dateType        日期类型
	 * @param timeValue       日期值
	 * @param branchStatsType 机构数据统计类型
	 * @return 时长分组统计实体
	 */
	List<CommonGroupDomain> getQueueTimeDomainList(@Param("ORG_ID") String orgId,
												   @Param("DATE_TYPE") String dateType,
												   @Param("TIME_VALUE") String timeValue,
												   @Param("BRANCH_STATS_TYPE") Integer branchStatsType);

	/**
	 * 获取排队排名信息
	 *
	 * @param orgId           机构id
	 * @param dateType        日期类型
	 * @param timeValue       日期值
	 * @param branchStatsType 机构数据统计类型
	 * @return 排名信息实体
	 */
	List<CommonRankDomain> getQueueTimeRankList(@Param("ORG_ID") String orgId,
												@Param("DATE_TYPE") String dateType,
												@Param("TIME_VALUE") String timeValue,
												@Param("BRANCH_STATS_TYPE") Integer branchStatsType);

	/**
	 * 获取排队时间分布信息
	 *
	 * @param orgId           机构id
	 * @param dateType        日期类型
	 * @param timeValue       日期值
	 * @param branchStatsType 机构数据统计类型
	 * @return 时间分布实体
	 */
	CommonDistributionDomain getAvgUnitNo(@Param("ORG_ID") String orgId,
										  @Param("DATE_TYPE") String dateType,
										  @Param("TIME_VALUE") String timeValue,
										  @Param("BRANCH_STATS_TYPE") Integer branchStatsType);

	/**
	 * 获取排队时间分布信息(按客户类型分类)
	 *
	 * @param orgId           机构id
	 * @param dateType        日期类型
	 * @param timeValue       日期值
	 * @param branchStatsType 机构数据统计类型
	 * @param type            客户类型
	 * @return 时间分布实体
	 */
	CommonDistributionDomain getCustTypeAvgUnitNo(@Param("ORG_ID") String orgId,
												  @Param("DATE_TYPE") String dateType,
												  @Param("TIME_VALUE") String timeValue,
												  @Param("BRANCH_STATS_TYPE") Integer branchStatsType,
												  @Param("CUSTOMER_TYPE") String type);
}
