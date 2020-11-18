package com.dcfs.smartaibank.manager.operations.web.service.impl;

import com.dcfs.smartaibank.manager.operations.web.domain.OrgInfo;
import com.dcfs.smartaibank.manager.operations.web.dao.OperationOrgTreeDao;
import com.dcfs.smartaibank.manager.operations.web.service.OperationOrgTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 结构树服务
 *
 * @author
 */
@Service
public class OperationOrgTreeServiceImpl implements OperationOrgTreeService {
	@Autowired
	private OperationOrgTreeDao dao;

	/**
	 * 查询机构数
	 *
	 * @param orgId 机构编号
	 * @return 机构数
	 */
	@Override
	public List<OrgInfo> findOrgTree(String orgId) {
		return dao.getOrgTree(orgId);
	}
}
