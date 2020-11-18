package com.dcfs.smartaibank.manager.monitor.web.service;

import com.dcfs.smartaibank.manager.monitor.web.domian.ErrorAccountInfo;



/**
 * @author wangtingo
 * @date 2019/8/12 10:56
 * @since
 */
public interface ErrorAccountService {

    /**
     * 错账登记
     * @param errorAccountInfo   错账信息
     */
    void register(ErrorAccountInfo errorAccountInfo);

    /***
     * 查询本加钞周期的错账处理记录
     * @param cycleId 假钞周期
     * @return
     */
    ErrorAccountInfo queryChecking(String cycleId);

    /**
     * 查询是否登记过
     * @param id 加钞周期id
     * @return boolean值
     */
    Boolean check(String id);

}
