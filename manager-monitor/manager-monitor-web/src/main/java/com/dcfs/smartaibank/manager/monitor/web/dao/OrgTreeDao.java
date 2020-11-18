package com.dcfs.smartaibank.manager.monitor.web.dao;

import com.dcfs.smartaibank.manager.monitor.web.domian.OrgInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 当前机构及其下属机构Dao
 *
 * @author wangjzm
 * @data 2019/06/17 09:56
 * @since 1.0.0
 */

@Component
@Mapper
public interface OrgTreeDao {

    /**
     * 根据机构查询下属机构
     *
     * @param orgId
     * @return
     */
    List<OrgInfo> getOrgTree(String orgId);

}
