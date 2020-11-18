package com.dcfs.smartaibank.manager.operations.web.dao;

import com.dcfs.smartaibank.manager.operations.web.domain.CommonDistriAndCtDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonDistributionDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonRankDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 服务质量DAO
 *
 * @author
 */
@Mapper
public interface ServiceQualityDao {

	/**
	 * 获取指定机构 指定服务质量类型 和 指定时间的 评价率(有三种类型：评价率，好评率，差评率)全行平均值和环比增加百分比数据
	 *
	 * @param branchStatsType 机构运营类型 默认为0，营业兼管理机构类型时，营业=1，管理=2
	 * @param orgId           机构编号
	 * @param dateType        时间类型
	 * @param timeValue       时间值
	 * @param qualityType     评价类型
	 * @return 时间分组统计实体
	 */
	CommonDistriAndCtDomain getServiceQualityType(@Param("orgId") String orgId,
												  @Param("dateType") String dateType,
												  @Param("timeValue") String timeValue,
												  @Param("qualityType") String qualityType,
												  @Param("branchStatsType") Integer branchStatsType);

	/**
	 * 获取 指定时间 指定服务质量类型 指定机构的evaluteValue评价值 前十的子机构
	 *
	 * @param orgId           机构编号
	 * @param dateType        时间类型
	 * @param timeValue       时间值
	 * @param qualityType     评价类型
	 * @param branchStatsType 机构运营类型 默认为0，营业兼管理机构类型时，营业=1，管理=2
	 * @return 排名信息实体集合
	 */
	List<CommonRankDomain> getSubBranchRankTop10List(@Param("orgId") String orgId,
													 @Param("dateType") String dateType,
													 @Param("timeValue") String timeValue,
													 @Param("qualityType") String qualityType,
													 @Param("branchStatsType") Integer branchStatsType);

	/**
	 * 获取指定时间的指定  服务类型的 指定区间段  指定机构Group组
	 *
	 * @param branchStatsType 机构运营类型 默认为0，营业兼管理机构类型时，营业=1，管理=2
	 * @param orgId           机构编号
	 * @param dateType        时间类型
	 * @param timeValue       时间值
	 * @param qualityType     评价类型
	 * @return 时间分布实体集合
	 */
	List<CommonDistributionDomain> getTimeGroupServiceQualityList(@Param("orgId") String orgId,
																  @Param("dateType") String dateType,
																  @Param("timeValue") String timeValue,
																  @Param("qualityType") String qualityType,
																  @Param("branchStatsType") Integer branchStatsType);

}
