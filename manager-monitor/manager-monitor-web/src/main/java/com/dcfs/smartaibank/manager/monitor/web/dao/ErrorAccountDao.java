package com.dcfs.smartaibank.manager.monitor.web.dao;


import com.dcfs.smartaibank.manager.monitor.web.domian.ErrorAccountInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 错账信息
 *
 * @author wangtingo
 * @date 2019-8-12 17:15:45
 */
@Mapper
public interface ErrorAccountDao {

    /**
     * 登记错账信息
     *
     * @param errorAccountInfo
     */
    void register(@Param("errorAccountInfo") ErrorAccountInfo errorAccountInfo);


    /**
     * 查询本加钞周期的错账处理记录
     *
     * @param cycleId 加钞周期
     * @return ErrorAccountInfo 错账登记信息
     */
    ErrorAccountInfo queryChecking(String cycleId);

    /**
     * 检测是否登记过
     * @param id 加钞周期id
     * @return 查询返回的结果数
     */
    Integer check(@Param("id") String id);

}
