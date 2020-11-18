package com.dcfs.smartaibank.manager.operations.web.service.impl;

import com.dcfs.smartaibank.manager.operations.web.dao.ServiceQualityDao;
import com.dcfs.smartaibank.manager.operations.web.service.ServiceQualityService;
import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonDistriAndCtDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonDistributionDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.QualityServiceDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.QualityServiceSumDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务质量服务
 *
 * @author
 */
@Service
@Slf4j
public class ServiceQualityServiceImpl implements ServiceQualityService {

	@Autowired
	private ServiceQualityDao serviceQualityDao;

	@Override
	public QualityServiceDomain getServiceQuality(String orgId,
												  String dateType,
												  String timeValue,
												  String qualityType,
												  Integer branchStatsType) {
		QualityServiceDomain resultQs = new QualityServiceDomain();
		try {
			resultQs.setSubBranchRankTop10List(
				serviceQualityDao.getSubBranchRankTop10List(orgId, dateType, timeValue, qualityType, branchStatsType));
			resultQs.setAvgRateSum(queryData(orgId, dateType, timeValue, qualityType, branchStatsType));
			resultQs.setQualityType(qualityType);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException("data.access");
		}
		return resultQs;
	}

	@Override
	public QualityServiceSumDomain getServiceQualitySum(String orgId,
														String dateType,
														String timeValue,
														Integer branchStatsType) {
		QualityServiceSumDomain rs = new QualityServiceSumDomain();
		try {
			String qualityType = "evaluteRate";
			rs.setEvaluteRate(queryData(orgId, dateType, timeValue, qualityType, branchStatsType));
			qualityType = "praiseRate";
			rs.setPraiseRate(queryData(orgId, dateType, timeValue, qualityType, branchStatsType));
			qualityType = "navigateRate";
			rs.setNavigateRate(queryData(orgId, dateType, timeValue, qualityType, branchStatsType));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException("data.access");
		}
		return rs;
	}

	private CommonDistriAndCtDomain queryData(String orgId,
											  String dateType,
											  String timeValue,
											  String qualityType,
											  Integer branchStatsType) {
		CommonDistriAndCtDomain rate = new CommonDistriAndCtDomain();
		List<CommonDistributionDomain> rateList = serviceQualityDao
			.getTimeGroupServiceQualityList(orgId, dateType, timeValue, qualityType, branchStatsType);
		// 因需计算无数据日期环比问题，日，月，季，年多查一周期数据，现删除
		rateList.remove(0);
		//查询当前日期无数据时全行平均并修改
		for (int i = 0; i < rateList.size(); i++) {
			CommonDistributionDomain domain = rateList.get(i);
			// 当当前日期无全行平均值时，查询它上级基础数据
			if (domain.getTotalAvg() == null) {
				CommonDistriAndCtDomain avg = serviceQualityDao
					.getServiceQualityType(orgId, dateType, domain.getTime(), qualityType, branchStatsType);
				if (avg != null) {
					domain.setTotalAvg(avg.getTotalAvg());
				} else {
					domain.setTotalAvg(0);
				}
				rateList.set(i, domain);
			}
		}
		//保持当前日期服务质量数据与服务质量变化一致
		CommonDistributionDomain rateForThisDate = rateList.get(rateList.size() - 1);
		rate.setValue(rateForThisDate.getValue());
		rate.setTotalAvg(rateForThisDate.getTotalAvg());
		if (rateForThisDate.getRate() != null) {
			rate.setRate(rateForThisDate.getRate());
		}
		rate.setDistributionList(rateList);
		return rate;
	}

}
