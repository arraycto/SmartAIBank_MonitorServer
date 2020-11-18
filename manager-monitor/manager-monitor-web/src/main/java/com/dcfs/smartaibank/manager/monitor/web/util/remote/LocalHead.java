package com.dcfs.smartaibank.manager.monitor.web.util.remote;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *  本地头消息
 * @author liangchenglong
 * @data 2019/07/25 17:03
 * @since 1.0.0
 */

@Data
@NoArgsConstructor
public class LocalHead {

    /**
     * 本地操作角标
     */
    @JsonProperty(value = "SERVICE_INDEX")
    private String serviceIndex = "";

    /**
     * 本地操作名称
     */
    @JsonProperty(value = "SYSTEM_NAME")
    private String systemName = "SMART_OPERATOR";

}
