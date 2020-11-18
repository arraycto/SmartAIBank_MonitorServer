package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 外设故障率详情信息实体类
 *
 * @author wangjzm
 * @data 2019/07/23 19:34
 * @since 1.0.0
 */
@ApiModel(value = "PeripheralFaultRateGatherInfo", description = "外设故障率详情信息实体类")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeripheralFaultRateGatherInfo {
    @ApiModelProperty(value = "机具类型")
    private String devClassKey;

    @ApiModelProperty(value = "机具类型名称")
    private String devClassName;

    @ApiModelProperty(value = "机具型号")
    private String devModelKey;

    @ApiModelProperty(value = "机具型号名称")
    private String devModelName;

    @ApiModelProperty(value = "厂商id")
    private String manufacturerId;

    @ApiModelProperty(value = "厂商名称")
    private String manufacturerName;

    @ApiModelProperty(value = "开机时长")
    private Integer startUpTime;

    @ApiModelProperty(value = "外设故障率详情集合")
    List<PeripheralFaultRate> peripheralFaultRates;

}
