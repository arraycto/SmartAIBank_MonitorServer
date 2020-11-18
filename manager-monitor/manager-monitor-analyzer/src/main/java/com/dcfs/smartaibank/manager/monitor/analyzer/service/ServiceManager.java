package com.dcfs.smartaibank.manager.monitor.analyzer.service;

import java.util.Map;

/**
 * 服务管理接口
 *
 * @author jiazw
 */
public interface ServiceManager {
	/**
	 * 根据类型获取服务
	 *
	 * @param type 服务类型
	 * @return 服务
	 */
	Service getService(String type);

	/**
	 * 初始化服务集合
	 *
	 * @param services 服务集合
	 */
	void setServices(Map<String, Service> services);

	/**
	 * 添加服务
	 *
	 * @param type    服务类型
	 * @param service 服务
	 */
	void addService(String type, Service service);

	/**
	 * 是否包含指定服务类型的服务
	 * @param type 服务类型
	 * @return 服务
	 */
	boolean contains(String type);
}

