package com.dcfs.smartaibank.manager.monitor.web.service.impl;

import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.manager.monitor.web.config.MonitorRemoteConfig;
import com.dcfs.smartaibank.manager.monitor.web.constance.Constance;
import com.dcfs.smartaibank.manager.monitor.web.dao.MonitorRemoteDao;
import com.dcfs.smartaibank.manager.monitor.web.domian.RemoteControlLog;
import com.dcfs.smartaibank.manager.monitor.web.enums.ClientCodeEnum;
import com.dcfs.smartaibank.manager.monitor.web.enums.RemoteLogEnum;
import com.dcfs.smartaibank.manager.monitor.web.enums.RemoteTypeEnum;
import com.dcfs.smartaibank.manager.monitor.web.param.AccountDetailParams;
import com.dcfs.smartaibank.manager.monitor.web.param.AccountRecordParams;
import com.dcfs.smartaibank.manager.monitor.web.param.MonitorRemoteParams;
import com.dcfs.smartaibank.manager.monitor.web.service.MonitorRemoteService;
import com.dcfs.smartaibank.manager.monitor.web.service.task.MonitorRemoteAsyncTask;
import com.dcfs.smartaibank.manager.monitor.web.util.DateUtil;
import com.dcfs.smartaibank.manager.monitor.web.util.remote.ClientBody;
import com.dcfs.smartaibank.manager.monitor.web.util.remote.ClientHeader;
import com.dcfs.smartaibank.manager.monitor.web.util.remote.ClientReq;
import com.dcfs.smartaibank.manager.monitor.web.util.remote.ClientRes;
import com.dcfs.smartaibank.manager.monitor.web.util.remote.ClientRet;
import com.dcfs.smartaibank.manager.monitor.web.util.remote.LocalHead;
import com.dcfs.smartaibank.manager.monitor.web.util.remote.MonitorAccountDetailRequest;
import com.dcfs.smartaibank.manager.monitor.web.util.remote.MonitorAccountRequest;
import com.dcfs.smartaibank.manager.monitor.web.util.remote.MonitorRemoteClientRequest;
import com.dcfs.smartaibank.manager.monitor.web.util.remote.MonitorRemoteRequest;
import com.dcfs.smartaibank.manager.monitor.web.util.remote.SysHead;
import com.dcfs.smartaibank.manager.monitor.web.util.remote.Target;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author liangchenglong
 * @date 2019/7/29 10:46
 * @since 1.0.0
 */

@Service
@Slf4j
public class MonitorRemoteServiceImpl implements MonitorRemoteService {

    @Autowired
    private MonitorRemoteDao monitorRemoteDao;

    @Autowired
    private MonitorRemoteAsyncTask monitorRemoteAsyncTask;

    /**
     * 管理端远程调用
     *
     * @param monitorRemoteConfig
     * @param monitorRemoteParams
     * @throws JsonProcessingException
     */
    @Override
    public String remoteManager(MonitorRemoteConfig monitorRemoteConfig, MonitorRemoteParams monitorRemoteParams) {
        MonitorRemoteRequest monitorRemoteRequest
                = getManagerRequest(monitorRemoteParams, monitorRemoteParams.getIsBatch());
        //post请求发送
        try {
            Future<String> result =
                    monitorRemoteAsyncTask.managerTask(monitorRemoteConfig.getUrl(), monitorRemoteRequest);
            String response = result.get(Constance.FUTURE_TIME, TimeUnit.MINUTES);
            return response;
        } catch (Exception e) {
            throw new BusinessException("设备运行请求第三方管理端远程调用异常:", e);
        }
    }

    /**
     * 管理端、第三方客户端 远程调用
     *
     * @param monitorRemoteConfig
     * @param monitorRemoteParams
     */
    @Override
    public String remoteManagerAndClient(MonitorRemoteConfig monitorRemoteConfig,
                                         MonitorRemoteParams monitorRemoteParams) {
        // 调用第三方系统，只有单次，没有批量请求操作
        if (!monitorRemoteParams.getIsBatch()) {
            Target target = monitorRemoteParams.getTarget().get(0);
            String classDeviceKey = target.getDevClassKey();
            String mac = target.getTarget();
            String ip = target.getIp();
            try {
                if (monitorRemoteConfig.getDevice().contains(classDeviceKey)) {
                    return this.sendToClient(monitorRemoteParams, monitorRemoteConfig, mac, ip);
                } else {
                    //调用管理端
                    MonitorRemoteRequest monitorRemoteRequest
                            = getManagerRequest(monitorRemoteParams, false);
                    //发送请求
                    Future<String> result
                            = monitorRemoteAsyncTask.managerTask(monitorRemoteConfig.getUrl(), monitorRemoteRequest);
                    return result.get(Constance.FUTURE_TIME, TimeUnit.MINUTES);
                }
            } catch (Exception e) {
                throw new BusinessException("remote.access.error", e);
            }
        } else {
            // 如果是批量操作，首先需要区分发往管理端的设备和发往三方系统的设备
            List<Target> targets = monitorRemoteParams.getTarget();
            List<Target> toManagerTargets = new ArrayList<>(targets.size());
            List<Target> toClientTargets = new ArrayList<>(targets.size());
            targets.parallelStream().forEach(target -> {
                if (monitorRemoteConfig.getDevice().contains(target.getDevClassKey())) {
                    toClientTargets.add(target);
                } else {
                    toManagerTargets.add(target);
                }
            });
            MonitorRemoteParams toClientParams = this.createParamsBean(monitorRemoteParams, toClientTargets);
            MonitorRemoteParams toManagerParams = this.createParamsBean(monitorRemoteParams, toManagerTargets);
            List<String> list = Collections.synchronizedList(new ArrayList<>());
            if (toClientTargets.size() > 0) {
                // 定义变量，存储远程操作指令发送失败的设备信息
                toClientTargets.parallelStream().forEach(toClientTarget -> {
                    try {
                        this.sendToClient(toClientParams, monitorRemoteConfig,
                                toClientTarget.getTarget(), toClientTarget.getIp());
                    } catch (Exception e) {
                        log.warn("批量远程操作--往{}发送远程操作指令失败", toClientTarget.getIp());
                        list.add(toClientTarget.getIp());
                    }
                });
            }
            if (toManagerTargets.size() > 0) {
                try {
                    MonitorRemoteRequest monitorRemoteRequest
                            = getManagerRequest(toManagerParams, true);
                    //发送请求
                    Future<String> result = monitorRemoteAsyncTask.managerTask(monitorRemoteConfig.getUrl(),
                            monitorRemoteRequest);
                    return result.get(Constance.FUTURE_TIME, TimeUnit.MINUTES);
                } catch (Exception e) {
                    log.warn("批量远程操作--往管理端发送远程操作指令失败");
                    list.addAll(toManagerTargets.parallelStream().map(Target::getIp).collect(Collectors.toList()));
                }
            }
            if (list.size() > 0) {
                StringBuilder str = new StringBuilder();
                list.forEach(s -> str.append("[" + s + "]"));
                throw new BusinessException("remote.batch.operation.err",
                        "设备批量远程操作部分执行失败，" + str.toString());
            }
            return getSuccessMonitorRemoteClientRequestString();
        }
    }

    private String getSuccessMonitorRemoteClientRequestString() {
        ClientRet clientRet = new ClientRet();
        clientRet.setCode(Constance.CLIENT_CODE);
        MonitorRemoteClientRequest request =
                new MonitorRemoteClientRequest(new ClientHeader().setRet(clientRet), null);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            log.error("远程操作响应信息序列化失败", e);
            throw new BusinessException("request.serialize.failure", e);
        }
    }

    private MonitorRemoteParams createParamsBean(MonitorRemoteParams monitorRemoteParams, List<Target> targets) {
        return MonitorRemoteParams.builder()
                .busType(monitorRemoteParams.getBusType())
                .isBatch(monitorRemoteParams.getIsBatch())
                .logDate(monitorRemoteParams.getLogDate())
                .logType(monitorRemoteParams.getLogType())
                .modelClass(monitorRemoteParams.getModelClass())
                .orgId(monitorRemoteParams.getOrgId())
                .rc(monitorRemoteParams.getRc())
                .target(targets)
                .user(monitorRemoteParams.getUser())
                .waitTime(monitorRemoteParams.getWaitTime())
                .build();
    }

    private String sendToClient(MonitorRemoteParams monitorRemoteParams,
                                MonitorRemoteConfig monitorRemoteConfig,
                                String mac,
                                String ip) throws Exception {
        MonitorRemoteClientRequest monitorRemoteClientRequest
                = getMonitorRemoteClientRequestByMacAndIP(monitorRemoteParams, mac, ip);
        //插入操作日志
        insetRemoteClientLog(monitorRemoteClientRequest);
        String url = new StringBuilder("http://")
                .append(ip)
                .append(":")
                .append(monitorRemoteConfig.getPort())
                .append("/message").toString();
        Future<String> result = monitorRemoteAsyncTask.clientTask(url, monitorRemoteClientRequest);
        String requestResult = result.get(Constance.FUTURE_TIME, TimeUnit.MINUTES);
        //修改日志操作
        updateRemoteClientLog(requestResult);
        return requestResult;
    }

    @Override
    public String remoteAccountRecord(String url, AccountRecordParams accountRecordParams) {
        try {
            MonitorAccountRequest mar = getMonitorAccountRequest(accountRecordParams);
            Future<String> result = monitorRemoteAsyncTask.managerTask(url, mar);
            return result.get(Constance.FUTURE_TIME, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new BusinessException("manual.reconciliation.remote.call.error", e);
        }
    }

    /**
     * 手工对账详情
     *
     * @param url                 url
     * @param accountDetailParams 参数
     * @return 返回远程请求结果
     */
    @Override
    public String getAccountInfo(String url, AccountDetailParams accountDetailParams) {
        try {
            MonitorAccountDetailRequest monitorAccountDetailRequest =
                    new MonitorAccountDetailRequest();
            monitorAccountDetailRequest.setSysHead(getSysHead(RemoteTypeEnum.RC015.name()));
            monitorAccountDetailRequest.setLocalHead(new LocalHead());
            monitorAccountDetailRequest.setOrgId(accountDetailParams.getOrgId());
            monitorAccountDetailRequest.setUser(accountDetailParams.getUser());
            monitorAccountDetailRequest.setTarget(accountDetailParams.getTarget());
            monitorAccountDetailRequest.setPeriodId(accountDetailParams.getPeriodId());
            monitorAccountDetailRequest.setBusType(accountDetailParams.getBusType());
            Future<String> result = monitorRemoteAsyncTask.managerTask(url, monitorAccountDetailRequest);
            return result.get(Constance.FUTURE_TIME, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new BusinessException("manual.reconciliation.remote.call.error", e);
        }
    }

    /**
     * 三方机具操作完成返回结果封装
     *
     * @param monitorRemote
     */
    @Override
    public void getClientReceive(MonitorRemoteClientRequest monitorRemote) {
        if (!Constance.CLIENT_CODE.equals(monitorRemote.getBody().getRes().getCode())) {
            RemoteControlLog remoteControlLog
                    = setRemoteControlLog(monitorRemote, RemoteLogEnum.FAIL.getCode());
            monitorRemoteDao.updateLog(remoteControlLog);
        } else {
            RemoteControlLog remoteControlLog
                    = setRemoteControlLog(monitorRemote, RemoteLogEnum.SUCCESS.getCode());
            monitorRemoteDao.updateLog(remoteControlLog);
        }
    }

    /**
     * 手工对账远程请求参数封装
     *
     * @param accountRecordParams 页面参数封装类
     * @return 返回远程结果
     */
    private MonitorAccountRequest getMonitorAccountRequest(AccountRecordParams accountRecordParams) {
        MonitorAccountRequest mar = new MonitorAccountRequest();
        mar.setLocalHead(new LocalHead());
        mar.setSysHead(getSysHead(RemoteTypeEnum.RC016.name()));
        mar.setMid(accountRecordParams.getMid());
        mar.setStartDate(accountRecordParams.getStartDate());
        mar.setEndDate(accountRecordParams.getEndDate());
        mar.setUser(accountRecordParams.getUser());
        mar.setOrgId(accountRecordParams.getOrgId());
        return mar;
    }

    /**
     * 远程操作日志入库操作
     *
     * @param mrc 页面参数封装
     * @return
     */
    public Integer insetRemoteClientLog(MonitorRemoteClientRequest mrc) {
        RemoteControlLog remoteControlLog
                = setRemoteControlLog(mrc, RemoteLogEnum.SEND.getCode());
        if (mrc.getHeader().getName().equals(ClientCodeEnum.RC009.getName())) {
            String logType = mrc.getBody().getReq().getLogType();
            String logDate = mrc.getBody().getReq().getLogDate();
            if (Constance.APP_LOG.equals(logType)) {
                remoteControlLog.setControlMsg("GETLOG.BUSI." + logDate);
            } else if (Constance.APP_DEVICE.equals(logType)) {
                remoteControlLog.setControlMsg("GETLOG.DRIVEROPERATOR." + logDate);
            } else {
                remoteControlLog.setControlMsg("GETLOG.PLATFORM." + logDate);
            }
        } else if (mrc.getHeader().getName().equals(ClientCodeEnum.RC008.getName())) {
            remoteControlLog.setControlMsg("GETPIC.SCREENSHOT");
        } else {
            remoteControlLog.setControlMsg(mrc.getHeader().getName());
        }
        return monitorRemoteDao.insertLog(remoteControlLog);
    }

    /**
     * 设置远程操作日志属性
     *
     * @param mrc
     * @param status
     * @return
     */
    public RemoteControlLog setRemoteControlLog(MonitorRemoteClientRequest mrc, String status) {
        RemoteControlLog remoteControlLog = new RemoteControlLog();
        remoteControlLog.setSeq(mrc.getHeader().getSeq());
        remoteControlLog.setStatus(status);
        remoteControlLog.setOrgId(mrc.getBody().getReq().getOrgId());
        remoteControlLog.setUserId(mrc.getBody().getReq().getUser());
        remoteControlLog.setMac(mrc.getBody().getReq().getMac());
        LocalDateTime parse = LocalDateTime.parse(mrc.getHeader().getDate(),
                DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss.SSS"));
        remoteControlLog.setBeginDate(parse.toLocalDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        remoteControlLog.setBeginTime(parse.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        return remoteControlLog;
    }

    private void updateRemoteClientLog(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MonitorRemoteClientRequest mrc
                    = objectMapper.readValue(responseBody, MonitorRemoteClientRequest.class);
            if (Constance.CLIENT_CODE.equals(mrc.getHeader().getRet().getCode())) {
                //更新成功操作日志
                RemoteControlLog remoteControlLog
                        = setRemoteControlLog(mrc, RemoteLogEnum.RECEIVE.getCode());
                monitorRemoteDao.updateLog(remoteControlLog);
            } else {
                log.info("客户端调用失败错误信息:{},{}",
                        mrc.getBody().getRes().getCode(), mrc.getBody().getRes().getCode());
            }
        } catch (Exception e) {
            throw new BusinessException("string.conversion.json.error", e);
        }

    }

    /**
     * 封装三方客户端请求对象
     *
     * @param monitorRemoteParams 远程调用对象封装
     * @param mac                 mac地址
     * @param ip                  ip地址
     * @return 三方客户端请求对象
     */
    public MonitorRemoteClientRequest getMonitorRemoteClientRequestByMacAndIP(MonitorRemoteParams monitorRemoteParams,
                                                                              String mac,
                                                                              String ip) {
        ClientHeader clientHeader = new ClientHeader();
        String name = ClientCodeEnum.getClientCodeEnumByCode(monitorRemoteParams.getRc()).getName();
        String data = DateUtil.getDate("yyyyMMdd HH:mm:ss.SSS");
        String logSeq = getSeq();
        ClientReq clientReq = new ClientReq();
        clientReq.setUser(monitorRemoteParams.getUser())
                .setMac(mac).setOrgId(monitorRemoteParams.getOrgId())
                .setIp(ip).setWaitTime(monitorRemoteParams.getWaitTime())
                .setModelClass(monitorRemoteParams.getModelClass())
                .setLogType(monitorRemoteParams.getLogType())
                .setLogDate(monitorRemoteParams.getLogDate());
        ClientRes clientRes = new ClientRes();
        ClientBody clientBody = new ClientBody(clientReq, clientRes);
        clientHeader.setName(name).setDate(data).setSeq(logSeq);
        MonitorRemoteClientRequest monitorRemoteClientRequest
                = new MonitorRemoteClientRequest(clientHeader, clientBody);
        return monitorRemoteClientRequest;
    }

    /**
     * 使用同步锁保证查询序列每次都不一样
     *
     * @return
     */
    private String getSeq() {
        Lock lock = new ReentrantLock();
        lock.lock();
        String logSeq = monitorRemoteDao.getRemoteOrder();
        lock.unlock();
        return logSeq;
    }

    /**
     * 获取管理端请求参数对象
     *
     * @param monitorRemoteParams 远程调用参数封装
     * @param isBatch             是否批量
     * @return
     */
    public MonitorRemoteRequest getManagerRequest(MonitorRemoteParams monitorRemoteParams, Boolean isBatch) {
        MonitorRemoteRequest monitorRemoteRequest
                = new MonitorRemoteRequest();
        String rc = monitorRemoteParams.getRc();
        //设置sysHead
        monitorRemoteRequest.setSysHead(getSysHead(rc));
        //设置机具参数
        if (isBatch) {
            monitorRemoteRequest.setTarget(monitorRemoteParams.getTarget());
        } else {
            monitorRemoteRequest.setTarget(monitorRemoteParams.getTarget().get(0).getTarget());
        }
        monitorRemoteRequest.setOrgId(monitorRemoteParams.getOrgId())
                .setUser(monitorRemoteParams.getUser())
                .setBusType(monitorRemoteParams.getBusType())
                .setWaitTime(monitorRemoteParams.getWaitTime())
                .setModelClass(monitorRemoteParams.getModelClass())
                .setLogDate(monitorRemoteParams.getLogDate())
                .setLogType(monitorRemoteParams.getLogType());
        return monitorRemoteRequest;
    }

    /**
     * 通过服务定位SysHead
     *
     * @param rc 服务码表
     * @return SysHead实体
     */
    private SysHead getSysHead(String rc) {
        RemoteTypeEnum remoteTypeEnum = RemoteTypeEnum.getRemoteTypeByName(rc);
        SysHead sysHead = new SysHead();
        sysHead.setMessageType(remoteTypeEnum.getMt())
                .setMessageCode(remoteTypeEnum.getMc())
                .setServiceCode(remoteTypeEnum.getSc());
        return sysHead;
    }
}
