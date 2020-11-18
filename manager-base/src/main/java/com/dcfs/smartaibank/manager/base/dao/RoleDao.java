package com.dcfs.smartaibank.manager.base.dao;

import com.dcfs.smartaibank.manager.base.domain.AccessSystem;
import com.dcfs.smartaibank.manager.base.domain.Role;
import com.dcfs.smartaibank.manager.base.domain.RoleKey;
import com.dcfs.smartaibank.springboot.core.base.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

/**
 * 角色信息查询
 *
 * @author wangjzm
 */
public interface RoleDao extends BaseDao<RoleKey, Role> {

	/**
	 * 插入角色对应资源权限信息
	 *
	 * @param role 　角色信息实体类
	 * @return Integer 是否插入成功
	 */
	Integer insertRoleAccessSources(Role role);

	/**
	 * 获取用户角色数量
	 *
	 * @param roleKey 　角色主键
	 * @return 角色数量
	 */
	int getUserRoleCount(RoleKey roleKey);

	/**
	 * 删除角色对应资源权限信息
	 *
	 * @param roleKey 　角色主键
	 */
	void deleteRoleAccessSources(RoleKey roleKey);

	/**
	 * 插入某一个角色对应资源对应的访问方式
	 *
	 * @param role 　角色信息实体类
	 * @return 插入行数
	 */
	Integer insertRoleResourceOperateMode(Role role);

	/**
	 * 根据系统id和资源id删除某一个角色对应资源对应的所有访问方式
	 *
	 * @param roleKey 　角色信息实体类
	 */
	void deleteRoleResourceOperateMode(RoleKey roleKey);

	/**
	 * 根据系统id和角色名查询角色名称是否存在
	 *
	 * @param systemId 　系统id
	 * @param roleName 角色名称
	 * @return 1：存在， 0:不存在
	 */
	int selectCountBySystemIdAndName(@Param("systemId") String systemId, @Param("roleName") String roleName);

	/**
	 * 根据用户id查询该用户可管理的所有系统
	 *
	 * @param userId 　用户id
	 * @return 可访问系统
	 */
	AccessSystem selectAccessSystemByUserId(@Param("userId") String userId);

}
