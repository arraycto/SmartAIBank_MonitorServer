package com.dcfs.smartaibank.manager.base.service.impl;

import com.dcfs.smartaibank.manager.base.dao.SpareDataDao;
import com.dcfs.smartaibank.manager.base.domain.CombinedSpareDomain;
import com.dcfs.smartaibank.manager.base.service.SpareDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 备选数据服务
 *
 * @author
 */
@Service
public class SpareDataServiceImpl implements SpareDataService {

	@Autowired
	private SpareDataDao spareDataDao;

	@Override
	public CombinedSpareDomain getCombinedSpareData() {
		CombinedSpareDomain result = new CombinedSpareDomain();
		result.setOrgLevelList(spareDataDao.getOrgLevelList());
		result.setReportLevelList(spareDataDao.getAllOrgLevelList());
		result.setOrgTypeList(spareDataDao.getAllOrgTypeList());
		result.setSystemList(spareDataDao.getAllSystemList());
		result.setMediumList(spareDataDao.getAllMediaTypeList());
		return result;
	}

}
