package com.dcfs.smartaibank.manager.operations.web.service;

import com.dcfs.smartaibank.manager.operations.web.domain.CustFlowDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CustFlowSumDomain;

import java.util.Map;

/**
 * 客户动线服务接口
 *
 * @author
 */
public interface CustFlowDataService {

	/**
	 * 获取客流多维度的统计信息，包括性别、年龄、资产等不同维度的客流变化信息
	 *
	 * @param paramMap 包括日期类型，日期值、机构编号
	 * @return
	 */

	CustFlowSumDomain getCustFlowDetailInfo(Map<String, Object> paramMap);

	/**
	 * 获取指定机构子机构在指定日期类型时间内对应的客流量排名
	 *
	 * @param paramMap 查询参数，包含日期类型，日期值、机构编号
	 * @return
	 */
	CustFlowDomain getSubBranchCustFlowRank(Map<String, Object> paramMap);

}
