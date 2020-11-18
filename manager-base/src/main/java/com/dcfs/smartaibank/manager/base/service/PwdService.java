package com.dcfs.smartaibank.manager.base.service;

import com.dcfs.smartaibank.manager.base.domain.MediumInfo;
import com.dcfs.smartaibank.springboot.core.base.service.BaseUpdateService;
import com.dcfs.smartaibank.springboot.core.base.service.SelectByIdService;

import java.util.Map;

/**
 * 密码管理服务接口
 *
 * @author
 */
public interface PwdService extends BaseUpdateService<MediumInfo>, SelectByIdService<String, MediumInfo> {

	/**
	 * 重置用户密码
	 *
	 * @param orgId 机构ID
	 * @param id    用户ID
	 * @param resetId 用户ID
	 * @return
	 */
	Map<String, String> resetById(String id, String resetId, String orgId);

	/**
	 * 锁定密码
	 *
	 * @param id 用户ID
	 * @return
	 */
	Integer lockById(String id);

	/**
	 * 查找用户名
	 * @param id 用户ID
	 * @return 用户名称
	 */
	String getUserName(String id);

}
