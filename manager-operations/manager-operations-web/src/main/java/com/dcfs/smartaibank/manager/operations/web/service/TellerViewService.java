package com.dcfs.smartaibank.manager.operations.web.service;

import com.dcfs.smartaibank.manager.operations.web.domain.TellerViewPersonalDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.TellerViewRankAndDetailDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.TranTop20Domain;

import java.util.Map;

/**
 * 类描述
 *
 * @Author qiuch
 * @Since 1.0.0
 */
public interface TellerViewService {
	/**
	 * 查询交易处理时长统计
	 *
	 * @param map
	 * @return
	 * @method getHandleTimeInfo
	 * @description 交易处理时长统计
	 * @date: 2019/3/6 16:09
	 */
	TellerViewRankAndDetailDomain getHandleTimeInfo(Map<String, Object> map);

	/**
	 * 查询交易数量统计
	 *
	 * @param map
	 * @return
	 * @method getTranCountInfo
	 * @description 交易数量统计
	 * @date: 2019/3/6 16:09
	 */
	TellerViewRankAndDetailDomain getTranCountInfo(Map<String, Object> map);

	/**
	 * 高频交易处理时长统计
	 *
	 * @param map
	 * @return
	 * @method getTranHandleTimeInfo
	 * @description 高频交易处理时长统计
	 * @date: 2019/3/6 16:09
	 */
	TranTop20Domain getTranTop20HandleTimeInfo(Map<String, Object> map);

	/**
	 * 服务质量统计（评价率，好评率，差评率）
	 *
	 * @param map
	 * @return
	 * @method geterviceQualityInfo
	 * @description 服务质量统计（评价率，好评率，差评率）
	 * @date: 2019/3/6 16:09
	 */
	TellerViewRankAndDetailDomain getServiceQualityInfo(Map<String, Object> map);

	/**
	 * 柜员个人详情统计
	 *
	 * @param map
	 * @return
	 * @method getPersonalDetailInfo
	 * @description 柜员个人详情统计
	 * @date: 2019/3/6 16:09
	 */
	TellerViewPersonalDomain getPersonalDetailInfo(Map<String, Object> map);
}
