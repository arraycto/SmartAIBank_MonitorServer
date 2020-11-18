package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 机具总数及分机具运行状态数量及占比实体类
 *
 * @author wangjzm
 * @data 2019/06/14 17:15
 * @since 1.0.0
 */
@ApiModel(value = "DeviceRunningStatusSum", description = "机具总数及分机具运行状态数量及占比实体类")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DeviceRunningStatusSum {
    @ApiModelProperty(value = "设备总数")
    private Integer totalCount;

    @ApiModelProperty(value = "通讯故障设备数量")
    private Integer commFailureCount;

    @ApiModelProperty(value = "外设故障设备数量")
    private Integer peripheralFailureCount;

    @ApiModelProperty(value = "告警设备数量")
    private Integer alarmCount;

    @ApiModelProperty(value = "应用未启动设备数量")
    private Integer appNotStartedCount;

    @ApiModelProperty(value = "正常设备数量")
    private Integer normalCount;

    @ApiModelProperty(value = "未监控设备数量")
    private Integer unmonitoredCount;

    @ApiModelProperty(value = "通讯故障设备数量占比")
    private Float commFailureRate;

    @ApiModelProperty(value = "外设故障设备数量占比")
    private Float peripheralFailureRate;

    @ApiModelProperty(value = "告警设备数量占比")
    private Float alarmRate;

    @ApiModelProperty(value = "应用未启动设备数量占比")
    private Float appNotStartedRate;

    @ApiModelProperty(value = "正常设备数量占比")
    private Float normalRate;

    @ApiModelProperty(value = "未监控设备数量占比")
    private Float unmonitoredRate;
}
