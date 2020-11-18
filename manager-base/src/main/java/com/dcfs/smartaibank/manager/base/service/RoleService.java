package com.dcfs.smartaibank.manager.base.service;

import com.dcfs.smartaibank.manager.base.domain.AccessSystem;
import com.dcfs.smartaibank.manager.base.domain.Role;
import com.dcfs.smartaibank.manager.base.domain.RoleKey;
import com.dcfs.smartaibank.springboot.core.base.service.BaseService;
import com.dcfs.smartaibank.springboot.core.base.service.SelectPageByBeanService;

/**
 * 角色服务
 *
 * @author wangjzm
 * @since 1.0.0
 */
public interface RoleService extends BaseService<RoleKey, Role>,
	SelectPageByBeanService<Role> {

	/**
	 * 根据用户id查询该用户可管理的所有系统
	 *
	 * @param userId 用户id
	 * @return AccessSystem 可选系统备选数据
	 */
	AccessSystem selectAccessSystemByUserId(String userId);

}
