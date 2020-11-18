/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: UserDao
 * Author:   jiazw
 * Date:     2018/12/6 10:05
 * Description: 用户DAO
 * History:
 * 作者姓名           修改时间           版本号              描述
 */
package com.dcfs.smartaibank.manager.base.dao;

import com.dcfs.smartaibank.manager.base.domain.User;
import com.dcfs.smartaibank.manager.base.domain.UserInfo;
import com.dcfs.smartaibank.springboot.core.base.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

/**
 * 用户DAO
 *
 * @author jiazw
 * @since 1.0.0
 */
public interface UserDao extends BaseDao<String, User> {
	/**
	 * 新增用户角色
	 *
	 * @param userId 用户ID
	 * @param roleId 角色ID
	 * @return
	 */
	Integer insertUserRole(@Param("userId") String userId, @Param("roleId") String roleId);

	/**
	 * 删除用户所属角色
	 *
	 * @param userId 用户ID
	 * @return
	 */
	Integer deleteUserRole(@Param("userId") String userId);

	/**
	 * 新建用户密码
	 *
	 * @param userId
	 * @return
	 */
	Integer insertUserMedium(@Param("userId") String userId);

	/**
	 * 删除用户密码
	 *
	 * @param userId 用户ID
	 * @return
	 */
	Integer deleteUserMedium(@Param("userId") String userId);

	/**
	 * 根据用户ID获取用户信息
	 *
	 * @param id 用户ID
	 * @return User用户信息
	 */
	UserInfo selectByUserId(@Param(value = "id") String id);

}

