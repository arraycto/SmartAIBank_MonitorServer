package com.dcfs.smartaibank.manager.operations.web.service.impl;


import com.dcfs.smartaibank.manager.operations.web.dao.CustRoutesDao;
import com.dcfs.smartaibank.manager.operations.web.domain.CustRoutesDomain;
import com.dcfs.smartaibank.manager.operations.web.service.CustRoutesService;
import com.dcfs.smartaibank.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 客户动线服务
 *
 * @author
 */
@Service
@Slf4j
public class CustRoutesServiceImpl implements CustRoutesService {
	@Autowired
	private CustRoutesDao custRoutesDao;

	@Override
	public CustRoutesDomain getCustRoutesDetailInfo(Map<String, Object> paramMap) {
		CustRoutesDomain custRoutesInfo = new CustRoutesDomain();
		try {
			List<CustRoutesDomain> list = custRoutesDao.getCustRoutesDetailInfo(paramMap);
			custRoutesInfo.setRouteList(list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new BusinessException("data.access");
		}
		return custRoutesInfo;
	}
}
