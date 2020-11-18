package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author wangtingo
 * @date 2019/7/22 13:38
 * @since
 */
@ApiModel(value = "ManufFaultInfo", description = "厂商故障信息")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ManufFaultInfo {

    @ApiModelProperty(value = "设备厂商Id")
    private String manufId;

    @ApiModelProperty(value = "设备厂商")
    private String manufName;

    @ApiModelProperty(value = "厂商下的设备信息")
    private List<ManufFaultDataInfo> infos;

    @ApiModelProperty(value = "设备故障时间")
    private Integer faultTime;

    @ApiModelProperty(value = "计划开机时间")
    private Integer normalTime;

    @ApiModelProperty(value = "设备故障率")
    private Float devFaultRate;

}
