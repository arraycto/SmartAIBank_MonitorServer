package com.dcfs.smartaibank.manager.operations.web.service.impl;

import com.dcfs.smartaibank.manager.operations.web.constant.Constant;
import com.dcfs.smartaibank.manager.operations.web.dao.CustFlowDataDao;
import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonDistributionDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonGroupDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CustFlowDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CustFlowSumDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonRankDomain;
import com.dcfs.smartaibank.manager.operations.web.service.CustFlowDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户动线服务
 *
 * @author
 */
@Service
@Slf4j
public class CustFlowDataServiceImpl implements CustFlowDataService {
	@Autowired
	private CustFlowDataDao custFlowDataDao;

	@Override
	public CustFlowSumDomain getCustFlowDetailInfo(Map<String, Object> paramMap) {
		CustFlowSumDomain result = new CustFlowSumDomain();
		try {
			//全行客流平均流量
			CustFlowDomain custFlowInfo = custFlowDataDao.getAvgCustFlow(paramMap);
			if (custFlowInfo == null) {
				custFlowInfo = new CustFlowDomain();
			}
			//客流随着时间的变化
			List<CommonDistributionDomain> timeGroupCustFlowList = custFlowDataDao.getCustFlowByTimeGroup(paramMap);
			//因需计算无数据日期环比问题，日，月，季，年多查一周期数据，现删除
			timeGroupCustFlowList.remove(0);
			//查询当前日期无数据时全行平均并修改
			for (int i = 0; i < timeGroupCustFlowList.size(); i++) {
				CommonDistributionDomain domain = timeGroupCustFlowList.get(i);
				// 当当前日期无全行平均值时，查询它上级基础数据
				if (domain.getTotalAvg() == null) {
					Map<String, Object> sumMap = new HashMap<String, Object>(4);
					sumMap.put("dateType", paramMap.get("dateType"));
					sumMap.put("timeValue", domain.getTime());
					sumMap.put("orgid", paramMap.get("orgid"));
					sumMap.put("branchStatsType", paramMap.get("branchStatsType"));
					CustFlowDomain avg = custFlowDataDao.getAvgCustFlowUnitNo(sumMap);
					if (avg != null) {
						domain.setTotalAvg(avg.getTotalAvg());
					} else {
						domain.setTotalAvg(0);
					}
					timeGroupCustFlowList.set(i, domain);
				}
			}
			CommonDistributionDomain dataForThisDate = timeGroupCustFlowList.get(timeGroupCustFlowList.size() - 1);
			// 保持当前日期客流数据与客流变化一致
			if (dataForThisDate.getRate() != null) {
				custFlowInfo.setRate(dataForThisDate.getRate());
			}
			custFlowInfo.setReceiveCount(Long.valueOf(String.valueOf(dataForThisDate.getValue())));
			custFlowInfo.setTotalAvg(Long.valueOf(String.valueOf(dataForThisDate.getTotalAvg())));
			custFlowInfo.setTimeGroupCustFlowList(timeGroupCustFlowList);
			result.setCustFlowInfo(custFlowInfo);
			//对公客户客流随时间变化
			CustFlowDomain publicAndPrivate = new CustFlowDomain();
			paramMap.put("custType", "C");
			List<CustFlowDomain> timePartCustFlowForPublicList = custFlowDataDao.getCustFlowForCustType(paramMap);
			Map<String, Object> publicMap = custFlowDataDao.getPublicCount(paramMap);
			//对私客户客流随时间变化
			List<CustFlowDomain> timePartCustFlowForPrivateList =
				custFlowDataDao.getCustFlowForCustTypeForPrivate(paramMap);
			//对公对私客户数据进行合并
			combinePrivateAndPublic(timePartCustFlowForPublicList, timePartCustFlowForPrivateList);
			Map<String, Object> privateMap = custFlowDataDao.getPrivateCount(paramMap);
			caculateRateOfCustType(publicMap, privateMap, publicAndPrivate);
			publicAndPrivate.setTimePartCustFlowForPublicList(timePartCustFlowForPublicList);
			result.setTimePartCustFlow(publicAndPrivate);
			//客户流量在各个性别的百分比
			List<Map<String, Object>> custFlowBySexList = custFlowDataDao.getCustFlowBySex(paramMap);
			result.setCustFlowPartBySexList(buildCustFlow(custFlowBySexList, "SEXSTEP"));
			//客流在各个年龄段的变化
			List<Map<String, Object>> custFlowByAgeList = custFlowDataDao.getCustFlowByAge(paramMap);
			List<CommonGroupDomain> list = buildCustFlow(custFlowByAgeList, "AGEGROUP");
			if (list.size() > 0) {
				list.add(list.get(0));
				list.remove(0);
			}
			result.setCustFlowPartByAgeList(list);
			//客流在各个资产段的百分比
			List<Map<String, Object>> custFlowByAssetsList = custFlowDataDao.getCustFlowByAssets(paramMap);
			result.setCustFlowPartByAssetsList(buildCustFlow(custFlowByAssetsList, "ASSETGROUP"));
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException("data.access");
		}
		return result;
	}

	private List<CommonGroupDomain> buildCustFlow(List<Map<String, Object>> custFlowBySexList, String fieldName) {
		List<CommonGroupDomain> list = new ArrayList<>();
		for (Map<String, Object> map : custFlowBySexList) {
			BigDecimal recieveCount = (BigDecimal) map.get("RECEIVE_COUNT");
			if (recieveCount == null || recieveCount.doubleValue() <= 0) {
				continue;
			}
			CommonGroupDomain domain = new CommonGroupDomain();
			if ("SEXSTEP".equals(fieldName)) {
				domain.setDesc((String) map.get(fieldName));
			}
			if ("AGEGROUP".equals(fieldName)) {
				domain.setDesc((String) map.get(fieldName));
			}
			if ("ASSETGROUP".equals(fieldName)) {
				domain.setDesc((String) map.get(fieldName));
			}
			domain.setValue(recieveCount.doubleValue());
			list.add(domain);
		}
		return list;
	}

	private void combinePrivateAndPublic(List<CustFlowDomain> publicList, List<CustFlowDomain> privateList) {
		if (publicList != null && privateList != null) {
			for (int i = 0; i < publicList.size(); i++) {
				if (privateList.size() > i) {
					publicList.get(i).setReceivePrivateCount(privateList.get(i).getReceiveCount());
					publicList.get(i).setReceivePublicCount(publicList.get(i).getReceiveCount());
					publicList.get(i).setReceiveCount(null);
				}
			}
		}
	}

	private void caculateRateOfCustType(Map<String, Object> publicMap,
										Map<String, Object> privateMap,
										CustFlowDomain custFlowInfo) {
		if (publicMap != null && privateMap != null) {
			BigDecimal publicCust = (BigDecimal) publicMap.get("CUSTSUM");
			BigDecimal privateCust = (BigDecimal) privateMap.get("CUSTSUM");
			if (publicCust != null && privateCust != null) {
				long sum = publicCust.longValue() + privateCust.longValue();
				sum = sum == 0 ? 1 : sum;
				BigDecimal b = new BigDecimal((float) publicCust.longValue() * 100 / sum);
				BigDecimal b2 = new BigDecimal((float) privateCust.longValue() * 100 / sum);
				long publicRate = b.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
				long privateRate = b2.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
				custFlowInfo.setPublicRate(publicRate);
				custFlowInfo.setPrivateRate(privateRate);
			} else {
				custFlowInfo.setPublicRate(0L);
				custFlowInfo.setPrivateRate(0L);
			}
		} else {
			custFlowInfo.setPublicRate(0L);
			custFlowInfo.setPrivateRate(0L);
		}
	}

	/**
	 * @return
	 */
	public float getRateStr(String rateStr) {
		if (rateStr == null || rateStr.isEmpty()) {
			return Float.parseFloat("0.0");
		}
		if (rateStr.indexOf(Constant.FORTY) != -1) {
			if (rateStr.substring(rateStr.indexOf(Constant.FORTY)).length() > Constant.FOUR) {
				rateStr = rateStr.substring(0, rateStr.indexOf(Constant.FORTY) + 5);
			}
		}
		return Float.parseFloat(rateStr);
	}

	@Override
	public CustFlowDomain getSubBranchCustFlowRank(Map<String, Object> paramMap) {
		CustFlowDomain custFlowInfo = null;
		try {
			paramMap.put("rownum", 11);
			paramMap.put("sortType", "DESC");
			List<CommonRankDomain> subBranchCustFlowRankList = custFlowDataDao.getSubBranchCustFlowRank(paramMap);
			custFlowInfo = custFlowDataDao.getAvgCustFlow(paramMap);
			if (custFlowInfo == null) {
				custFlowInfo = new CustFlowDomain();
			}
			custFlowInfo.setList(subBranchCustFlowRankList);
		} catch (Exception e) {
			throw new BusinessException("data.access");
		}
		return custFlowInfo;
	}
}
