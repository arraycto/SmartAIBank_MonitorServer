package com.dcfs.smartaibank.manager.operations.web.dao;

import com.dcfs.smartaibank.manager.operations.web.domain.TellerViewTranRankDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.TranRankDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.UserRankDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CommonDistributionDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 类描述
 *
 * @Author qiuch
 * @Since 1.0.0
 */
@Mapper
public interface TellerViewDao {

	/**
	 * 查询柜员视角-交易排名信息
	 *
	 * @param map 查询参数
	 * @return
	 */
	List<TellerViewTranRankDomain<UserRankDomain>> getTranTop20List(Map<String, Object> map);

	/**
	 * 查询用户排名信息
	 *
	 * @param map 查询参数
	 * @return
	 */
	List<UserRankDomain> getTranTop20HandleTimeList(Map<String, Object> map);

	/**
	 * 查询交易排名信息
	 *
	 * @param map 查询参数
	 * @return
	 */
	List<TranRankDomain> getTranTop20HandleTimePersonal(Map<String, Object> map);

	/**
	 * 查询用户排名信息
	 *
	 * @param map 查询参数
	 * @return
	 */
	List<UserRankDomain> getUserRankList(Map<String, Object> map);

	/**
	 * 查询时间分布信息
	 *
	 * @param map 查询参数
	 * @return
	 */
	List<CommonDistributionDomain> getUserDistributionList(Map<String, Object> map);


}
