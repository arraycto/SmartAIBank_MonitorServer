package com.dcfs.smartaibank.manager.operations.web.dao;

import com.dcfs.smartaibank.manager.operations.web.batch.domain.CustomerAssetsDomain;
import com.dcfs.smartaibank.manager.operations.web.batch.domain.CustomerInfoDomain;
import com.dcfs.smartaibank.manager.operations.web.batch.domain.TradeInfoDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 数据预处理dao，包括数据导入，数据清理的dao
 *
 * @author yangpingf
 */
@Mapper
public interface DataPreDealDao {
	/**
	 * 清空客户资产
	 */
	void truncateTempCustAssets();

	/**
	 * 清空客户信息
	 */
	void truncateTempCustomerInfo();

	/**
	 * 插入客户信息
	 *
	 * @param customerDomain
	 */
	void insertCustomerInfo(CustomerInfoDomain customerDomain);

	/**
	 * 根据客户号更新客户信息
	 *
	 * @param customerInfo 客户信息
	 * @return
	 */
	int updateCustomerInfo(CustomerInfoDomain customerInfo);

	/**
	 * 厅堂营销数据处理
	 *
	 * @param paramMap
	 */
	void hallMarketDataDeal(Map<String, Object> paramMap);

	/**
	 * 交易信息处理
	 *
	 * @param paramMap 参数
	 */
	void tradeDataDeal(Map<String, Object> paramMap);

	/**
	 * 排队数据处理
	 *
	 * @param paramMap 参数
	 */
	void queueDataDeal(Map<String, Object> paramMap);

	/**
	 * 客户动线处理
	 *
	 * @param paramMap 参数
	 */
	void custRoutesDataDeal(Map<String, Object> paramMap);

	/**
	 * 客户信息处理
	 *
	 * @param paramMap 参数
	 */
	void custDataDeal(Map<String, Object> paramMap);

	/**
	 * 更新客户资产信息
	 *
	 * @param domain 客户资产信息
	 */
	void insertCustomerAssetInfo(CustomerAssetsDomain domain);

	/**
	 * 插入交易信息
	 *
	 * @param domain 柜面交易信息
	 */
	void insertTradeinfo(TradeInfoDomain domain);

}
