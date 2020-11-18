package com.dcfs.smartaibank.manager.base.dao;

import com.dcfs.smartaibank.manager.base.domain.Resource;
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
	 * @param userId   用户id
	 * @param systemId 系统id
	 * @return
	 */
	List<Resource> selectAccessResourceByUserIdAndSystemId(@Param("userId") String userId,
														   @Param("systemId") String systemId);
}
