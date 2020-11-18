package com.dcfs.smartaibank.manager.monitor.web.dao;


import com.dcfs.smartaibank.manager.monitor.web.domian.HistoryTransInfo;
import com.dcfs.smartaibank.manager.monitor.web.param.HistoryTransParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 历史交易数据查询接口服务
 *
 * @author wangtingo
 * @className HistoryTransDao.java
 * @date 2019-7-11 17:15:45
 */
@Mapper
public interface HistoryTransDao {

    /**
     * 1.查询历史交易的信息
     *
     * @param branchId 机构号
     * @param historyTransParams 查询参数
     * @return HistroyTransInfo 历史交易信息
     */
    List<HistoryTransInfo> getHistroyTransList(@Param("branchId") String branchId,
                                               @Param("historyTransParams") HistoryTransParams historyTransParams);

}
