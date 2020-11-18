package com.dcfs.smartaibank.manager.monitor.web.service;

import com.dcfs.smartaibank.manager.monitor.web.domian.OrgInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 机构信息查询业务层接口
 *
 * @author wangjzm
 * @since 1.0.0
 */
@Component
public interface OrgTreeService {

    /**
     * 根据上送orgId 查询当前机构及下属机构
     *
     * @param orgId 机构编号
     * @return
     */
    List<OrgInfo> findOrgTree(String orgId);

}
