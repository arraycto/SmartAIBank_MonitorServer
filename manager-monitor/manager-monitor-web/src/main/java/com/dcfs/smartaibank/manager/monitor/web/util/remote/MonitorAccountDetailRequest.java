package com.dcfs.smartaibank.manager.monitor.web.util.remote;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *  手工对账详情远程调用请求体
 * @author liangchenglong
 * @data 2019/07/25 17:03
 * @since 1.0.0
 */

@Data
public class MonitorAccountDetailRequest {

    @JsonProperty("SYS_HEAD")
    private SysHead sysHead;

    @JsonProperty("LOCAL_HEAD")
    private LocalHead localHead = new LocalHead();

    @JsonProperty("ORGID")
    private String orgId;

    @JsonProperty("USER")
    private String user;

    @JsonProperty("TARGET")
    private String target;

    @JsonProperty("PERIOD_ID")
    private String periodId;

    @JsonProperty("BUS_TYPE")
    private String busType;

}
