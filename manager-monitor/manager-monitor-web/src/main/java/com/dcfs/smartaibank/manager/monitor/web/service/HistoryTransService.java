package com.dcfs.smartaibank.manager.monitor.web.service;

import com.dcfs.smartaibank.manager.monitor.web.domian.HistoryTransInfo;
import com.dcfs.smartaibank.manager.monitor.web.param.HistoryTransParams;
import com.github.pagehelper.PageInfo;


/**
 * @author wangtingo
 * @date 2019/7/11 10:56
 * @since
 */
public interface HistoryTransService {

    /**
     * 查询历史交易信息
     *
     * @param branchId 机构编号
     * @param historyTransParams 查询条件
     * @return HistroyTransInfo 历史交易信息
     */
    PageInfo<HistoryTransInfo> getHistroyTransInfo(String branchId, HistoryTransParams historyTransParams);

}
