package com.dcfs.smartaibank.manager.operations.web.service;

import com.dcfs.smartaibank.manager.operations.web.domain.SumInfoDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.SumItemInfoDomain;

import java.util.Map;

/**
 * 机构信息汇总service
 *
 * @Author qiuch
 * @Since 1.0.0
 */
public interface OrgSumInfoService {

	/**
	 * 查询指定机构的在指定时间的客流、营销、排队、服务质量等统计信息
	 *
	 * @param paramMap 机构编号，查询日期类型，日期值，机构数据统计类型
	 * @return 概况汇总单独统计项实体
	 */
	SumInfoDomain getOrgSumInfo(Map<String, Object> paramMap);

	/**
	 * 查询指定机构的在指定时间的客流的接待数、全行均值等统计信息
	 *
	 * @param paramMap 机构编号，查询日期类型，日期值，机构数据统计类型
	 * @return 概况汇总统计实体
	 */
	SumItemInfoDomain getCustFlowSumInfo(Map<String, Object> paramMap);

	/**
	 * 查询指定机构排队概况统计
	 *
	 * @param paramMap 机构编号，查询日期类型，日期值，机构数据统计类型
	 * @return 概况汇总统计实体
	 */
	SumItemInfoDomain getQueueSumInfo(Map<String, Object> paramMap);

	/**
	 * 查询指定机构营销概况统计
	 *
	 * @param paramMap 机构编号，查询日期类型，日期值，机构数据统计类型
	 * @return 概况汇总统计实体
	 */
	SumItemInfoDomain getHallMarketSumInfo(Map<String, Object> paramMap);

	/**
	 * 查询指定机构服务质量概况统计
	 *
	 * @param paramMap 机构编号，查询日期类型，日期值，机构数据统计类型
	 * @return 概况汇总统计实体
	 */
	SumItemInfoDomain getServiceQualitySumInfo(Map<String, Object> paramMap);

	/**
	 * 查询指定机构业务效率概况统计
	 *
	 * @param paramMap 机构编号，查询日期类型，日期值，机构数据统计类型
	 * @return 概况汇总统计实体
	 */
	SumItemInfoDomain getBusiEffictSumInfo(Map<String, Object> paramMap);

}
