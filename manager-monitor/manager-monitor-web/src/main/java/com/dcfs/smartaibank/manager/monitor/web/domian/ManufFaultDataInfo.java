package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tanchena
 * @date 2019/8/27 14:21
 */
@ApiModel(value = "ManufFaultDataInfo", description = "故障信息")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ManufFaultDataInfo {

    @ApiModelProperty(value = "设备类型KEY")
    private String deviceKey;

    @ApiModelProperty(value = "设备故障时间")
    private Integer faultTime;

    @ApiModelProperty(value = "计划开机时间")
    private Integer normalTime;

    @ApiModelProperty(value = "设备故障率")
    private Float devFaultRate;

}
