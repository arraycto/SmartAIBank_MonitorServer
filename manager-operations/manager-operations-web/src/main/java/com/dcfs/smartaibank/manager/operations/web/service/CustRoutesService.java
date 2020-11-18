package com.dcfs.smartaibank.manager.operations.web.service;

import com.dcfs.smartaibank.manager.operations.web.domain.CustRoutesDomain;

import java.util.Map;

/**
 * 客户动线服务接口
 *
 * @author
 */
public interface CustRoutesService {

	/**
	 * 查询客户动线信息
	 *
	 * @param paramMap 查询参数
	 * @return
	 */
	CustRoutesDomain getCustRoutesDetailInfo(Map<String, Object> paramMap);

}
