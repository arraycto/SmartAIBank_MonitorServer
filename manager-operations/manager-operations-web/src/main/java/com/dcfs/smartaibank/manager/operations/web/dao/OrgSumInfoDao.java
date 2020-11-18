package com.dcfs.smartaibank.manager.operations.web.dao;

import com.dcfs.smartaibank.manager.operations.web.domain.CommonRankDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.SumItemInfoDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 机构统计信息DAO
 *
 * @author
 */
@Mapper
public interface OrgSumInfoDao {

	/**
	 * 获取指定机构的营销、排队、接待客户数、预约产品数、交易平均处理时间等汇总信息。
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	Map<String, Object> getOrgSumInfo(Map<String, Object> paramMap);

	/**
	 * 获取服务质量Top5
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<CommonRankDomain> getServiceQualityTop5(Map<String, Object> paramMap);

	/**
	 * 获取平均等待时长
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	SumItemInfoDomain getAvgWaiteTime(Map<String, Object> paramMap);

	/**
	 * 获取平均等待市场top5
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<CommonRankDomain> getAvgWaiteTimeTop5(Map<String, Object> paramMap);

	/**
	 * 获取子机构客流排名
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<CommonRankDomain> getSubBranchCustFlowRank(Map<String, Object> paramMap);

	/**
	 * 查询子机构排名TOP10
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<CommonRankDomain> getSubBranSaleCustRankTop10List(Map<String, Object> paramMap);

	/**
	 * 查询预约购买等技术汇总信息
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	SumItemInfoDomain getReserveBuyCustSum(Map<String, Object> paramMap);

	/**
	 * 查询评价汇总信息
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	SumItemInfoDomain getEvaluteSumInfo(Map<String, Object> paramMap);

	/**
	 * 查询处理时间排名TOP10
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<CommonRankDomain> getDealTimeRannkTop10List(Map<String, Object> paramMap);

	/**
	 * 查询业务效率汇总信息
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	SumItemInfoDomain getBusiEffictSumInfo(Map<String, Object> paramMap);

	/**
	 * 查询客流平均值信息
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	SumItemInfoDomain getAvgCustFlow(Map<String, Object> paramMap);

	/**
	 * 查询4级机构最大值（不包含预约购买登记数）
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	Map<String, Object> getMaxFromLevel4(Map<String, Object> paramMap);

	/**
	 * 查询4级机构预约购买登记数最大值
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	Map<String, Object> getMaxFromReserve4(Map<String, Object> paramMap);

	/**
	 * 查询3级机构最大值（不包含预约购买登记数）
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	Map<String, Object> getMaxFromLevel3(Map<String, Object> paramMap);

	/**
	 * 查询3级机构预约购买登记数最大值
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	Map<String, Object> getMaxFromReserve3(Map<String, Object> paramMap);

	/**
	 * 查询总行分行最大值（不包含预约购买登记数）
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	Map<String, Object> getMaxFromLevel(Map<String, Object> paramMap);

	/**
	 * 查询总行分行预约购买登记数最大值
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	Map<String, Object> getMaxFromReserve(Map<String, Object> paramMap);

	/**
	 * 查询机构登记
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	Map<String, Object> getOrgLevel(Map<String, Object> paramMap);

}
