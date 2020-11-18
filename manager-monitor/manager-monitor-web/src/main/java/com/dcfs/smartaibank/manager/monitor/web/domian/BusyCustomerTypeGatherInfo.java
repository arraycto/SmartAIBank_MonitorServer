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
 * @author wangjzm
 * @data 2019/07/19 14:26
 * @since 1.0.0
 */
@ApiModel(value = "BusyCustomerTypeGatherInfo", description = "当前客户类型的分时段繁忙情况、汇总信息、排队等待时长分布")
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusyCustomerTypeGatherInfo {
    @ApiModelProperty(value = "当前客户类型的分时段繁忙情况")
    List<TimePhasedBusinessInfo> timePhasedBusinessInfoList;

    @ApiModelProperty(value = "当前客户类型的排队等待时长分布情况")
    BusyWaitTimeDistribution busyWaitTimeDistribution;

    @ApiModelProperty(value = "当前客户类型的汇总信息情况")
    BusyCustGatherInfoInCustType busyCustGatherInfoInCustType;

    @ApiModelProperty(value = "当前客户类型的可办理业务窗口信息查询")
    List<ManageableWindow> windows;
}
