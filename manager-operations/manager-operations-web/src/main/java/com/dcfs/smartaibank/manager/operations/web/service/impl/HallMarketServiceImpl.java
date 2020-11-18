package com.dcfs.smartaibank.manager.operations.web.service.impl;

import com.dcfs.smartaibank.manager.operations.web.constant.Constant;
import com.dcfs.smartaibank.manager.operations.web.dao.HallMarketDao;
import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.manager.operations.web.domain.BusinessDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonDistributionDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.HallMarketDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.HallMarketSumDomain;
import com.dcfs.smartaibank.manager.operations.web.service.HallMarketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 厅堂营销服务
 *
 * @author
 */
@Service
@Slf4j
public class HallMarketServiceImpl implements HallMarketService {
	@Autowired
	private HallMarketDao hallMarketDao;

	/**
	 * 修改 BY zhangypm 2019/5/17
	 */
	public HallMarketSumDomain getHallMarketSuminfo(Map<String, Object> paramMap) {
		HallMarketSumDomain result = new HallMarketSumDomain();
		try {
			//营销客户数汇总信息
			//营销客户数的时间变化统计列表
			result.setSaleCustCount(queryData(hallMarketDao.getTimeGroupSaleCustList(paramMap),
				"identify", paramMap));
			//潜在客户汇总信息
			//潜在客户登记的时间统计列表
			result.setPotentialCustRegister(queryData(hallMarketDao.getTimeGroupPotentialCustList(paramMap),
				"protential", paramMap));
			//营销产品数汇总信息
			//营销产品数随时间变化的列表
			result.setSaleProductCount(queryData(hallMarketDao.getTimeGroupSaleProductCountList(paramMap),
				"product", paramMap));
			//预约购买客户汇总信息
			//预约购买客户时间变化列表
			result.setReserveBuyCust(queryData(hallMarketDao.getTimeGroupReserveCustCountList(paramMap),
				"reserve", paramMap));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException("data.access");
		}
		return result;
	}

	/**
	 * 厅堂营销汇总归正
	 * 新增 BY zhangypm 2019/5/17
	 */
	private HallMarketDomain queryData(List<CommonDistributionDomain> hallMarketList,
									   String saleType,
									   Map<String, Object> paramMap) {
		HallMarketDomain hallMarket = new HallMarketDomain();
		// 因需计算无数据日期环比问题，日，月，季，年多查一周期数据，现删除
		hallMarketList.remove(0);
		//查询当前日期无数据时全行平均并修改
		for (int i = 0; i < hallMarketList.size(); i++) {
			CommonDistributionDomain domain = hallMarketList.get(i);
			// 当当前日期无全行平均值时，查询它上级基础数据
			if (domain.getTotalAvg() == null) {
				Map<String, Object> sumMap = new HashMap<String, Object>(4);
				sumMap.put("dateType", paramMap.get("dateType"));
				sumMap.put("timeValue", domain.getTime());
				sumMap.put("orgid", paramMap.get("orgid"));
				sumMap.put("saleType", saleType);
				HallMarketDomain avg = hallMarketDao.getHallMarketSum(sumMap);
				if (avg != null) {
					domain.setTotalAvg(avg.getBranchAvg());
				} else {
					domain.setTotalAvg(0);
				}
				hallMarketList.set(i, domain);
			}
		}
		//保持当前日期厅堂营销数据与厅堂营销变化一致
		CommonDistributionDomain hallMarketForThisDate = hallMarketList.get(hallMarketList.size() - 1);
		hallMarket.setCount(Long.valueOf(String.valueOf(hallMarketForThisDate.getValue())));
		if (ObjectUtils.isEmpty(hallMarketForThisDate.getTotalAvg())) {
			hallMarket.setAvgValue(Float.valueOf(0f));
		} else {
			hallMarket.setAvgValue(Float.parseFloat(String.valueOf(hallMarketForThisDate.getTotalAvg())));
		}
		if (hallMarketForThisDate.getRate() != null) {
			hallMarket.setRate(hallMarketForThisDate.getRate());
		}
		hallMarket.setTimeGroupList(hallMarketList);
		return hallMarket;
	}

	/**
	 * @return
	 */
	public BusinessDomain getHallMarketBusinDetail(Map<String, Object> paramMap) {
		BusinessDomain result = new BusinessDomain();
		try {
			result = hallMarketDao.getCustReceptionPartList(paramMap);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException("data.access");
		}
		return result;
	}

	/**
	 * @return
	 */
	public HallMarketSumDomain getSubBranchHallMarketRank(Map<String, Object> paramMap) {
		HallMarketSumDomain result = new HallMarketSumDomain();
		try {
			//营销客户数汇总信息
			HallMarketDomain saleCust = hallMarketDao.getSaleCustHallMarketSum(paramMap);
			if (saleCust == null) {
				saleCust = new HallMarketDomain();
			}
			//营销客户数子机构排名
			paramMap.put("rownum", 11);
			paramMap.put("sortType", "DESC");
			saleCust.setRankList(hallMarketDao.getSubBranSaleCustRankTop10List(paramMap));
			result.setSaleCustCount(saleCust);

			//潜在客户汇总信息
			HallMarketDomain potential = hallMarketDao.getPotentialCustRegisterSum(paramMap);
			if (potential == null) {
				potential = new HallMarketDomain();
			}
			//潜在客户登记数子机构排名
			potential.setRankList(hallMarketDao.getSubBranPotentialCustRankTop10List(paramMap));
			result.setPotentialCustRegister(potential);

			//营销产品数汇总信息
			HallMarketDomain product = hallMarketDao.getSaleProductCountSum(paramMap);
			if (product == null) {
				product = new HallMarketDomain();
			}
			//营销产品数子机构排名
			product.setRankList(hallMarketDao.getSubBranSaleProductRankTop10List(paramMap));
			result.setSaleProductCount(product);

			//预约购买客户汇总信息
			HallMarketDomain reserveBuyCust = hallMarketDao.getReserveBuyCustSum(paramMap);
			if (reserveBuyCust == null) {
				reserveBuyCust = new HallMarketDomain();
			}
			//预约购买客户子机构排名
			reserveBuyCust.setRankList(hallMarketDao.getSubBranReserveBuyRankTop10List(paramMap));
			result.setReserveBuyCust(reserveBuyCust);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException("data.access");
		}
		return result;
	}

	/**
	 * 员工的营销信息与本机构均值 全行均值来源不是同一个表
	 *
	 * @return
	 */
	public HallMarketDomain getUserHallMarketRank(Map<String, Object> paramMap) {
		HallMarketDomain result = new HallMarketDomain();
		try {
			//获取全行平均、本行平均
			Map<String, Object> orgAvgMap = hallMarketDao.getOrgUserAvgInfo(paramMap);
			if (orgAvgMap == null) {
				orgAvgMap = new HashMap<>(16);
			}
			//存放全行平均、本机构均值信息
			buildUserDomain(orgAvgMap, paramMap, result);
			//获取指定营销类型的用户业务排名
			List<HallMarketDomain> brokerRank = hallMarketDao.getUserMarketRankTop(paramMap);
			result.setBrokerRank(brokerRank);
			List<HallMarketDomain> top3 = new ArrayList<>();
			result.setTop3(top3);
			for (HallMarketDomain item : brokerRank) {
				HallMarketDomain top1 = new HallMarketDomain();
				top1.setTellerId(item.getTellerId());
				top1.setTellerName(item.getTellerName());
				top1.setCount(item.getCount());
				top1.setRate(result.getRate());
				top1.setBranchAvg(result.getBranchAvg());
				top1.setAvgValue(result.getAvgValue());

				//根据员工的userid查询员工营销信息的变化规律
				paramMap.put("tellerid", item.getTellerId());
				List<HallMarketDomain> userMarketList = hallMarketDao.getUserSaleInfoList(paramMap);
				//因需计算无数据日期环比问题，日，月，季，年多查一周期数据，现删除
				userMarketList.remove(0);
				top1.setList(userMarketList);
				top3.add(top1);
			}
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException("data.access");
		}

	}

	private void buildUserDomain(Map<String, Object> orgAvgMap, Map<String, Object> paramMap, HallMarketDomain result) {
		BigDecimal branchValue = null;
		BigDecimal avgValue = null;
		BigDecimal ringValue = null;
		String saleType = (String) paramMap.get("saleType");
		if (Constant.IDENTIFY.equals(saleType)) {
			branchValue = (BigDecimal) orgAvgMap.get("MKT_C_COUNT_B_AVG");
			avgValue = (BigDecimal) orgAvgMap.get("MKT_C_COUNT_T_AVG");
			ringValue = (BigDecimal) orgAvgMap.get("MKT_C_COUNT_RING");
		} else if (Constant.PRODUCT.equals(saleType)) {
			branchValue = (BigDecimal) orgAvgMap.get("MKT_P_COUNT_B_AVG");
			avgValue = (BigDecimal) orgAvgMap.get("MKT_P_COUNT_T_AVG");
			ringValue = (BigDecimal) orgAvgMap.get("MKT_P_COUNT_RING");
		} else if (Constant.PROTENTIAL.equals(saleType)) {
			branchValue = (BigDecimal) orgAvgMap.get("POTENTIAL_REG_COUNT_B_AVG");
			avgValue = (BigDecimal) orgAvgMap.get("POTENTIAL_REG_COUNT_T_AVG");
			ringValue = (BigDecimal) orgAvgMap.get("POTENTIAL_REG_COUNT_RING");
		} else if (Constant.RESERVE.equals(saleType)) {
			branchValue = (BigDecimal) orgAvgMap.get("RESERVE_REG_COUNT_B_AVG");
			avgValue = (BigDecimal) orgAvgMap.get("RESERVE_REG_COUNT_T_AVG");
			ringValue = (BigDecimal) orgAvgMap.get("RESERVE_REG_COUNT_RING");
		}
		if (branchValue != null) {
			result.setBranchAvg(branchValue.floatValue());
		}
		if (avgValue != null) {
			result.setAvgValue(avgValue.floatValue());
		}
		if (ringValue != null) {
			result.setRate(ringValue.floatValue());
		}
	}

	/**
	 * 修改 By zhangypm 2019/5/17
	 *
	 * @return
	 */
	public HallMarketSumDomain getHallMarketBusinUserDetail(Map<String, Object> paramMap) {
		HallMarketSumDomain result = new HallMarketSumDomain();
		try {
			//查询指定机构指定柜员的业务受理、客户接待类型
			BusinessDomain busineAcceptList = hallMarketDao.getUserBusiAccetpInfo(paramMap);
			result.setBusineAcceptList(busineAcceptList);
			//查询指定柜员营销客户数
			paramMap.put("saleType", "identify");
			result.setSaleCustCount(queryDataUser(hallMarketDao.getUserSaleInfoList(paramMap), paramMap));
			//查询指定柜员潜在客户登记数
			paramMap.put("saleType", "protential");
			result.setPotentialCustRegister(queryDataUser(hallMarketDao.getUserSaleInfoList(paramMap), paramMap));
			//查询指定柜员营销产品数
			paramMap.put("saleType", "product");
			result.setSaleProductCount(queryDataUser(hallMarketDao.getUserSaleInfoList(paramMap), paramMap));
			//查询指定柜员预约购买客户数
			paramMap.put("saleType", "reserve");
			result.setReserveBuyCust(queryDataUser(hallMarketDao.getUserSaleInfoList(paramMap), paramMap));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException("data.access");
		}
		return result;
	}

	/**
	 * 大堂经理厅堂营销汇总归正
	 * 新增 BY zhangypm 2019/5/17
	 */
	private HallMarketDomain queryDataUser(List<HallMarketDomain> hallMarketList, Map<String, Object> paramMap) {
		HallMarketDomain hallMarket = new HallMarketDomain();
		// 因需计算无数据日期环比问题，日，月，季，年多查一周期数据，现删除
		hallMarketList.remove(0);
		//查询当前日期无数据时全行平均并修改
		for (int i = 0; i < hallMarketList.size(); i++) {
			HallMarketDomain domain = hallMarketList.get(i);
			// 当当前日期无全行平均值时，查询它上级基础数据
			if (domain.getAvgValue() == null) {
				Map<String, Object> sumMap = new HashMap<String, Object>(4);
				sumMap.put("dateType", paramMap.get("dateType"));
				sumMap.put("timeValue", domain.getTime());
				sumMap.put("orgid", paramMap.get("orgid"));
				Map<String, Object> orgAvgMap = hallMarketDao.getOrgUserAvgInfo(sumMap);
				if (orgAvgMap != null) {
					String saleType = (String) paramMap.get("saleType");
					BigDecimal avg = null;
					if ("identify".equals(saleType)) {
						avg = orgAvgMap.get("MKT_C_COUNT_B_AVG") != null
							?
							(BigDecimal) orgAvgMap.get("MKT_C_COUNT_B_AVG") : new BigDecimal("0");
					} else if ("product".equals(saleType)) {
						avg = orgAvgMap.get("MKT_P_COUNT_B_AVG") != null
							?
							(BigDecimal) orgAvgMap.get("MKT_P_COUNT_B_AVG") : new BigDecimal("0");
					} else if ("protential".equals(saleType)) {
						avg = orgAvgMap.get("POTENTIAL_REG_COUNT_B_AVG") != null
							?
							(BigDecimal) orgAvgMap.get("POTENTIAL_REG_COUNT_B_AVG") : new BigDecimal("0");
					} else if ("reserve".equals(saleType)) {
						avg = orgAvgMap.get("RESERVE_REG_COUNT_B_AVG") != null
							?
							(BigDecimal) orgAvgMap.get("RESERVE_REG_COUNT_B_AVG") : new BigDecimal("0");
					}
					domain.setAvgValue(avg.floatValue());
				} else {
					domain.setAvgValue(Float.valueOf("0"));
				}
				hallMarketList.set(i, domain);
			}
		}
		//保持当前日期大堂经理厅堂营销数据与大堂经理厅堂营销变化一致
		HallMarketDomain hallMarketForThisDate = hallMarketList.get(hallMarketList.size() - 1);
		hallMarket.setCount(Long.valueOf(String.valueOf(hallMarketForThisDate.getCount())));
		hallMarket.setAvgValue(Float.parseFloat(String.valueOf(hallMarketForThisDate.getAvgValue())));
		hallMarket.setBranchAvg(Float.parseFloat(String.valueOf(hallMarketForThisDate.getBranchAvg())));
		if (hallMarketForThisDate.getRate() != null) {
			hallMarket.setRate(hallMarketForThisDate.getRate());
		}
		hallMarket.setList(hallMarketList);
		return hallMarket;
	}
}
