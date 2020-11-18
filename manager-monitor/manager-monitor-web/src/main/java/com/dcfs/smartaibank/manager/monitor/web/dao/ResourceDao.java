package com.dcfs.smartaibank.manager.monitor.web.dao;

import com.dcfs.smartaibank.manager.monitor.web.domian.Resource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资源信息查询
 *
 * @author wangjzm
 */
public interface ResourceDao {
    /**
     * 根据用户id查询该用户可管理的所有系统
     *
     * @param userId 用户id
     * @return 源信息集合
     */
    List<Resource> selectAccessResourceByUserIdAndSystemId(@Param("userId") String userId);
}
