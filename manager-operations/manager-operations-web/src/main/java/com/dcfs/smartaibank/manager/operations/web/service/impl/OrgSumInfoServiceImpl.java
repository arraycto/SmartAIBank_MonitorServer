package com.dcfs.smartaibank.manager.operations.web.service.impl;

import com.dcfs.smartaibank.manager.operations.web.constant.Constant;
import com.dcfs.smartaibank.manager.operations.web.dao.OrgSumInfoDao;
import com.dcfs.smartaibank.manager.operations.web.service.OrgSumInfoService;
import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonRankDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.SumInfoDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.SumItemInfoDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构信息汇总service
 *
 * @Author qiuch　wangjzm
 * @Since 1.0.0
 */
@Service
@Slf4j
public class OrgSumInfoServiceImpl implements OrgSumInfoService {
	@Autowired
	private OrgSumInfoDao orgSumInfoDao;

	@Override
	public SumInfoDomain getOrgSumInfo(Map<String, Object> paramMap) {
		SumInfoDomain result = new SumInfoDomain();
		try {
			//查询指定机构的营销、接待客户、排队等信息
			Map<String, Object> resultMap = orgSumInfoDao.getOrgSumInfo(paramMap);
			//根据机构级别查询最大值处理
			Map<String, Object> maxResult = new HashMap<String, Object>(16);
			Map<String, Object> maxResultFromReserve = new HashMap<String, Object>(16);
			Map<String, Object> orgLevel = new HashMap<String, Object>(16);
			//查询机构级别
			orgLevel = orgSumInfoDao.getOrgLevel(paramMap);
			if (orgLevel != null) {
				Object unitLevel = orgLevel.get("UNITLEVEL");
				Object branchStatsType = paramMap.get("branchStatsType");
				if ((Constant.FOUR_STRING.equals(unitLevel))) {
					maxResult = orgSumInfoDao.getMaxFromLevel4(paramMap);
					maxResultFromReserve = orgSumInfoDao.getMaxFromReserve4(paramMap);
				} else if (Constant.THREE_STRING.equals(unitLevel) && branchStatsType.equals(1)) {
					maxResult = orgSumInfoDao.getMaxFromLevel4(paramMap);
					maxResultFromReserve = orgSumInfoDao.getMaxFromReserve4(paramMap);
				} else if (Constant.ONE.equals(unitLevel) || Constant.TWO_STRING.equals(unitLevel)) {
					maxResult = orgSumInfoDao.getMaxFromLevel(paramMap);
					maxResultFromReserve = orgSumInfoDao.getMaxFromReserve(paramMap);
				} else {
					maxResult = orgSumInfoDao.getMaxFromLevel3(paramMap);
					maxResultFromReserve = orgSumInfoDao.getMaxFromReserve3(paramMap);
				}
				if (resultMap != null) {
					SumItemInfoDomain customer = new SumItemInfoDomain();
					customer.setRecieveCount(getLongFroMap("RECEIVE_COUNT", resultMap));
					customer.setAvgValue(getFloatFroMap("RECEIVE_COUNT_T_AVG", resultMap));
					customer.setRate(getFloatFroMap("RECEIVE_COUNT_RING", resultMap));
					if (maxResult != null) {
						customer.setMaxRecieve(getLongFroMap("MAX_RECEIVE", maxResult));
					}
					result.setCustomer(customer);
					SumItemInfoDomain praise = new SumItemInfoDomain();
					praise.setPraiseRate(getFloatFroMap("NEGATIVE_RATE", resultMap));
					praise.setAvgValue(getFloatFroMap("NEGATIVE_RATE_T_AVG", resultMap));
					praise.setRate(getFloatFroMap("NEGATIVE_RATE_RING", resultMap));
					if (maxResult != null) {
						praise.setMaxNegativeRate(getFloatFroMap("MAX_NEGATIVE_RATE", maxResult));
					}
					result.setPraise(praise);
					//这里直接查询预约购买记录数，因为汇总表没有预约购买记录的字段，表结构设计有遗漏
					SumItemInfoDomain reserve = orgSumInfoDao.getReserveBuyCustSum(paramMap);
					if (ObjectUtils.isEmpty(reserve)) {
						reserve = new SumItemInfoDomain();
					}
					//设置预约购买记录数最大值
					if (maxResultFromReserve != null) {
						reserve.setMaxReserveCount(getLongFroMap("MAXRESERVECOUNT", maxResultFromReserve));
					}
					result.setReserve(reserve);
					SumItemInfoDomain avgWait = new SumItemInfoDomain();
					avgWait.setAvgWaitTime(getFloatFroMap("WAIT_TIME_AVG", resultMap));
					avgWait.setAvgValue(getFloatFroMap("WAIT_TIME_T_AVG", resultMap));
					avgWait.setRate(getFloatFroMap("WAIT_TIME_AVG_RING", resultMap));
					if (maxResult != null) {
						avgWait.setMaxAvgWaitTime(getFloatFroMap("MAX_WAIT_TIME_AVG", maxResult));
					}
					result.setAvgWaitTime(avgWait);
					SumItemInfoDomain avgDeal = new SumItemInfoDomain();
					avgDeal.setAvgDealTime(getFloatFroMap("HANDLE_TIME_AVG", resultMap));
					avgDeal.setAvgValue(getFloatFroMap("HANDLE_TIME_T_AVG", resultMap));
					avgDeal.setRate(getFloatFroMap("HANDLE_TIME_RING", resultMap));
					if (maxResult != null) {
						avgDeal.setMaxAvgDealTime(getFloatFroMap("MAX_HANDLE_TIME_AVG", maxResult));
					}
					result.setAvgDealTime(avgDeal);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException("data.access");
		}
		return result;
	}

	private long getLongFroMap(String fieldName, Map<String, Object> map) {
		if (map == null || map.get(fieldName) == null) {
			return 0L;
		}
		BigDecimal value = (BigDecimal) map.get(fieldName);
		return value.longValue();
	}

	private float getFloatFroMap(String fieldName, Map<String, Object> map) {
		if (map == null || map.get(fieldName) == null) {
			return 0;
		}
		BigDecimal value = (BigDecimal) map.get(fieldName);
		return value.floatValue();
	}

	@Override
	public SumItemInfoDomain getCustFlowSumInfo(Map<String, Object> paramMap) {
		SumItemInfoDomain result = null;
		try {
			result = orgSumInfoDao.getAvgCustFlow(paramMap);
			if (result == null) {
				result = new SumItemInfoDomain();
			}
			paramMap.put("rownum", 6);
			paramMap.put("sortType", "DESC");
			List<CommonRankDomain> top5 = orgSumInfoDao.getSubBranchCustFlowRank(paramMap);
			paramMap.put("sortType", "ASC");
			List<CommonRankDomain> bottom5 = orgSumInfoDao.getSubBranchCustFlowRank(paramMap);
			result.setTop5(top5);
			result.setBottom5(bottom5);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException("data.access");
		}
		return result;
	}

	@Override
	public SumItemInfoDomain getQueueSumInfo(Map<String, Object> paramMap) {
		SumItemInfoDomain result = null;
		try {
			result = orgSumInfoDao.getAvgWaiteTime(paramMap);
			if (result == null) {
				result = new SumItemInfoDomain();
			}
			paramMap.put("rownum", 6);
			paramMap.put("sortType", "DESC");
			List<CommonRankDomain> top5 = orgSumInfoDao.getAvgWaiteTimeTop5(paramMap);
			paramMap.put("sortType", "ASC");
			List<CommonRankDomain> bottom5 = orgSumInfoDao.getAvgWaiteTimeTop5(paramMap);
			result.setTop5(top5);
			result.setBottom5(bottom5);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException("data.access");
		}
		return result;
	}

	@Override
	public SumItemInfoDomain getHallMarketSumInfo(Map<String, Object> paramMap) {
		SumItemInfoDomain result = null;
		try {
			result = orgSumInfoDao.getReserveBuyCustSum(paramMap);
			if (result == null) {
				result = new SumItemInfoDomain();
			}
			paramMap.put("rownum", 6);
			paramMap.put("sortType", "DESC");
			List<CommonRankDomain> top5 = orgSumInfoDao.getSubBranSaleCustRankTop10List(paramMap);
			paramMap.put("sortType", "ASC");
			List<CommonRankDomain> buttom5 = orgSumInfoDao.getSubBranSaleCustRankTop10List(paramMap);
			result.setTop5(top5);
			result.setBottom5(buttom5);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException("data.access");
		}
		return result;
	}

	@Override
	public SumItemInfoDomain getServiceQualitySumInfo(Map<String, Object> paramMap) {
		SumItemInfoDomain result = null;
		try {
			result = orgSumInfoDao.getEvaluteSumInfo(paramMap);
			if (result == null) {
				result = new SumItemInfoDomain();
			}
			paramMap.put("rownum", 6);
			paramMap.put("sortType", "DESC");
			List<CommonRankDomain> top5 = orgSumInfoDao.getServiceQualityTop5(paramMap);
			paramMap.put("sortType", "ASC");
			List<CommonRankDomain> buttom5 = orgSumInfoDao.getServiceQualityTop5(paramMap);
			result.setTop5(top5);
			result.setBottom5(buttom5);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException("data.access");
		}
		return result;
	}

	@Override
	public SumItemInfoDomain getBusiEffictSumInfo(Map<String, Object> paramMap) {
		SumItemInfoDomain result = null;
		try {
			result = orgSumInfoDao.getBusiEffictSumInfo(paramMap);
			if (result == null) {
				result = new SumItemInfoDomain();
			}
			paramMap.put("rownum", 6);
			paramMap.put("sortType", "ASC");
			List<CommonRankDomain> top5 = orgSumInfoDao.getDealTimeRannkTop10List(paramMap);
			paramMap.put("sortType", "DESC");
			List<CommonRankDomain> buttom5 = orgSumInfoDao.getDealTimeRannkTop10List(paramMap);
			result.setTop5(top5);
			result.setBottom5(buttom5);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException("data.access");
		}
		return result;
	}

}
