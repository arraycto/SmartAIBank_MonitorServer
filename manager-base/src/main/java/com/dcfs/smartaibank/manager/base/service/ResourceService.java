package com.dcfs.smartaibank.manager.base.service;

import com.dcfs.smartaibank.manager.base.domain.Resource;

import java.util.List;

/**
 * 资源信息查询
 *
 * @author wangjzm
 */
public interface ResourceService {

	/**
	 * 根据用户id查询该用户可管理的所有系统
	 *
	 * @param userId   用户id
	 * @param systemId 系统id
	 * @return 资源信息集合
	 */
	List<Resource> selectAccessResourceByUserIdAndSystemId(String userId, String systemId);
}
