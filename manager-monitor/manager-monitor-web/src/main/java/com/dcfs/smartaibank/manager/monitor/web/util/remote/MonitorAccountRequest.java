package com.dcfs.smartaibank.manager.monitor.web.util.remote;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *  手工对账远程调用请求体
 * @author liangchenglong
 * @data 2019/07/25 17:03
 * @since 1.0.0
 */

@Data
public class MonitorAccountRequest {

    @JsonProperty("SYS_HEAD")
    private SysHead sysHead;

    @JsonProperty("LOCAL_HEAD")
    private LocalHead localHead;

    @JsonProperty("MID")
    private String mid;

    @JsonProperty("START_DATE")
    private String startDate;

    @JsonProperty("END_DATE")
    private String endDate;

    @JsonProperty("USER")
    private String user;

    @JsonProperty("ORGID")
    private String orgId;
}
