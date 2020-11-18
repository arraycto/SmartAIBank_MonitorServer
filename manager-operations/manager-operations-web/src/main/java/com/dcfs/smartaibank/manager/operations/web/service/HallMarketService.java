package com.dcfs.smartaibank.manager.operations.web.service;

import com.dcfs.smartaibank.manager.operations.web.domain.BusinessDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.HallMarketDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.HallMarketSumDomain;

import java.util.Map;

/**
 * 厅堂营销服务接口
 *
 * @author
 */
public interface HallMarketService {

	/**
	 * 查询厅堂营销汇总信息
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	HallMarketSumDomain getHallMarketSuminfo(Map<String, Object> paramMap);

	/**
	 * 查询厅堂营销业务受理类型
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	BusinessDomain getHallMarketBusinDetail(Map<String, Object> paramMap);

	/**
	 * 查询子机构厅堂营销汇总信息
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	HallMarketSumDomain getSubBranchHallMarketRank(Map<String, Object> paramMap);

	/**
	 * 查询员工的营销信息与本机构均值 全行均值来源不是同一个表
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	HallMarketDomain getUserHallMarketRank(Map<String, Object> paramMap);

	/**
	 * 查询员工的营销详情信息
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	HallMarketSumDomain getHallMarketBusinUserDetail(Map<String, Object> paramMap);
}
