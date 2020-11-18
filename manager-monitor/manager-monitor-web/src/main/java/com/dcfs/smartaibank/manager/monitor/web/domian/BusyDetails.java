package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 繁忙度列表详情实体
 *
 * @author wangjzm
 * @data 2019/07/05 10:20
 * @since 1.0.0
 */
@ApiModel(value = "BusyDetails", description = "设备列表页面设备详情实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BusyDetails {
    @ApiModelProperty(value = "机构排名名次")
    private Integer rankNo;

    @ApiModelProperty(value = "机构id")
    private String branchId;

    @ApiModelProperty(value = "机构名称")
    private String branchName;

    @ApiModelProperty(value = "状态")
    private Character status;

    @ApiModelProperty(value = "状态描述")
    private String statusDesc;

    @ApiModelProperty(value = "等待客户数")
    private Integer waitingCustomers;

    @ApiModelProperty(value = "窗口平均等待客户数")
    private Integer avgWindowWaitingCustomers;

    @ApiModelProperty(value = "客户平均等待时间")
    private Integer avgWaitingTime;

    @ApiModelProperty(value = "客户最长等待时间")
    private Integer maxWaitingTime;
}
