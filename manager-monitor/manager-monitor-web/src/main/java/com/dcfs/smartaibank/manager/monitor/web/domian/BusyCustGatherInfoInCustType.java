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
 * 客户类型看板分客户类型级别的汇总信息（包含等待客户数、等待客户数占比、客户平均等待时长）
 *
 * @author wangjzm
 * @data 2019/07/19 13:46
 * @since 1.0.0
 */
@ApiModel(value = "BusyCustGatherInfoInCustType",
        description = "客户类型看板分客户类型级别的汇总信息（包含等待客户数、等待客户数占比、客户平均等待时长）")
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusyCustGatherInfoInCustType {
    @ApiModelProperty(value = "等待客户数")
    private Integer waitingCustomers;

    @ApiModelProperty(value = "等待客户数占比")
    private Float waitingCustomersRate;

    @ApiModelProperty(value = "客户平均等待时间")
    private Integer avgWaitingTime;
}
