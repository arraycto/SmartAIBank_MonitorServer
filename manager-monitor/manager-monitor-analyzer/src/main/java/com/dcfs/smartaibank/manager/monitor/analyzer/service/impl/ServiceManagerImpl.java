package com.dcfs.smartaibank.manager.monitor.analyzer.service.impl;

import com.dcfs.smartaibank.manager.monitor.analyzer.service.Service;
import com.dcfs.smartaibank.manager.monitor.analyzer.service.ServiceManager;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务管理类
 *
 * @author jiazw
 */
@Component("serviceManager")
public class ServiceManagerImpl implements ServiceManager {

	private Map<String, Service> services = new HashMap<>(16);

	@Override
	public Service getService(String type) {
		return services.get(type);
	}

	@Override
	public void setServices(Map<String, Service> services) {
		this.services = services;
	}

	@Override
	public void addService(String type, Service service) {
		this.services.put(type, service);
	}

	@Override
	public boolean contains(String type) {
		return this.services.containsKey(type);
	}

}

