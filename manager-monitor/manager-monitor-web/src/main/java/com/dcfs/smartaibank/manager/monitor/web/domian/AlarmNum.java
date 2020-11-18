package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tanchen1
 * @date 2019/7/2 14:28
 * @since
 */
@ApiModel(value = "AlarmNum", description = "预警数量实体")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AlarmNum {

    @ApiModelProperty(value = "设备预警数量")
    private Integer deviceNum;

    @ApiModelProperty(value = "交易预警数量")
    private Integer tranNum;

}
