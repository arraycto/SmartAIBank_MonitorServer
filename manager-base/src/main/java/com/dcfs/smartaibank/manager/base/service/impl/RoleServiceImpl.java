package com.dcfs.smartaibank.manager.base.service.impl;

import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.manager.base.domain.AccessSystem;
import com.dcfs.smartaibank.manager.base.domain.Role;
import com.dcfs.smartaibank.manager.base.domain.RoleKey;
import com.dcfs.smartaibank.manager.base.dao.RoleDao;
import com.dcfs.smartaibank.manager.base.service.RoleService;
import com.dcfs.smartaibank.springboot.core.base.service.BaseServiceUseDaoImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色服务
 *
 * @author wangjzm
 * @since 1.0.0
 */
@Service
@Slf4j
public class RoleServiceImpl extends BaseServiceUseDaoImpl<RoleKey, Role, RoleDao> implements RoleService {

	/**
	 * 新增记录
	 *
	 * @param role 角色信息
	 * @return 插入成功失败标识
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer insert(Role role) {
		// 角色名称是否在系统中存在
		int count = dao.selectCountBySystemIdAndName(role.getSystemId(), role.getName());
		if (count > 0) {
			throw new BusinessException("role.name.exists");
		}
		try {
			// 插入基础表
			dao.insert(role);
			// 插入角色资源表
			dao.insertRoleAccessSources(role);
			// 插入某一个角色对应资源对应的访问方式
			return dao.insertRoleResourceOperateMode(role);
		} catch (Exception e) {
			throw new BusinessException("data.access", e);
		}
	}

	/**
	 * 更新角色信息
	 *
	 * @param roleInfo 角色信息
	 * @return 更新成功失败标识
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer updateById(Role roleInfo) {
		RoleKey roleKey = new RoleKey(roleInfo.getId(), roleInfo.getSystemId());
		// 更新查询角色名称是否在系统中存在
		Role temp = dao.selectByPrimaryKey(roleKey);
		if (temp != null) {
			if (!temp.getName().equals(roleInfo.getName())) {
				int count = dao.selectCountBySystemIdAndName(roleInfo.getSystemId(), roleInfo.getName());
				if (count > 0) {
					throw new BusinessException("role.name.exists");
				}
			}
		} else {
			throw new BusinessException("role.not.exists");
		}

		try {
			// 更新基础表
			dao.updateByPrimaryKey(roleInfo);
			// 更新某一个角色对应资源对应的访问方式，先删除后插入
			dao.deleteRoleResourceOperateMode(roleKey);
			// 更新角色资源表,先删除后插入
			dao.deleteRoleAccessSources(roleKey);
			dao.insertRoleAccessSources(roleInfo);
			return dao.insertRoleResourceOperateMode(roleInfo);
		} catch (Exception e) {
			throw new BusinessException("data.access", e);
		}
	}

	/**
	 * 删除角色信息
	 *
	 * @param roleKey 角色信息
	 * @return 删除成功失败标识
	 */
	@Override
	public Integer deleteById(RoleKey roleKey) {
		// 删除角色时首先需要查询该角色是否正在被使用，如果正在被使用不允许删除
		int count = dao.getUserRoleCount(roleKey);
		if (count > 0) {
			throw new BusinessException("role.delete.notallowed");
		}
		try {
			// 删除某一个角色对应资源对应的访问方式
			dao.deleteRoleResourceOperateMode(roleKey);
			// 删除角色资源表
			dao.deleteRoleAccessSources(roleKey);
			// 删除角色基础表
			return dao.deleteByPrimaryKey(roleKey);
		} catch (Exception e) {
			throw new BusinessException("data.access", e);
		}
	}

	/**
	 * 分页查询
	 *
	 * @param role     角色信息
	 * @param pageNum  页数
	 * @param pageSize 每页记录条数
	 * @return 角色信息分页对象
	 */
	@Override
	public PageInfo<Role> selectByPage(Role role, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Role> roles = dao.select(role);
		return new PageInfo<>(roles);
	}

	/**
	 * 根据用户id查询该用户可管理的所有系统
	 *
	 * @param userId 用户id
	 * @return AccessSystem 可选系统备选数据
	 */
	@Override
	public AccessSystem selectAccessSystemByUserId(String userId) {
		return dao.selectAccessSystemByUserId(userId);

	}

}
