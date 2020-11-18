package com.dcfs.smartaibank.manager.monitor.web.service.impl;


import com.dcfs.smartaibank.manager.monitor.web.dao.HistoryTransDao;
import com.dcfs.smartaibank.manager.monitor.web.domian.HistoryTransInfo;
import com.dcfs.smartaibank.manager.monitor.web.param.HistoryTransParams;
import com.dcfs.smartaibank.manager.monitor.web.service.HistoryTransService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangtingo
 * @date 2019/7/11 10:58
 * @since
 */
@Service
public class HistoryTransServiceImpl implements HistoryTransService {

    @Autowired
    private HistoryTransDao historyTransDao;

    @Override
    public PageInfo<HistoryTransInfo> getHistroyTransInfo(String branchId, HistoryTransParams historyTransParams) {
        PageHelper.startPage(historyTransParams.getPageNum(), historyTransParams.getPageSize());
        List<HistoryTransInfo> historyTransInfos =
                historyTransDao.getHistroyTransList(branchId, historyTransParams);
        return new PageInfo<>(historyTransInfos);
    }
}
