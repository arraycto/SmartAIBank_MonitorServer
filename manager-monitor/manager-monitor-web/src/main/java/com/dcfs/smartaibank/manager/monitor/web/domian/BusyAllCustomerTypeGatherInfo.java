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
 * 所有客户类型的分时段繁忙情况、汇总信息、排队等待时长分布
 *
 * @author wangjzm
 * @data 2019/07/19 15:00
 * @since 1.0.0
 */
@ApiModel(value = "BusyAllCustomerTypeGatherInfo", description = "所有客户类型的分时段繁忙情况、汇总信息、排队等待时长分布")
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusyAllCustomerTypeGatherInfo {
    @ApiModelProperty(value = "个人普通汇总数据")
    BusyCustomerTypeGatherInfo personalCustomGatherInfo;

    @ApiModelProperty(value = "个人VIP汇总数据")
    BusyCustomerTypeGatherInfo personalVIPGatherInfo;

    @ApiModelProperty(value = "公司客户汇总数据")
    BusyCustomerTypeGatherInfo companyGatherInfo;
}
