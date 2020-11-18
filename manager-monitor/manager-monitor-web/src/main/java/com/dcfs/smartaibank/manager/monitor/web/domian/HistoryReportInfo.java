package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wangtingo
 * @date 2019/7/11 13:38
 * @since
 */
@ApiModel(value = "HistoryReportInfo", description = "交易对账信息")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class HistoryReportInfo {

    @ApiModelProperty(value = "设备类型key")
    private String toolClassKey;

    @ApiModelProperty(value = "设备类型")
    private String toolClassName;

    @ApiModelProperty(value = "设备型号")
    private String toolModelName;

    @ApiModelProperty(value = "设备厂商Id")
    private String manufId;

    @ApiModelProperty(value = "设备厂商")
    private String manufName;

    @ApiModelProperty(value = "设备故障时间")
    private Integer faultTime;

    @ApiModelProperty(value = "计划开机时间")
    private Integer normalTime;

    @ApiModelProperty(value = "设备故障率")
    private Float devFaultRate;

    @ApiModelProperty(value = "外设类型key")
    private String devClassKey;

}
