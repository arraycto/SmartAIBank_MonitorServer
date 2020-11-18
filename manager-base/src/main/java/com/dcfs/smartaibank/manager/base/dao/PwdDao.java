package com.dcfs.smartaibank.manager.base.dao;

import com.dcfs.smartaibank.manager.base.domain.MediumInfo;
import com.dcfs.smartaibank.springboot.core.base.dao.SelectOneDao;
import com.dcfs.smartaibank.springboot.core.base.dao.UpdateByPrimaryKeyDao;
import org.apache.ibatis.annotations.Param;

/**
 * 密码管理DAO
 * @author jiazw
 */
public interface PwdDao extends UpdateByPrimaryKeyDao<MediumInfo>, SelectOneDao<MediumInfo> {

	/**
	 * 锁定密码
	 *
	 * @param mediumInfo
	 * @return 更新记录数
	 */
	Integer lockPwd(MediumInfo mediumInfo);

	/**
	 * 重置密码
	 *
	 * @param mediumInfo
	 * @return
	 */
	Integer resetPwd(MediumInfo mediumInfo);

	/**
	 * 查找用户名
	 *
	 * @param id
	 * @return
	 */
	String getUserName(String id);

	/**
	 * 判断当前机构是否为总行
	 *
	 * @param orgId
	 * @return
	 */
	String checkOrgLevel(String orgId);

	/**
	 * 判断当前需要重置的用户是否在权限内
	 *
	 * @param id
	 * @param orgId
	 * @return
	 */
	String checkOrgUser(@Param("id") String id, @Param("orgId") String orgId);
}
