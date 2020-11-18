package com.dcfs.smartaibank.manager.monitor.web.domian;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 监控交易处理列表详情实体类
 *
 * @author wangjzm
 * @data 2019/07/01 15:51
 * @since 1.0.0
 */
@ApiModel(value = "TransDetail", description = "监控交易处理列表详情实体类")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TransDetail {
    @ApiModelProperty(value = "交易状态")
    private String tranStatus;

    @ApiModelProperty(value = "交易状态描述")
    private String tranStatusDesc;

    @ApiModelProperty(value = "错误码")
    private String tranReturnCode;

    @ApiModelProperty(value = "错误码描述")
    private String tranReturnMsg;

    @ApiModelProperty(value = "交易时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date tranData;

    @ApiModelProperty(value = "交易类型")
    private String tranType;

    @ApiModelProperty(value = "交易类型描述")
    private String tranTypeDesc;

    @ApiModelProperty(value = "交易流水号")
    private String tranSequence;

    @ApiModelProperty(value = "设备编号")
    private String deviceId;

    @ApiModelProperty(value = "交易码")
    private String tranCode;

    @ApiModelProperty(value = "设备类型")
    private String deviceClassKey;

    @ApiModelProperty(value = "设备类型名称")
    private String deviceClassName;

    @ApiModelProperty(value = "所属机构")
    private String branchId;

    @ApiModelProperty(value = "所属机构名称")
    private String branchName;

    @ApiModelProperty(value = "交易地点编号")
    private String fieldNo;

    @ApiModelProperty(value = "交易地点名称")
    private String fieldName;
}
