package com.dcfs.smartaibank.manager.operations.web.service.impl;

import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.manager.operations.web.constant.Constant;
import com.dcfs.smartaibank.manager.operations.web.dao.BusiEfficencyDao;
import com.dcfs.smartaibank.manager.operations.web.domain.BusiEffectDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.BusiEffectSumDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonDistributionDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonRankDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.HighFrequencyTradeDomain;
import com.dcfs.smartaibank.manager.operations.web.service.BusiEfficencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


/**
 * 业务效率服务
 *
 * @author
 */
@Service
@Slf4j
public class BusiEfficencyServiceImpl implements BusiEfficencyService {
	@Autowired
	private BusiEfficencyDao busiEfficencyDao;

	@Override
	public BusiEffectSumDomain getBusinessDetailInfo(Map<String, Object> paramMap) {
		BusiEffectSumDomain result = new BusiEffectSumDomain();
		try {
			//全行业务总量均值和增加比例、平均处理时间均值以及增加值
			BusiEffectDomain businessSum = busiEfficencyDao.getAvgBusinessSum(paramMap);
			if (businessSum == null) {
				businessSum = new BusiEffectDomain();
			}
			//指定机构按照时间分组的业务总量变化
			List<CommonDistributionDomain> timeGroupBusiSumList = busiEfficencyDao.getTimeGroupBusiSumList(paramMap);
			//因需计算无数据日期环比问题，日，月，季，年多查一周期数据，现删除
			timeGroupBusiSumList.remove(0);
			//查询当前日期无数据时全行平均并修改
			for (int i = 0; i < timeGroupBusiSumList.size(); i++) {
				CommonDistributionDomain domain = timeGroupBusiSumList.get(i);
				// 当当前日期无全行平均值时，查询它上级基础数据
				if (domain.getTotalAvg() == null) {
					Map<String, Object> sumMap = new HashMap<String, Object>(4);
					sumMap.put("dateType", paramMap.get("dateType"));
					sumMap.put("timeValue", domain.getTime());
					sumMap.put("orgid", paramMap.get("orgid"));
					BusiEffectDomain avg = busiEfficencyDao.getAvgBusinessUnitNo(sumMap);
					if (avg != null) {
						domain.setTotalAvg(avg.getTranCounTotalAvg());
					} else {
						domain.setTotalAvg(0);
					}
					timeGroupBusiSumList.set(i, domain);
				}
			}
			//保持当前日期业务总量数据与业务总量变化一致
			CommonDistributionDomain dataForThisDate = timeGroupBusiSumList.get(timeGroupBusiSumList.size() - 1);
			if (dataForThisDate.getRate() != null) {
				businessSum.setTranCountRing(Float.parseFloat(String.valueOf(dataForThisDate.getRate())));
			}
			businessSum.setTranCount(Long.valueOf(String.valueOf(dataForThisDate.getValue())));
			businessSum.setTranCounTotalAvg(Float.parseFloat(String.valueOf(dataForThisDate.getTotalAvg())));

			businessSum.setTimeGroupBusiSumList(timeGroupBusiSumList);
			result.setBusinessSum(businessSum);

			BusiEffectDomain tradeDealAvgTime = busiEfficencyDao.getAvgBusinessSum(paramMap);
			if (tradeDealAvgTime == null) {
				tradeDealAvgTime = new BusiEffectDomain();
			}
			//指定机构交易平均处理时间的趋势图
			List<CommonDistributionDomain> timeGroupDealTime = busiEfficencyDao.getTimeGroupDealTime(paramMap);
			//因需计算无数据日期环比问题，日，月，季，年多查一周期数据，现删除
			timeGroupDealTime.remove(0);
			//查询当前日期无数据时全行平均并修改
			for (int i = 0; i < timeGroupDealTime.size(); i++) {
				CommonDistributionDomain domain = timeGroupDealTime.get(i);
				// 当当前日期无全行平均值时，查询它上级基础数据
				if (domain.getTotalAvg() == null) {
					BusiEffectDomain avg = busiEfficencyDao.getAvgBusinessUnitNo(paramMap);
					if (avg != null) {
						domain.setTotalAvg(avg.getHandleTimeTotalAvg());
					} else {
						domain.setTotalAvg(0);
					}
					timeGroupDealTime.set(i, domain);
				}
			}
			//保持当前日期平均处理时间数据与平均处理时间变化一致
			CommonDistributionDomain dataTimeForThisDate = timeGroupDealTime.get(timeGroupBusiSumList.size() - 1);
			tradeDealAvgTime.setHandleTimeAvg(Float.parseFloat(String.valueOf(dataTimeForThisDate.getValue())));
			if (dataTimeForThisDate.getRate() != null) {
				tradeDealAvgTime.setHandleTimeRing(Float.parseFloat(String.valueOf(dataTimeForThisDate.getRate())));
			}
			tradeDealAvgTime.setHandleTimeTotalAvg(Float.parseFloat(String.valueOf(dataTimeForThisDate.getTotalAvg())));

			tradeDealAvgTime.setTimeGroupDealTimeList(timeGroupDealTime);
			result.setTradeDealAvgTime(tradeDealAvgTime);
		} catch (Exception e) {
			throw new BusinessException("data.access");
		}
		return result;
	}


	@Override
	public BusiEffectSumDomain getSubBranchBusinessRank(Map<String, Object> paramMap) {
		BusiEffectSumDomain result = new BusiEffectSumDomain();
		try {
			BusiEffectDomain businessSum = busiEfficencyDao.getAvgBusinessSum(paramMap);
			if (businessSum == null) {
				businessSum = new BusiEffectDomain();
			}
			//子机构业务总量top10
			paramMap.put("rownum", 11);
			List<CommonRankDomain> busiSumRankTop10List = busiEfficencyDao.getBusiSumRankTop10List(paramMap);
			businessSum.setBusiSumRankTop10List(busiSumRankTop10List);
			result.setBusinessSum(businessSum);

			//子机构平均处理时间top10
			paramMap.put("rownum", 11);
			paramMap.put("sortType", "ASC");
			List<CommonRankDomain> dealTimeRannkTop10List = busiEfficencyDao.getDealTimeRannkTop10List(paramMap);
			businessSum.setDealTimeRankTop10List(dealTimeRannkTop10List);
		} catch (Exception e) {
			throw new BusinessException("data.access");
		}
		return result;
	}

	@Override
	public BusiEffectSumDomain getHignFrequencyTradeRank(Map<String, Object> paramMap) {
		BusiEffectSumDomain result = new BusiEffectSumDomain();
		try {
			//高频交易top20
			if (Constant.ONE.equals(paramMap.get(Constant.TRADE_TYPE))) {
				paramMap.put("sortType", "DESC");
			} else {
				paramMap.put("sortType", "ASC");
			}
			paramMap.put("tranFlag", paramMap.get("tradeType"));
			HighFrequencyTradeDomain tradeSuminfo = busiEfficencyDao.getTradeSumInfo(paramMap);
			if (tradeSuminfo == null) {
				tradeSuminfo = new HighFrequencyTradeDomain();
			}
			Float handleAvg = getHandleTimeAvg(tradeSuminfo);
			tradeSuminfo.setHandleTimeAvg(handleAvg);
			List<HighFrequencyTradeDomain> highFrequencyTradeTop20List =
				busiEfficencyDao.getHighFrequencyTradeTop20List(paramMap);
			tradeSuminfo.setList(highFrequencyTradeTop20List);
			result.setHighFrequencyTrade(tradeSuminfo);
		} catch (Exception e) {
			throw new BusinessException("data.access");
		}
		return result;
	}

	private float getHandleTimeAvg(HighFrequencyTradeDomain tradeSuminfo) {
		float totalTime = tradeSuminfo.getHandleTimeTotal() == null ? 0 : tradeSuminfo.getHandleTimeTotal();
		long trandCount = tradeSuminfo.getTrandCount() == null ? 1 : tradeSuminfo.getTrandCount();
		BigDecimal b = new BigDecimal((float) totalTime / trandCount);
		float result = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		return result;
	}

	@Override
	public HighFrequencyTradeDomain getTradeTrend(Map<String, Object> paramMap) {
		HighFrequencyTradeDomain result = new HighFrequencyTradeDomain();
		try {
			result.setTradeCode((String) paramMap.get("tradeType"));
			List<Map<String, Object>> mostQuickTimeList = busiEfficencyDao.getMostQuickTime(paramMap);
			List<HighFrequencyTradeDomain> tendList = busiEfficencyDao.getTendList(paramMap);
			if (tendList != null && tendList.size() > 0) {
				for (int i = 0; i < tendList.size(); i++) {
					BigDecimal minitime = (BigDecimal) mostQuickTimeList.get(i).get("MINITIME");
					if (minitime != null) {
						tendList.get(i).setMostQuickTime(minitime.floatValue());
					}
				}
			}
			result.setTendList(tendList);
		} catch (Exception e) {
			throw new BusinessException("data.access");
		}
		return result;
	}
}
