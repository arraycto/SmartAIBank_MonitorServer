package com.dcfs.smartaibank.manager.monitor.notify.service;

import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.notify.dao.BranchDao;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 机构信息管理
 *
 * @author jiazw
 */
@Component("branchManager")
public class BranchManagerImpl implements BranchManager, InitializingBean {

	private Map<String, String> map = new ConcurrentHashMap<>();

	@Autowired
	private BranchDao dao;

	@Override
	public void init() {
		List<Map<String, String>> superiores = this.dao.getSuperior();
		for (Map<String, String> m : superiores) {
			String branchNo = m.get(Constants.BRANCH_NO);
			String superior = m.get("SUPERIOR");
			if (branchNo != null && superior != null) {
				this.map.put(branchNo, superior);
			}
		}
	}

	@Override
	public String getSuperior(String branchNo) {
		return map.get(branchNo);
	}

	@Override
	public void afterPropertiesSet() {
		init();
	}
}
