package com.dcfs.smartaibank.manager.monitor.analyzer.service.impl;

import java.util.Map;

import com.dcfs.smartaibank.manager.monitor.analyzer.dao.CommonDao;
import com.dcfs.smartaibank.manager.monitor.analyzer.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * 通用服务服务实现类
 *
 * @author jiazw
 */
@Component("commonService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
public class CommonServiceImpl implements CommonService {
	@Autowired
	private CommonDao commonDao;

	/**
	 * @return
	 */
	@Override
	public boolean isExpectCount(Map<String, Object> param) {
		param.put("RESULT", 0);
		commonDao.computeRepeatCount(param);
		int count = (Integer) param.get("RESULT");
		return count > 0;
	}

}

