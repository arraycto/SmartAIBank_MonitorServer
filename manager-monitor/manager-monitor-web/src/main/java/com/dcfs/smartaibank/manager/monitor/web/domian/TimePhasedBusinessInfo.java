package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wangjzm
 * @data 2019/07/17 20:02
 * @since 1.0.0
 */
@ApiModel(value = "TimePhasedBusinessInfo", description = "分时段繁忙度信息（抽号数量、叫号数量）")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TimePhasedBusinessInfo {
    @ApiModelProperty(value = "时间段末尾值")
    private String endTime;

    @ApiModelProperty(value = "抽号数量")
    private String applyCustomers;

    @ApiModelProperty(value = "叫号数量")
    private String callCustomers;
}
