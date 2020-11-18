package com.dcfs.smartaibank.manager.monitor.web.util.remote;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

/**
 * 管理端，客户端，请求消息体对象，json格式如下
 *
 * @author liangchenglong
 * @data 2019/07/25 17:03
 * @since 1.0.0
 */

@NoArgsConstructor
public class MonitorRemoteRequest {

    /**
     * 系统头信息
     */
    @JsonProperty(value = "SYS_HEAD")
    private SysHead sysHead = new SysHead();

    /**
     * 本地头信息
     */
    @JsonProperty(value = "LOCAL_HEAD")
    private LocalHead localHead = new LocalHead();

    /**
     * 系统的mac地址
     * 类型不确定
     */
    @JsonProperty(value = "TARGET")
    private Object target;

    /**
     * 机构编号
     */
    @JsonProperty(value = "ORGID")
    private String orgId = "";

    /**
     * 用户名
     */
    @JsonProperty(value = "USER")
    private String user = "";

    /**
     * bus类型
     */
    @JsonProperty(value = "BUS_TYPE")
    private String busType = "";

    /**
     * 服务重启等待时间，例如：5分钟，30分钟，一小时
     */
    @JsonProperty(value = "WAITTIME")
    private String waitTime = "";

    /**
     * 刷新硬件状态
     */
    @JsonProperty(value = "MODELCLASS")
    private String modelClass = "";

    /**
     * 日志类型
     */
    @JsonProperty(value = "LOGTYPE")
    private String logType = "";


    /**
     * 日志生成日期
     */
    @JsonProperty(value = "LOGDATE")
    private String logDate = "";


    public SysHead getSysHead() {
        return sysHead;
    }

    public void setSysHead(SysHead sysHead) {
        this.sysHead = sysHead;
    }

    public LocalHead getLocalHead() {
        return localHead;
    }

    public void setLocalHead(LocalHead localHead) {
        this.localHead = localHead;
    }

    public Object getTarget() {
        return target;
    }

    /**
     * @param target
     * @return
     */
    public MonitorRemoteRequest setTarget(Object target) {
        if (null != target) {
            this.target = target;
        }
        return this;
    }


    public String getOrgId() {
        return orgId;
    }

    /**
     * @param orgId
     * @return
     */
    public MonitorRemoteRequest setOrgId(String orgId) {
        if (null != orgId) {
            this.orgId = orgId;
        }
        return this;
    }

    public String getUser() {
        return user;
    }

    /**
     * @param user
     * @return
     */
    public MonitorRemoteRequest setUser(String user) {
        if (null != user) {
            this.user = user;
        }
        return this;
    }

    public String getBusType() {
        return busType;
    }

    /**
     * 设置busType
     * @param busType
     * @return
     */
    public MonitorRemoteRequest setBusType(String busType) {
        if (null != busType) {
            this.busType = busType;
        }
        return this;
    }

    public String getWaitTime() {
        return waitTime;
    }

    /**
     * 设置等待时间
     * @param waitTime
     * @return
     */
    public MonitorRemoteRequest setWaitTime(String waitTime) {
        if (null != waitTime) {
            this.waitTime = waitTime;
        }
        return this;
    }

    public String getModelClass() {
        return modelClass;
    }

    /**
     * 设置设备
     * @param modelClass
     * @return
     */
    public MonitorRemoteRequest setModelClass(String modelClass) {
        if (null != modelClass) {
            this.modelClass = modelClass;
        }
        return this;
    }

    public String getLogType() {
        return logType;
    }

    /**
     * 设置日志类型
     * @param logType
     * @return
     */
    public MonitorRemoteRequest setLogType(String logType) {
        if (null != logType) {
            this.logType = logType;
        }
        return this;
    }

    public String getLogDate() {
        return logDate;
    }

    /**
     * 设置日期
     * @param logDate
     * @return
     */
    public MonitorRemoteRequest setLogDate(String logDate) {
        if (null != logDate) {
            this.logDate = logDate;
        }
        return this;
    }
}
