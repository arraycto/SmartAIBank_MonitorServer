package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * @author tanchen1
 * @date 2019/6/20 9:06
 * @since
 */
@ApiModel(value = "AlarmInfo", description = "预警信息实体")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AlarmInfo {

    @ApiModelProperty(value = "预警级别(1-黄色预警 ，2-橙色预警 ，3-红色预警)")
    private Integer alarmLevel;

    @ApiModelProperty(value = "故障描述")
    private String description;

    @ApiModelProperty(value = "设备编号")
    private String modelId;

    @ApiModelProperty(value = "MAC")
    private String mac;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "设备类型key")
    private String devClassKey;

    @ApiModelProperty(value = "设备类型")
    private String modelClassName;

    @ApiModelProperty(value = "机构编号")
    private String branchNo;

    @ApiModelProperty(value = "所属机构名称")
    private String branchName;

    @ApiModelProperty(value = "所在位置")
    private String fieldName;

    @ApiModelProperty(value = "设备厂商")
    private String manufName;

    @ApiModelProperty(value = "处理状态(1-未处理，2-处理中，3-已报修，4-已解除，5-已关闭)")
    private Integer alarmDealStatus;

    @ApiModelProperty(value = "预警流水号")
    private String id;

    @ApiModelProperty(value = "预警时间")
    private String alarmDate;

    @ApiModelProperty(value = "处理人编号")
    private String userId;

    @ApiModelProperty(value = "处理人姓名")
    private String userName;

    @ApiModelProperty(value = "报修时间")
    private String repairTime;

    @ApiModelProperty(value = "恢复时间")
    private String recoverTime;

    @ApiModelProperty(value = "挂起时间")
    private String startTime;

    @ApiModelProperty(value = "预警类型(device-设备,tran-交易)")
    private String warnType;

    @ApiModelProperty(value = "关闭方式(1-自动关闭,2-登记关闭)")
    private String closeType;

    @ApiModelProperty(value = "故障外设名称")
    private String devModelName;

}
