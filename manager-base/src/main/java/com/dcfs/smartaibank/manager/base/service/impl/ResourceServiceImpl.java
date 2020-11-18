package com.dcfs.smartaibank.manager.base.service.impl;

import com.dcfs.smartaibank.manager.base.dao.ResourceDao;
import com.dcfs.smartaibank.manager.base.domain.Resource;
import com.dcfs.smartaibank.manager.base.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资源服务
 *
 * @author wangjzm
 * @since 1.0.0
 */
@Service
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	ResourceDao resourceDao;

	@Override
	public List<Resource> selectAccessResourceByUserIdAndSystemId(String userId, String systemId) {
		return resourceDao.selectAccessResourceByUserIdAndSystemId(userId, systemId);
	}
}
