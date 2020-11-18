package com.dcfs.smartaibank.manager.operations.web.service;

import com.dcfs.smartaibank.manager.operations.web.domain.QualityServiceDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.QualityServiceSumDomain;

/**
 * 服务质量服务接口
 *
 * @author
 */
public interface ServiceQualityService {

	/**
	 * 查询服务质量返回数据
	 *
	 * @param orgId           机构编号
	 * @param dateType        时间类型
	 * @param timeValue       时间值
	 * @param qualityType     查询类型
	 * @param branchStatsType 机构类型
	 * @return
	 */
	QualityServiceDomain getServiceQuality(String orgId,
										   String dateType,
										   String timeValue,
										   String qualityType,
										   Integer branchStatsType);

	/**
	 * 查询服务质量信息
	 *
	 * @param orgId           机构编号
	 * @param dateType        时间类型
	 * @param timeValue       时间值
	 * @param branchStatsType 机构类型
	 * @return
	 */
	QualityServiceSumDomain getServiceQualitySum(String orgId,
												 String dateType,
												 String timeValue,
												 Integer branchStatsType);

}
