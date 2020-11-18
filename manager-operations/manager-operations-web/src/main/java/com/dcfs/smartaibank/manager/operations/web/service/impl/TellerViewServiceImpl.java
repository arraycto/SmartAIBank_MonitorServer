package com.dcfs.smartaibank.manager.operations.web.service.impl;

import com.dcfs.smartaibank.manager.operations.web.domain.CommonRank1Domain;
import com.dcfs.smartaibank.manager.operations.web.domain.TellerViewDistriAndCtDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.TellerViewPersonalDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.TellerViewRankAndDetailDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.TellerViewTranRankDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.TranRankDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.TranTop20Domain;
import com.dcfs.smartaibank.manager.operations.web.domain.UserRankDomain;
import com.dcfs.smartaibank.manager.operations.web.utils.ParamValidator;
import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.manager.operations.web.dao.TellerViewDao;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonDistriAndCtDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonDistributionDomain;
import com.dcfs.smartaibank.manager.operations.web.service.TellerViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 柜员视角
 *
 * @Author qiuch
 * @Since 1.0.0
 */
@Service
@Slf4j
public class TellerViewServiceImpl implements TellerViewService {
	@Autowired
	TellerViewDao dao;

	/**
	 * 定义当前统计维度
	 */
	int ctValue = 0;

	@Override
	public TellerViewRankAndDetailDomain getHandleTimeInfo(Map<String, Object> map) {
		map.put("type", "handleTime");
		TellerViewRankAndDetailDomain result = getUserRankList(map);
		return result;
	}

	@Override
	public TellerViewRankAndDetailDomain getTranCountInfo(Map<String, Object> map) {
		map.put("type", "tranCount");
		TellerViewRankAndDetailDomain result = getUserRankList(map);
		return result;
	}

	@Override
	public TranTop20Domain getTranTop20HandleTimeInfo(Map<String, Object> map) {
		TranTop20Domain result = new TranTop20Domain();
		try {
			List<TellerViewTranRankDomain<UserRankDomain>> tranTop20HandleTimeList = dao.getTranTop20List(map);
			if (tranTop20HandleTimeList.size() == 0) {
				log.info("未查询到交易信息");
				return result;
			}
			for (int i = 0; i < tranTop20HandleTimeList.size(); i++) {
				TellerViewTranRankDomain tranDetail = tranTop20HandleTimeList.get(i);
				map.put("tranCode", tranDetail.getTranCode());
				List<UserRankDomain> tranTop20HandleTimeDetail = dao.getTranTop20HandleTimeList(map);
				tranDetail.setRankList(tranTop20HandleTimeDetail);
			}
			result.setTranTop20List(tranTop20HandleTimeList);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException("data.access");
		}
		return result;
	}

	@Override
	public TellerViewRankAndDetailDomain getServiceQualityInfo(Map<String, Object> map) {
		TellerViewRankAndDetailDomain result = getUserRankList(map);
		return result;
	}

	@Override
	public TellerViewPersonalDomain getPersonalDetailInfo(Map<String, Object> map) {
		TellerViewPersonalDomain result = new TellerViewPersonalDomain();
		//平均处理时长
		map.put("type", "handleTime");
		TellerViewDistriAndCtDomain handleTimeDistributionList = getPersonalDistriAndCtInfo(map);
		result.setHandleTimeDistributionList(handleTimeDistributionList);

		//交易处理数量
		map.put("type", "tranCount");
		TellerViewDistriAndCtDomain tranCountDistributionList = getPersonalDistriAndCtInfo(map);
		result.setTranCountDistributionList(tranCountDistributionList);

		//评价率
		map.put("type", "evaluateRate");
		TellerViewDistriAndCtDomain evaluateRateList = getPersonalDistriAndCtInfo(map);
		result.setEvaluateRateList(evaluateRateList);

		//好评率
		map.put("type", "positiveRate");
		TellerViewDistriAndCtDomain positiveRateList = getPersonalDistriAndCtInfo(map);
		result.setPositiveRateList(positiveRateList);

		//差评率
		map.put("type", "negativeRate");
		TellerViewDistriAndCtDomain negativeRateList = getPersonalDistriAndCtInfo(map);
		result.setNegativeRateList(negativeRateList);

		//高频交易
		List<TranRankDomain> tranTop20HandleTimeList = dao.getTranTop20HandleTimePersonal(map);
		result.setRanTop20HandleTimeList(tranTop20HandleTimeList);

		return result;
	}

	/**
	 * @param map
	 * @return
	 */
	public TellerViewRankAndDetailDomain getUserRankList(Map<String, Object> map) {
		TellerViewRankAndDetailDomain result = new TellerViewRankAndDetailDomain();
		CommonRank1Domain<UserRankDomain> userRankInfo = new CommonRank1Domain<>();
		List<CommonDistriAndCtDomain> userDistributionList = new ArrayList<>();

		try {
			//交易处理数量柜员排名
			List<UserRankDomain> userRankList = dao.getUserRankList(map);
			if (userRankList.size() == 0) {
				log.info("未查询到柜员信息");
				result.setUserRankList(userRankInfo);
				result.setUserDetailList(userDistributionList);
				return result;
			}
			userRankInfo.setRankList(userRankList);
			//定义当前值
			ctValue = userRankList.size() - 1;
			userRankInfo.setBranchAvg(userRankList.get(ctValue).getBranchAvg());
			userRankInfo.setTotalAvg(userRankList.get(ctValue).getTotalAvg());
			result.setUserRankList(userRankInfo);

			// 交易处理数量详情
			for (int i = 0; i < userRankList.size(); i++) {
				CommonDistriAndCtDomain tellerDetail = new CommonDistriAndCtDomain();
				String tellerId = userRankList.get(i).getUserId();
				map.put("tellerId", tellerId);
				List<CommonDistributionDomain> tranCountDistributionDetail = dao.getUserDistributionList(map);
				// 因需计算无数据日期环比问题，日，月，季，年多查一周期数据，现删除
				tranCountDistributionDetail.remove(0);
				tellerDetail.setDistributionList(tranCountDistributionDetail);
				tellerDetail.setId(tellerId);
				tellerDetail.setRankNo(userRankList.get(i).getRankNo());
				tellerDetail.setName(userRankList.get(i).getUserName());
				tellerDetail.setTotalAvg(userRankList.get(i).getTotalAvg());
				tellerDetail.setBranchAvg(userRankList.get(i).getBranchAvg());
				tellerDetail.setValue(userRankList.get(i).getValue());
				tellerDetail.setRate(userRankList.get(i).getRate());
				userDistributionList.add(tellerDetail);
			}
			result.setUserDetailList(userDistributionList);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException("data.access");
		}
		return result;
	}

	/**
	 * @param map
	 * @return
	 */
	public TellerViewDistriAndCtDomain getPersonalDistriAndCtInfo(Map<String, Object> map) {
		TellerViewDistriAndCtDomain list = new TellerViewDistriAndCtDomain();

		List<UserRankDomain> userRankList = dao.getUserRankList(map);
		if (userRankList.size() == 0) {
			log.info("未查询到柜员信息");
			return list;
		}
		//定义当前值
		ctValue = userRankList.size() - 1;
		list.setRankNo(userRankList.get(ctValue).getRankNo());
		list.setBranchAvg(userRankList.get(ctValue).getBranchAvg());
		list.setTotalAvg(userRankList.get(ctValue).getTotalAvg());
		list.setValue(userRankList.get(ctValue).getValue());
		list.setRate(userRankList.get(ctValue).getRate());
		//获取排名趋势
		Map<String, Object> preMap = new HashMap<>(16);
		preMap.putAll(map);
		preMap.put("TIME_VALUE", ParamValidator.dateFormat(map.get("TIME_VALUE").toString(),
			map.get("DATE_TYPE").toString(), -1));
		List<UserRankDomain> userRankPreList = dao.getUserRankList(preMap);
		if (!userRankPreList.isEmpty() && userRankPreList.size() > 0) {
			int rankTrend = userRankPreList.get(ctValue).getRankNo() - userRankList.get(ctValue).getRankNo();
			list.setRankTrend(rankTrend);
		}
		List<CommonDistributionDomain> userDetailPersonalList = dao.getUserDistributionList(map);
		// 因需计算无数据日期环比问题，日，月，季，年多查一周期数据，现删除
		userDetailPersonalList.remove(0);
		list.setDistributionList(userDetailPersonalList);
		return list;
	}

}
