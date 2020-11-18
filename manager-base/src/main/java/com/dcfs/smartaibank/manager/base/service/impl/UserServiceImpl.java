package com.dcfs.smartaibank.manager.base.service.impl;

import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.manager.base.service.UserService;
import com.dcfs.smartaibank.manager.base.dao.UserDao;
import com.dcfs.smartaibank.manager.base.domain.User;
import com.dcfs.smartaibank.springboot.core.base.service.BaseServiceUseDaoImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 用户服务
 *
 * @author jiazw
 * @since 1.0.0
 */
@Service
public class UserServiceImpl extends BaseServiceUseDaoImpl<String, User, UserDao> implements UserService {

	/**
	 * 按主键删除记录
	 *
	 * @param id 主键
	 * @return 删除的行数
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer deleteById(String id) {
		try {
			dao.deleteUserMedium(id);
			dao.deleteUserRole(id);
			return dao.deleteByPrimaryKey(id);
		} catch (Exception e) {
			throw new BusinessException("data.access", e);
		}
	}

	/**
	 * 新增记录
	 *
	 * @param entity
	 * @return 新增的行数
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer insert(User entity) {
		try {
			entity.setCreateTime(new Date());
			Integer index = dao.insert(entity);
			insertOrUpdateUserRole(entity);
			dao.insertUserMedium(entity.getId());
			return index;
		} catch (Exception e) {
			throw new BusinessException("data.access", e);
		}
	}

	/**
	 * 分页查询记录
	 *
	 * @param entity   查询条件
	 * @param pageNum  页数
	 * @param pageSize 每页记录条数
	 * @return 分页查询结果
	 */
	@Override
	public PageInfo<User> selectByPage(User entity, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<User> list = this.select(entity);
		return new PageInfo<>(list);
	}

	/**
	 * 按主键更新记录
	 *
	 * @param entity 记录
	 * @return 更新的行数
	 */
	@Override
	public Integer updateById(User entity) {
		try {
			entity.setUpdateTime(new Date());
			insertOrUpdateUserRole(entity);
			return dao.updateByPrimaryKey(entity);
		} catch (Exception e) {
			throw new BusinessException("data.access", e);
		}
	}

	/**
	 * 新增用户角色
	 *
	 * @param entity
	 */
	private void insertOrUpdateUserRole(User entity) {
		dao.deleteUserRole(entity.getId());
		for (String roleId : entity.getRoles()) {
			dao.insertUserRole(entity.getId(), roleId);
		}
	}
}
