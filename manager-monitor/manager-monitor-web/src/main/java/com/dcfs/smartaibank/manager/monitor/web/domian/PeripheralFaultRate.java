package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 外设故障率详情
 *
 * @author wangjzm
 * @data 2019/07/23 20:21
 * @since 1.0.0
 */
@ApiModel(value = "PeripheralFaultRate", description = "外设故障率详情")
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PeripheralFaultRate {
    @ApiModelProperty(value = "外设模块id")
    private String peripheralId;

    @ApiModelProperty(value = "外设模块名称")
    private String peripheralName;

    @ApiModelProperty(value = "设备故障时间")
    private Integer sumFaultTimeInt;

    @ApiModelProperty(value = "设备故障率")
    private Float faultTimeIntRate;
}
