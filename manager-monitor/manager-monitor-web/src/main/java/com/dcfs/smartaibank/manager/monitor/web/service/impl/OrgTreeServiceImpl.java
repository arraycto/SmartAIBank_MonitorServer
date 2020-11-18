package com.dcfs.smartaibank.manager.monitor.web.service.impl;

import com.dcfs.smartaibank.manager.monitor.web.dao.OrgTreeDao;
import com.dcfs.smartaibank.manager.monitor.web.domian.OrgInfo;
import com.dcfs.smartaibank.manager.monitor.web.service.OrgTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 机构信息查询业务层实现
 *
 * @author wangjzm
 * @since 1.0.0
 */
@Service
public class OrgTreeServiceImpl implements OrgTreeService {
    @Autowired
    private OrgTreeDao dao;

    @Override
    public List<OrgInfo> findOrgTree(String orgId) {
        return dao.getOrgTree(orgId);
    }
}
