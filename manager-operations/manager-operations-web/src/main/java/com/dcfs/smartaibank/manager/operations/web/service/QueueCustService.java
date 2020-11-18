package com.dcfs.smartaibank.manager.operations.web.service;

import com.dcfs.smartaibank.manager.operations.web.domain.QueueCustBankDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.QueueCustDetailDomain;

/**
 * 客户排队信息查询服务接口
 *
 * @Author qiuch wangjzm
 * @Since 1.0.0
 */
public interface QueueCustService {
	/**
	 * 获取客户排队情况的详情
	 *
	 * @param orgId           机构编号
	 * @param dateType        日期类型
	 * @param timeValue       时间值
	 * @param branchStatsType 机构类型
	 * @return QueueCustDetailDomain
	 * @method getQueueDetailInfo
	 * @description 获取客户排队情况的详情
	 * @date: 2019/2/28 14:13
	 */
	QueueCustDetailDomain getQueueDetailInfo(String orgId, String dateType, String timeValue, Integer branchStatsType);

	/**
	 * 获取各网点排队时间的排名
	 *
	 * @param orgId           机构编号
	 * @param dateType        日期类型
	 * @param timeValue       时间值
	 * @param branchStatsType 机构类型
	 * @return QueueCustBankDomain
	 * @method getQueueRankInfo
	 * @description 获取各网点排队时间的排名
	 * @date: 2019/2/28 14:13
	 */
	QueueCustBankDomain getQueueRankInfo(String orgId, String dateType, String timeValue, Integer branchStatsType);
}
