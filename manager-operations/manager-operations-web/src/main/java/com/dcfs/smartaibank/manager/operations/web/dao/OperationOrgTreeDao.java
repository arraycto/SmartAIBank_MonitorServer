package com.dcfs.smartaibank.manager.operations.web.dao;

import com.dcfs.smartaibank.manager.operations.web.domain.OrgInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 当前机构及其下属机构 dao
 *
 * @author qiucha
 */
@Mapper
public interface OperationOrgTreeDao {

	/**
	 * 根据机构查询下属机构
	 *
	 * @param orgId
	 * @return
	 */
	List<OrgInfo> getOrgTree(String orgId);

}
