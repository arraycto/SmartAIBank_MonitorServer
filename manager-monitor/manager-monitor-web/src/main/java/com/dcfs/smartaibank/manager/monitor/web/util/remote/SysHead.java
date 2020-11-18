package com.dcfs.smartaibank.manager.monitor.web.util.remote;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

/**
 *  系统类型
 * @author liangchenglong
 * @data 2019/07/25 17:03
 * @since 1.0.0
 */
@NoArgsConstructor
public class SysHead {
    /**
     * 消息类型
     */
    @JsonProperty(value = "MESSAGE_TYPE")
    private String messageType;

    /**
     * 消息码
     */
    @JsonProperty(value = "MESSAGE_CODE")
    private String messageCode;

    /**
     * 服务码
     */
    @JsonProperty(value = "SERVICE_CODE")
    private String serviceCode = "";

    public String getMessageType() {
        return messageType;
    }

    /**
     * 设置消息类型
     * @param messageType
     * @return
     */
    public SysHead setMessageType(String messageType) {
        if (null != messageType) {
            this.messageType = messageType;
        }
        return this;
    }

    public String getMessageCode() {
        return messageCode;
    }

    /**
     * 设置消息code
     * @param messageCode
     * @return
     */
    public SysHead setMessageCode(String messageCode) {
        if (null != messageCode) {
            this.messageCode = messageCode;
        }
        return this;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    /**
     * 设置服务
     * @param serviceCode
     * @return
     */
    public SysHead setServiceCode(String serviceCode) {
        if (null != serviceCode) {
            this.serviceCode = serviceCode;
        }
        return this;
    }


}
