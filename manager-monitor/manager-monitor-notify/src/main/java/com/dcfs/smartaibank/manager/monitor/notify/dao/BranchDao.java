package com.dcfs.smartaibank.manager.monitor.notify.dao;

import java.util.List;
import java.util.Map;

/**
 * 机构DAO
 *
 * @author jiazw
 */
public interface BranchDao {
	/**
	 * 查询机构及上级机构
	 *
	 * @return 机构及上级机构
	 */
	List<Map<String, String>> getSuperior();
}
