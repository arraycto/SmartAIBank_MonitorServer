/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: OrgServiceImpl
 * Author:   jiazw
 * Date:     2019/4/30 13:19
 * Description: 机构信息服务
 * History:
 * 作者姓名           修改时间           版本号              描述
 */
package com.dcfs.smartaibank.manager.base.service.impl;

import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.manager.base.domain.Org;
import com.dcfs.smartaibank.manager.base.domain.OrgType;
import com.dcfs.smartaibank.manager.base.domain.SpareDataDomain;
import com.dcfs.smartaibank.manager.base.service.OrgService;
import com.dcfs.smartaibank.manager.base.dao.OrgDao;
import com.dcfs.smartaibank.springboot.core.base.service.BaseServiceUseDaoImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构信息服务
 *
 * @author jiazw
 * @since 1.0.0
 */
@Service
public class OrgServiceImpl extends BaseServiceUseDaoImpl<String, Org, OrgDao> implements OrgService {

	/**
	 * 分页查询记录
	 *
	 * @param entity   查询条件
	 * @param pageNum  页数
	 * @param pageSize 每页记录条数
	 * @return 分页查询结果
	 */
	@Override
	public PageInfo<Org> selectByPage(Org entity, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Org> list = this.select(entity);
		return new PageInfo<>(list);
	}

	/**
	 * 按主键删除记录
	 * 机构是逻辑删除
	 *
	 * @param id 主键
	 * @return 删除的行数
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer deleteById(String id) {
		Integer index = 0;
		try {
			//查询是否有下级机构
			Integer countSuperior = dao.selectOrgSuperior(id);
			//查询此机构下是否有用户信息
			Integer countUser = dao.selectOrgUser(id);
			if (countSuperior == 0 && countUser == 0) {
				//逻辑删除机构信息
				index = dao.updateOrgId(id);
			}
			if (countSuperior != 0) {
				//提示有下级机构，不能删除此机构
				throw new BusinessException("org.not.delete.superior", "");
			}
			if (countUser != 0) {
				//提示此机构有用户信息，不能删除
				throw new BusinessException("org.not.delete.users", "");
			}
			return index;
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
	public Integer insert(Org entity) {
		try {
			entity.setCreateTime(new Date());
			Integer index = dao.insert(entity);
			return index;
		} catch (Exception e) {
			throw new BusinessException("data.access", e);
		}
	}

	/**
	 * 按主键更新记录
	 *
	 * @param entity 记录
	 * @return 更新的行数
	 */
	@Override
	public Integer updateById(Org entity) {
		try {
			entity.setUpdateTime(new Date());
			return dao.updateByPrimaryKey(entity);
		} catch (Exception e) {
			throw new BusinessException("data.access", e);
		}
	}

	/**
	 * 根据选择的机构类型和机构级别，查询上级机构信息和账务机构信息
	 *
	 * @param queryMap
	 * @return
	 */
	@Override
	public Map<String, List<SpareDataDomain>> selectQuerySuperior(Map<String, Object> queryMap) {
		Map<String, List<SpareDataDomain>> map = new HashMap<>(16);
		//查询业务管理上级
		List<SpareDataDomain> bussSuperiorList = dao.selectBussSuperior(queryMap);
		String type = queryMap.get("type").toString();
		if (type.equals(OrgType.MANAGE) || type.equals(OrgType.MANAGEDEPART)) {
			//查询账务机构信息
			List<SpareDataDomain> accSuperiorList = dao.selectAccSuperior(queryMap);
			map.put("AccSuperiorList", accSuperiorList);
		}
		map.put("BussSuperiorList", bussSuperiorList);
		return map;
	}

	/**
	 * 根据选择的机构类型查询机构级别
	 *
	 * @param queryMap
	 * @return
	 */
	@Override
	public List<SpareDataDomain> selectLevel(Map<String, Object> queryMap) {
		List<SpareDataDomain> levelList = dao.selectLevel(queryMap);
		return levelList;
	}
}
