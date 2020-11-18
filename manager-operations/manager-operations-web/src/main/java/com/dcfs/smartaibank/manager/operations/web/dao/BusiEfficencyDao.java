package com.dcfs.smartaibank.manager.operations.web.dao;

import com.dcfs.smartaibank.manager.operations.web.domain.BusiEffectDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.HighFrequencyTradeDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonRankDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonDistributionDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 业务效率DAO
 *
 * @author
 */
@Mapper
public interface BusiEfficencyDao {

	/**
	 * 获取全行的业务总量平均值、增长率、平均处理时间、处理时间增长率
	 *
	 * @param paramMap 查询参数
	 * @return 指定机构的平均值
	 */
	BusiEffectDomain getAvgBusinessSum(Map<String, Object> paramMap);

	/**
	 * 获取上级机构的业务总量平均值、增长率、平均处理时间、处理时间增长率
	 *
	 * @param paramMap 查询参数
	 * @return 指定机构的平均值
	 */
	BusiEffectDomain getAvgBusinessUnitNo(Map<String, Object> paramMap);

	/**
	 * 按照时间分组的指定机构的业务总量
	 *
	 * @param paramMap 查询参数
	 * @return 按照时间分组的指定机构的业务总量
	 */
	List<CommonDistributionDomain> getTimeGroupBusiSumList(Map<String, Object> paramMap);

	/**
	 * 获取交易处理时间随时间的变化趋势
	 *
	 * @param paramMap 查询参数
	 * @return 获取交易处理时间随时间的变化趋势
	 */
	List<CommonDistributionDomain> getTimeGroupDealTime(Map<String, Object> paramMap);

	/**
	 * 获取指定机构子机构业务汇总top10
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<CommonRankDomain> getBusiSumRankTop10List(Map<String, Object> paramMap);

	/**
	 * 获取指定机构子机构交易平均处理时间最快top10
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<CommonRankDomain> getDealTimeRannkTop10List(Map<String, Object> paramMap);

	/**
	 * 获取指定机构高频交易的top20
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<HighFrequencyTradeDomain> getHighFrequencyTradeTop20List(Map<String, Object> paramMap);

	/**
	 * 获取指定机构指定交易的处理时间趋势图
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<HighFrequencyTradeDomain> getTendList(Map<String, Object> paramMap);

	/**
	 * 获取全行的汇总后的高频交易或者耗时交易的总笔数以及平均处理时间
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	HighFrequencyTradeDomain getTradeSumInfo(Map<String, Object> paramMap);


	/**
	 * 获取某只交易的某个时间的最快处理时间
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	List<Map<String, Object>> getMostQuickTime(Map<String, Object> paramMap);

}
