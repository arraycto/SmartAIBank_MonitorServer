package com.dcfs.smartaibank.manager.base.dao;

import com.dcfs.smartaibank.manager.base.domain.Org;
import com.dcfs.smartaibank.manager.base.domain.SpareDataDomain;
import com.dcfs.smartaibank.springboot.core.base.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 机构DAO
 *
 * @author wangting
 * @since 1.0.0
 */
public interface OrgDao extends BaseDao<String, Org> {
	/**
	 * 查询指定机构的下级机构数量
	 *
	 * @param id 机构ID
	 * @return 下级机构数量
	 */
	Integer selectOrgSuperior(@Param("id") String id);

	/**
	 * 查询机构下的用户信息
	 *
	 * @param id 机构ID
	 * @return
	 */
	Integer selectOrgUser(@Param("id") String id);

	/**
	 * 修改机构状态（逻辑删除机构）
	 *
	 * @param id 机构ID
	 * @return 更新结果
	 */
	Integer updateOrgId(@Param("id") String id);

	/**
	 * 查询机构等级
	 *
	 * @param queryMap 查询条件
	 * @return 机构等级
	 */
	List<SpareDataDomain> selectLevel(Map<String, Object> queryMap);

	/**
	 * 查询上级机构
	 *
	 * @param queryMap 查询条件
	 * @return 上级机构
	 */
	List<SpareDataDomain> selectBussSuperior(Map<String, Object> queryMap);

	/**
	 * 查询账户上级机构
	 *
	 * @param queryMap 查询条件
	 * @return 上级机构
	 */
	List<SpareDataDomain> selectAccSuperior(Map<String, Object> queryMap);
}
