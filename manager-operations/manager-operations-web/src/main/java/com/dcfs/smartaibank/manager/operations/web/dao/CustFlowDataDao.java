package com.dcfs.smartaibank.manager.operations.web.dao;

import com.dcfs.smartaibank.manager.operations.web.domain.CommonDistributionDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonRankDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CustFlowDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 客户动线DAO
 *
 * @author
 */
@Mapper
public interface CustFlowDataDao {

	/**
	 * 获取指定机构、指定时间的平均客流量
	 *
	 * @param paramMap 参数包括机构编号、时间类型、指定时间的值
	 * @return 客户在指定时间的平均值
	 */
	CustFlowDomain getAvgCustFlow(Map<String, Object> paramMap);

	/**
	 * 获取上级机构、指定时间的平均客流量
	 *
	 * @param paramMap 参数包括机构编号、时间类型、指定时间的值
	 * @return 客户在指定时间的平均值
	 */
	CustFlowDomain getAvgCustFlowUnitNo(Map<String, Object> paramMap);

	/**
	 * 获取指定机构，指定时间类型的客流变化列表,比如，传递月份，获取10月份之后的十个月的客流变化数据
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<CommonDistributionDomain> getCustFlowByTimeGroup(Map<String, Object> paramMap);

	/**
	 * 获取指定机构，指定时间类型的分时段对私/对公客户客流变化
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<CustFlowDomain> getCustFlowForCustType(Map<String, Object> paramMap);

	/**
	 * 查询对私客户客户流量的变化
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<CustFlowDomain> getCustFlowForCustTypeForPrivate(Map<String, Object> paramMap);

	/**
	 * 查询客流变化汇总信息
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	int getCustFlowForCustTypeForSum(Map<String, Object> paramMap);

	/**
	 * 获取指定机构、指定时间类型的不同性别的客户流变化
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<Map<String, Object>> getCustFlowBySex(Map<String, Object> paramMap);

	/**
	 * 获取指定机构、指定时间类型的不同年龄段区间的客户流变化
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<Map<String, Object>> getCustFlowByAge(Map<String, Object> paramMap);

	/**
	 * 获取指定机构、指定时间类型的不同客户资产段区间的客户流变化
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<Map<String, Object>> getCustFlowByAssets(Map<String, Object> paramMap);

	/**
	 * 获取指定机构子机构在指定日期类型时间内对应的客流量排名
	 *
	 * @param paramMap 包括日期类型，日期值、机构编号
	 * @return
	 */
	List<CommonRankDomain> getSubBranchCustFlowRank(Map<String, Object> paramMap);

	/**
	 * 获取对公对私客户数
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	Map<String, Object> getPrivateCount(Map<String, Object> paramMap);

	/**
	 * 查询对公客户数
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	Map<String, Object> getPublicCount(Map<String, Object> paramMap);
}
