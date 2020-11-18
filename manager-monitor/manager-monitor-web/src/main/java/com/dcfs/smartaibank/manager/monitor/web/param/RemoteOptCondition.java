package com.dcfs.smartaibank.manager.monitor.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 设备远程操作记录查询条件
 *
 * @author wangjzm
 * @data 2019/0620 13:26
 * @since 1.0.0
 */
@ApiModel(value = "RemoteOptCondition", description = "设备远程操作记录查询条件")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RemoteOptCondition {
    @ApiModelProperty(value = "设备id")
    private String deviceID;

    @ApiModelProperty(value = "设备类型id")
    private String deviceClassKey;

    @ApiModelProperty(value = "所属机构")
    private String branchId;

    @ApiModelProperty(value = "操作人员id")
    private String operatorId;

}
