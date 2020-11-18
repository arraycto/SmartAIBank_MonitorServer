package com.dcfs.smartaibank.manager.operations.web.service;

import com.dcfs.smartaibank.manager.operations.web.domain.BusiEffectSumDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.HighFrequencyTradeDomain;

import java.util.Map;

/**
 * 服务效率服务接口
 *
 * @author
 */
public interface BusiEfficencyService {

	/**
	 * 查询业务效率汇总信息
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	BusiEffectSumDomain getBusinessDetailInfo(Map<String, Object> paramMap);

	/**
	 * 查询业务效率汇总信息排名信息
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	BusiEffectSumDomain getSubBranchBusinessRank(Map<String, Object> paramMap);

	/**
	 * 查询高频交易排名信息
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	BusiEffectSumDomain getHignFrequencyTradeRank(Map<String, Object> paramMap);

	/**
	 * 查询高频交易信息
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	HighFrequencyTradeDomain getTradeTrend(Map<String, Object> paramMap);

}
