package com.dcfs.smartaibank.manager.operations.web.dao;

import com.dcfs.smartaibank.manager.operations.web.domain.BusinessDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.HallMarketDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonRankDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonDistributionDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 厅堂营销DAO
 *
 * @author
 */
@Mapper
public interface HallMarketDao {
	/**
	 * 获取营销客户数汇总信息、营销客户数、全行平均数、环比增加
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	HallMarketDomain getHallMarketSum(Map<String, Object> paramMap);

	/**
	 * 获取营销客户数汇总信息、营销客户数、全行平均数、环比增加
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	HallMarketDomain getSaleCustHallMarketSum(Map<String, Object> paramMap);

	/**
	 * 获取潜在客户数汇总信息、营销客户数、全行平均数、环比增加
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	HallMarketDomain getPotentialCustRegisterSum(Map<String, Object> paramMap);

	/**
	 * 获取营销产品数汇总信息、营销客户数、全行平均数、环比增加
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	HallMarketDomain getSaleProductCountSum(Map<String, Object> paramMap);

	/**
	 * 获取预约客户数汇总信息、营销客户数、全行平均数、环比增加
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	HallMarketDomain getReserveBuyCustSum(Map<String, Object> paramMap);

	/**
	 * 营销客户数随着时间的变化列表
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<CommonDistributionDomain> getTimeGroupSaleCustList(Map<String, Object> paramMap);

	/**
	 * 潜在客户数随着时间的变化列表
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<CommonDistributionDomain> getTimeGroupPotentialCustList(Map<String, Object> paramMap);

	/**
	 * 营销产品数随着时间的变化列表
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<CommonDistributionDomain> getTimeGroupSaleProductCountList(Map<String, Object> paramMap);

	/**
	 * 预约购买数随着时间的变化列表
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<CommonDistributionDomain> getTimeGroupReserveCustCountList(Map<String, Object> paramMap);

	/**
	 * 获取客户接待类型列表和业务办理类型列表
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	BusinessDomain getCustReceptionPartList(Map<String, Object> paramMap);

	/**
	 * 获取子机构业务办理量的top10
	 *
	 * @param paramMap 查询参数
	 * @return
	 */

	List<CommonRankDomain> getSubBranSaleCustRankTop10List(Map<String, Object> paramMap);

	/**
	 * 获取子机构潜在客户登记数的top10
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<CommonRankDomain> getSubBranPotentialCustRankTop10List(Map<String, Object> paramMap);

	/**
	 * 获取子机构营销产品数的top10
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<CommonRankDomain> getSubBranSaleProductRankTop10List(Map<String, Object> paramMap);

	/**
	 * 获取子机构预约购买产品数的top10
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<CommonRankDomain> getSubBranReserveBuyRankTop10List(Map<String, Object> paramMap);

	/**
	 * 获取指定员工的业务办理类型列表
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	BusinessDomain getUserBusiAccetpInfo(Map<String, Object> paramMap);

	/**
	 * 获取员工营销的信息，包括营销客户，营销产品，预约购买客户数，潜在客户等级数
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<HallMarketDomain> getUserSaleInfoList(Map<String, Object> paramMap);

	/**
	 * 查询机构的平均值信息
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	Map<String, Object> getOrgAvgInfo(Map<String, Object> paramMap);

	/**
	 * 查询机构用户的平均值信息
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	Map<String, Object> getOrgUserAvgInfo(Map<String, Object> paramMap);

	/**
	 * 获取指定机构下某一营销业务类型的前三名员工
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<HallMarketDomain> getUserMarketRankTop(Map<String, Object> paramMap);
}
