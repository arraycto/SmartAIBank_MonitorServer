package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * 分时段交易数量
 *
 * @author wangjzm
 * @data 2019/07/10 18:23
 * @since 1.0.0
 */
@ApiModel(value = "TimePhasedTransCount", description = "分时段交易数量")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TimePhasedTransCount {
    @ApiModelProperty(value = "时间段起始值")
    private String startTime;

    @ApiModelProperty(value = "交易数")
    private String transCount;
}
