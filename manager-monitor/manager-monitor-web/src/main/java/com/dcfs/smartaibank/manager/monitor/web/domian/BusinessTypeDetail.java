package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 业务类型详情实体（等待客户数，客户平均等待时间，可办理窗口）
 *
 * @author wangjzm
 * @data 2019/07/16 09:49
 * @since 1.0.0
 */
@ApiModel(value = "BusinessTypeDetail", description = "业务类型详情实体（等待客户数，客户平均等待时间，可办理窗口）")
@ToString
@Getter
@Setter
@NoArgsConstructor
public class BusinessTypeDetail {
    @ApiModelProperty(value = "业务类型ID")
    private String businessTypeId;

    @ApiModelProperty(value = "业务类型名称")
    private String businessTypeName;

    @ApiModelProperty(value = "上级业务类型ID")
    private String parentBusinessTypeId;

    @ApiModelProperty(value = "等待客户数")
    private String waitingCustomerCount;

    @ApiModelProperty(value = "平均排队等待时长")
    private String avgWaitingTime;

    @ApiModelProperty(value = "可办理窗口信息")
    private List<ManageableWindow> windowList;
}
