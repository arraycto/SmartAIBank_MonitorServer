package com.dcfs.smartaibank.manager.monitor.notify.service;

/**
 * 机构管理
 *
 * @author jiazw
 */
public interface BranchManager {
	/**
	 * 初始化
	 */
	void init();

	/**
	 * 获取上级机构
	 *
	 * @param branchNo 查询机构
	 * @return 上级机构
	 */
	String getSuperior(String branchNo);

}
