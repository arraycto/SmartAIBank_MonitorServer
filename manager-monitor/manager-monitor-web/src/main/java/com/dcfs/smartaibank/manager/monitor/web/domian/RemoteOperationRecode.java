package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * 设备远程操作记录
 *
 * @author wangjzm
 * @data 2019/06/20 10:46
 * @since 1.0.0
 */
@ApiModel(value = "RemoteOperationRecode", description = "设备远程操作记录")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RemoteOperationRecode {

    @ApiModelProperty(value = "设备id")
    private String deviceID;

    @ApiModelProperty(value = "设备类型id")
    private String deviceClassKey;

    @ApiModelProperty(value = "设备类型描述")
    private String deviceClassName;

    @ApiModelProperty(value = "所属机构")
    private String branchId;

    @ApiModelProperty(value = "所属机构名称")
    private String branchName;

    @ApiModelProperty(value = "操作人员id")
    private String operatorId;

    @ApiModelProperty(value = "操作人员姓名")
    private String operatorName;

    @ApiModelProperty(value = "操作指令")
    private String operationInstruction;

    @ApiModelProperty(value = "操作内容")
    private String operationContent;

    @ApiModelProperty(value = "操作结果id")
    private String operationResultId;

    @ApiModelProperty(value = "操作结果描述")
    private String operationResultDesc;

    @ApiModelProperty(value = "操作日期")
    private String operationDate;

    @ApiModelProperty(value = "操作时间")
    private String operationTime;

    @ApiModelProperty(value = "操作结果返回时间")
    private String finishTime;
}
