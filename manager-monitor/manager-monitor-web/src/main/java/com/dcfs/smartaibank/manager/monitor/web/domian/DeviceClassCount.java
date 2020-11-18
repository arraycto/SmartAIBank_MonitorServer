package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 设备类型统计数量实体
 *
 * @author wangtingo
 * @data 2019/06/21 11:10
 * @since 1.0.0
 */
@ApiModel(value = "DeviceClassCount", description = "设备类型统计数量实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DeviceClassCount {
    @ApiModelProperty(value = "设备总数")
    private Integer sumCount;

    @ApiModelProperty(value = "填单机个数")
    private Integer fillerCount;

    @ApiModelProperty(value = "排队机个数")
    private Integer queueCount;

    @ApiModelProperty(value = "atm个数")
    private Integer atmCount;

    @ApiModelProperty(value = "stm个数")
    private Integer stmCount;

    @ApiModelProperty(value = "回单机个数")
    private Integer bsrCount;

}
