package com.dcfs.smartaibank.manager.operations.web.service.impl;

import com.dcfs.smartaibank.manager.operations.web.dao.QueueCustDao;
import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonDistriAndCtDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonGroupDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.QueueCustDetailDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.QueueTimeByCustTypeDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonDistributionDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.QueueCustBankDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonRankDomain;
import com.dcfs.smartaibank.manager.operations.web.service.QueueCustService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户排队信息查询实现类
 *
 * @author
 */
@Service
@Slf4j
public class QueueCustServiceImpl implements QueueCustService {
	@Autowired
	private QueueCustDao dao;

	@Override
	public QueueCustDetailDomain getQueueDetailInfo(String orgId, String dateType, String timeValue,
													Integer branchStatsType) {
		QueueCustDetailDomain result = new QueueCustDetailDomain();
		CommonDistriAndCtDomain queueTimeGather = new CommonDistriAndCtDomain();
		List<CommonGroupDomain> queueTimeGroupList = new ArrayList<>();
		List<QueueTimeByCustTypeDomain> queueTimeByCustTypeList = new ArrayList<>();
		List<CommonGroupDomain> queueTimeDomainList = new ArrayList<>();
		//定义当前统计维度
		int ctValue = 0;
		try {
			//排队等待时长汇总
			List<CommonDistributionDomain> queueTimeGatherList =
				dao.getQueueTimeGatherList(orgId, dateType, timeValue, branchStatsType);
			// 因需计算无数据日期环比问题，日，月，季，年多查一周期数据，现删除
			queueTimeGatherList.remove(0);
			//查询当前日期无数据时全行平均并修改
			for (int i = 0; i < queueTimeGatherList.size(); i++) {
				setQueueTimeGatherList(queueTimeGatherList, i, orgId, dateType, branchStatsType);
			}
			if (queueTimeGatherList.size() == 0) {
				log.info("未查询到排队等待时长记录");
				result.setQueueTimeGather(queueTimeGather);
				result.setQueueTimeGroupList(queueTimeGroupList);
				result.setQueueTimeByCustTypeList(queueTimeByCustTypeList);
				result.setQueueTimeDomainList(queueTimeDomainList);
				return result;
			}
			result.setQueueTimeGather(setQueueTimeGatherValues(queueTimeGatherList, queueTimeGather));
			//排队时长统计-区间段
			queueTimeGroupList = dao.getQueueTimeGroupList(orgId, dateType, timeValue, null, branchStatsType);
			result.setQueueTimeGroupList(queueTimeGroupList);
			//排队时长统计-客户类型
			String[] custType = {"P", "V", "C"};
			for (String type : custType) {
				QueueTimeByCustTypeDomain custBean = new QueueTimeByCustTypeDomain();
				List<CommonDistributionDomain> timeDistributionByCustTypeList =
					dao.getTimeDistributionByCustTypeList(orgId, dateType, timeValue, type, branchStatsType);
				//  因需计算无数据日期环比问题，日，月，季，年多查一周期数据，现删除
				timeDistributionByCustTypeList.remove(0);
				//查询当前日期无数据时全行平均并修改
				for (int i = 0; i < timeDistributionByCustTypeList.size(); i++) {
					setCommonDistribution(timeDistributionByCustTypeList, i, orgId, dateType, type, branchStatsType);
				}
				if (timeDistributionByCustTypeList.size() == 0) {
					log.info("未查询到排队等待时长记录");
					return result;
				}
				custBean.setDistributionList(timeDistributionByCustTypeList);
				List<CommonGroupDomain> timeGroupByCustTypeList =
					dao.getQueueTimeGroupList(orgId, dateType, timeValue, type, branchStatsType);
				custBean.setGroupList(timeGroupByCustTypeList);
				//定义当前值
				ctValue = timeDistributionByCustTypeList.size() - 1;
				custBean.setId(timeDistributionByCustTypeList.get(ctValue).getId());
				custBean.setName(timeDistributionByCustTypeList.get(ctValue).getName());
				custBean.setValue(timeDistributionByCustTypeList.get(ctValue).getValue());
				custBean.setTotalAvg(timeDistributionByCustTypeList.get(ctValue).getTotalAvg());
				custBean.setRate(timeDistributionByCustTypeList.get(ctValue).getRate());
				queueTimeByCustTypeList.add(custBean);
			}
			result.setQueueTimeByCustTypeList(queueTimeByCustTypeList);
			//排队时长统计-时间段
			queueTimeDomainList = dao.getQueueTimeDomainList(orgId, dateType, timeValue, branchStatsType);
			result.setQueueTimeDomainList(queueTimeDomainList);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException("data.access");
		}
		return result;
	}

	private void setCommonDistribution(List<CommonDistributionDomain> timeDistributionByCustTypeList, int i,
									   String orgId, String dateType, String type, Integer branchStatsType) {
		CommonDistributionDomain domain = timeDistributionByCustTypeList.get(i);
		// 当当前日期无全行平均值时，查询它上级基础数据
		if (domain.getTotalAvg() == null) {
			CommonDistributionDomain avg =
				dao.getCustTypeAvgUnitNo(orgId, dateType, domain.getTime(), branchStatsType, type);
			if (avg != null) {
				domain.setTotalAvg(avg.getTotalAvg());
			} else {
				domain.setTotalAvg(0);
			}
			timeDistributionByCustTypeList.set(i, domain);
		}
	}

	private void setQueueTimeGatherList(List<CommonDistributionDomain> queueTimeGatherList, int i, String orgId,
										String dateType, Integer branchStatsType) {
		CommonDistributionDomain domain = queueTimeGatherList.get(i);
		// 当当前日期无全行平均值时，查询它上级基础数据
		if (domain.getTotalAvg() == null) {
			CommonDistributionDomain avg = dao.getAvgUnitNo(orgId, dateType, domain.getTime(), branchStatsType);
			if (avg != null) {
				domain.setTotalAvg(avg.getTotalAvg());
			} else {
				domain.setTotalAvg(0);
			}
			queueTimeGatherList.set(i, domain);
		}
	}

	private CommonDistriAndCtDomain setQueueTimeGatherValues(List<CommonDistributionDomain> queueTimeGatherList,
															 CommonDistriAndCtDomain queueTimeGather) {
		queueTimeGather.setDistributionList(queueTimeGatherList);
		//定义当前值
		int ctValue = queueTimeGatherList.size() - 1;
		queueTimeGather.setValue(queueTimeGatherList.get(ctValue).getValue());
		queueTimeGather.setTotalAvg(queueTimeGatherList.get(ctValue).getTotalAvg());
		queueTimeGather.setRate(queueTimeGatherList.get(ctValue).getRate());
		return queueTimeGather;
	}

	@Override
	public QueueCustBankDomain getQueueRankInfo(String orgId,
												String dateType,
												String timeValue,
												Integer branchStatsType) {
		QueueCustBankDomain result = new QueueCustBankDomain();
		try {
			List<CommonRankDomain> queueTimeRankList =
				dao.getQueueTimeRankList(orgId, dateType, timeValue, branchStatsType);
			result.setQueueTimeRankList(queueTimeRankList);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException("data.access");
		}
		return result;
	}
}
