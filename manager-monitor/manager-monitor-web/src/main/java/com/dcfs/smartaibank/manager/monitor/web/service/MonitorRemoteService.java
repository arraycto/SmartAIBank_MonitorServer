package com.dcfs.smartaibank.manager.monitor.web.service;

import com.dcfs.smartaibank.manager.monitor.web.config.MonitorRemoteConfig;
import com.dcfs.smartaibank.manager.monitor.web.param.AccountDetailParams;
import com.dcfs.smartaibank.manager.monitor.web.param.AccountRecordParams;
import com.dcfs.smartaibank.manager.monitor.web.param.MonitorRemoteParams;
import com.dcfs.smartaibank.manager.monitor.web.util.remote.MonitorRemoteClientRequest;

/**
 * @author liangchenglong
 * @date 2019/7/29 10:46
 * @since 1.0.0
 */

public interface MonitorRemoteService {

    /**
     *  管理端远程调用
     * @param monitorRemoteConfig 远程调用配置类
     * @param monitorRemoteParams 远程调用页面传入参数类
     * @return
     */
    String remoteManager(MonitorRemoteConfig monitorRemoteConfig,
                              MonitorRemoteParams monitorRemoteParams);

    /**
     * 管理端，客户端 远程调用
     * @param monitorRemoteConfig 远程调用配置类
     * @param monitorRemoteParams 远程调用页面传入参数类
     * @return
     */
    String remoteManagerAndClient(MonitorRemoteConfig monitorRemoteConfig,
                                MonitorRemoteParams monitorRemoteParams);


    /**
     * 手工对账远程调用请求
     * @param url
     * @param accountRecordParams
     * @return
     */
    String remoteAccountRecord(String url, AccountRecordParams accountRecordParams);

    /**
     * 手工对账远程调用请求详情页
     * @param url
     * @param accountDetailParams
     * @return
     */
    String getAccountInfo(String url, AccountDetailParams accountDetailParams);

    /**
     * 确认客户端操作返回信息
     * @param monitorRemote
     * @return
     */
    void getClientReceive(MonitorRemoteClientRequest monitorRemote);
}
