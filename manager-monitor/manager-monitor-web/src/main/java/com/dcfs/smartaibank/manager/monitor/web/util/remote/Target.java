package com.dcfs.smartaibank.manager.monitor.web.util.remote;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 *  系统类型
 * @author liangchenglong
 * @data 2019/07/25 17:03
 * @since 1.0.0
 */
@Data
public class Target {

    @JsonProperty(value = "TARGET")
    @NotNull
    @ApiModelProperty(value = "机具mac", required = true)
    private String target = "";

    @JsonProperty(value = "DEVCLASSKEY")
    @ApiModelProperty(value = "设备devClassKey", required = true)
    private String devClassKey = "";

    @ApiModelProperty(value = "ip")
    @JsonProperty(value = "IP")
    private String ip = "";
}
