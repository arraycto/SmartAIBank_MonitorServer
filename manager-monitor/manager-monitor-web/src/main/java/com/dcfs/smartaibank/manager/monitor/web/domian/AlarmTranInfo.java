package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tanchen1
 * @date 2019/6/27 13:52
 * @since
 */
@ApiModel(value = "AlarmTranInfo", description = "交易预警信息实体")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AlarmTranInfo {

    @ApiModelProperty(value = "预警级别(1-黄色预警 ，2-橙色预警 ，3-红色预警)")
    private Integer alarmLevel;

    @ApiModelProperty(value = "交易状态(1-正常，2-失败，3-异常)")
    private Integer tranStatus;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "交易类型")
    private String tranType;

    @ApiModelProperty(value = "交易类型名称")
    private String tranTypeName;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "交易流水号")
    private String seqId;

    @ApiModelProperty(value = "交易金额")
    private Double tranAmt;

    @ApiModelProperty(value = "交易卡号")
    private String tranCardNo;

    @ApiModelProperty(value = "交易卡类型")
    private String tranCardType;

    @ApiModelProperty(value = "交易卡类型名称")
    private String tranCardTypeName;

    @ApiModelProperty(value = "预警时间")
    private String alarmDate;

    @ApiModelProperty(value = "设备编号")
    private String modelNo;

    @ApiModelProperty(value = "设备类型")
    private String modelTypeName;

    @ApiModelProperty(value = "所属机构编号")
    private String branchNo;

    @ApiModelProperty(value = "所属机构")
    private String branchName;

    @ApiModelProperty(value = "所在位置")
    private String fieldName;

    @ApiModelProperty(value = "设备厂商")
    private String manufacturerName;

    @ApiModelProperty(value = "处理状态")
    private String alarmStatus;
}
