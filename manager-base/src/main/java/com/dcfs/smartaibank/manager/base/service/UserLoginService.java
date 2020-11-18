package com.dcfs.smartaibank.manager.base.service;

import com.dcfs.smartaibank.manager.base.domain.UserInfo;

/**
 * @author tanchena
 * @date 2019/8/20 9:53
 */
public interface UserLoginService {

	/**
	 * 查询用户登陆之后的角色和资源信息
	 * @param id 用户id
	 * @return
	 */
	UserInfo userLogin(String id);

}
