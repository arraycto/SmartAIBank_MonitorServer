package com.dcfs.smartaibank.manager.monitor.web.util.remote;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 客户端请求类
 * @author liangchenglong
 * @date 2019/7/29 10:46
 * @since 1.0.0
 */

@NoArgsConstructor
@ToString
public class ClientReq {

    private String user;

    private String mac;

    @JsonProperty("orgid")
    private String orgId;

    private String ip;

    @JsonProperty("waittime")
    private String waitTime;

    @JsonProperty("modelclass")
    private String modelClass;

    @JsonProperty("logtype")
    private String logType;

    @JsonProperty("logdate")
    private String logDate;

    /**
     * @return
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user
     * @return
     */
    public ClientReq setUser(String user) {
        this.user = user;
        return this;
    }

    /**
     * @return
     */
    public String getMac() {
        return mac;
    }

    /**
     * @param mac
     * @return
     */
    public ClientReq setMac(String mac) {
        this.mac = mac;
        return this;
    }

    /**
     * @return
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * @param orgId
     * @return
     */
    public ClientReq setOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * @return
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     * @return
     */
    public ClientReq setIp(String ip) {
        this.ip = ip;
        return this;
    }

    /**
     * @return
     */
    public String getWaitTime() {
        return waitTime;
    }

    /**
     * @param waitTime
     * @return
     */
    public ClientReq setWaitTime(String waitTime) {
        this.waitTime = waitTime;
        return this;
    }

    /**
     * @return
     */
    public String getModelClass() {
        return modelClass;
    }

    /**
     * @param modelClass
     * @return
     */
    public ClientReq setModelClass(String modelClass) {
        this.modelClass = modelClass;
        return this;
    }

    /**
     * @return
     */
    public String getLogType() {
        return logType;
    }

    /**
     * @param logType
     * @return
     */
    public ClientReq setLogType(String logType) {
        this.logType = logType;
        return this;
    }

    /**
     * @return
     */
    public String getLogDate() {
        return logDate;
    }

    /**
     * @param logDate
     * @return
     */
    public ClientReq setLogDate(String logDate) {
        this.logDate = logDate;
        return this;
    }
}
