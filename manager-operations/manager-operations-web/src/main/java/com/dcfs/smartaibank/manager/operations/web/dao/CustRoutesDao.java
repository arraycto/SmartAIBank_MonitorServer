package com.dcfs.smartaibank.manager.operations.web.dao;

import com.dcfs.smartaibank.manager.operations.web.domain.CustRoutesDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 客户动线DAO
 *
 * @author
 */
@Mapper
public interface CustRoutesDao {

	/**
	 * 查询客户动线信息
	 *
	 * @param paramMap 查询参数
	 * @return 客户动线信息
	 */
	List<CustRoutesDomain> getCustRoutesDetailInfo(Map<String, Object> paramMap);

}
