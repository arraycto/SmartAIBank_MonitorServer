package com.dcfs.smartaibank.manager.base.service;

import com.dcfs.smartaibank.manager.base.domain.CombinedSpareDomain;

/**
 * 备选数据查询服务接口
 * @author wangtingo
 */
public interface SpareDataService {

    /**
     * 备选数据
     *
     * @return CombinedSpareDomain
     */
    CombinedSpareDomain getCombinedSpareData();
}
