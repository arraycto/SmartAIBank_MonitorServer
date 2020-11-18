package com.dcfs.smartaibank.manager.monitor.web.dao;

import com.dcfs.smartaibank.manager.monitor.web.domian.RemoteControlLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author liangclenglong
 * @date 2019/7/27 13:29
 * @since
 */
@Mapper
public interface MonitorRemoteDao {

    /**
     * 获取请求序列
     * @return
     */
    String getRemoteOrder();

    /**
     * 插入日志
     * @param remoteControlLog
     * @return
     */
    Integer insertLog(RemoteControlLog remoteControlLog);

    /**
     * 修改远程操作日志
     * @param remoteControlLog
     * @return
     */
    Integer updateLog(RemoteControlLog remoteControlLog);
}
