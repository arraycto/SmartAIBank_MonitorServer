package com.dcfs.smartaibank.manager.operations.web.service;

import com.dcfs.smartaibank.manager.operations.web.domain.OrgInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 结构树服务
 *
 * @author
 */
@Component
public interface OperationOrgTreeService {

	/**
	 * 根据上送orgId 查询当前机构及下属机构
	 *
	 * @param orgId 机构编号
	 * @return
	 */
	List<OrgInfo> findOrgTree(String orgId);

}
