package com.dcfs.smartaibank.manager.monitor.web.service.impl;


import com.dcfs.smartaibank.manager.monitor.web.dao.ErrorAccountDao;
import com.dcfs.smartaibank.manager.monitor.web.domian.ErrorAccountInfo;
import com.dcfs.smartaibank.manager.monitor.web.service.ErrorAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangtingo
 * @date 2019/6/20 10:58
 * @since
 */
@Service
public class ErrorAccountServiceImpl implements ErrorAccountService {

    @Autowired
    private ErrorAccountDao errorAccountDao;

    @Override
    public void register(ErrorAccountInfo errorAccountInfo) {
        errorAccountDao.register(errorAccountInfo);
    }

    @Override
    public ErrorAccountInfo queryChecking(String cycleId) {
        return errorAccountDao.queryChecking(cycleId);
    }

    @Override
    public Boolean check(String id) {
        Integer i = errorAccountDao.check(id);
        return i > 0 ? false : true;
    }
}
