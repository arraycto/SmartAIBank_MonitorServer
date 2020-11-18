package com.dcfs.smartaibank.manager.monitor.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 设备运行查询条件实体类
 *
 * @author wangjzm
 * @data 2019/06/14 18:03
 * @since 1.0.0
 */
@ApiModel(value = "DeviceRunningQueryParams", description = "设备运行查询条件实体类")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DeviceRunningQueryParams {

    @ApiModelProperty(value = "设备类型")
    private String deviceModelKey;

    @ApiModelProperty(value = "设备型号")
    private String deviceClassKey;

    @ApiModelProperty(value = "设备id")
    private String deviceId;

    @ApiModelProperty(value = "设备厂商")
    private String manufacturerId;

    @ApiModelProperty(value = "机具状态，0-未监控，1-正常，2-应用未启动，3-告警，4-外设故障，5-通讯故障")
    private Integer deviceStatus;

}
