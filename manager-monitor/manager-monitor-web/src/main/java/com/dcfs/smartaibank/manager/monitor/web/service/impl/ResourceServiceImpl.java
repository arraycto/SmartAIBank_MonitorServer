package com.dcfs.smartaibank.manager.monitor.web.service.impl;

import com.dcfs.smartaibank.manager.monitor.web.dao.ResourceDao;
import com.dcfs.smartaibank.manager.monitor.web.domian.Resource;
import com.dcfs.smartaibank.manager.monitor.web.service.ResourceService;
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
    public List<Resource> selectAccessResourceByUserIdAndSystemId(String userId) {
        return resourceDao.selectAccessResourceByUserIdAndSystemId(userId);
    }
}
