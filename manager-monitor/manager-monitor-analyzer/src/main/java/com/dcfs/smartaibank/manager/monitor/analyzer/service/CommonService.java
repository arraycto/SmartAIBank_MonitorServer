package com.dcfs.smartaibank.manager.monitor.analyzer.service;

import java.util.Map;

/**
 * 通用服务
 *
 * @author jiazw
 */

public interface CommonService {
	/**
	 * 是否达到预期次数
	 *
	 * @param map 查询条件
	 * @return true:达到预期次数
	 */
	boolean isExpectCount(Map<String, Object> map);
}
