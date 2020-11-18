package com.dcfs.smartaibank.manager.monitor.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author wangtingo
 * @date 2019/6/25 17:03
 * @since
 */
@ApiModel(value = "AlarmWorkInfo", description = "预警工单登记信息")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AlarmWorkInfo {

    @ApiModelProperty(value = "预警id", required = true)
    private String alarmId;

    @ApiModelProperty(value = "维修人员姓名", required = true)
    private String repairUserName;

    @ApiModelProperty(value = "设备id", required = true)
    private String deviceId;

    @ApiModelProperty(value = "维修人员到达时间", required = true)
    private String receiveTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "报修的的结束时间")
    private String repairEndTime;

    @ApiModelProperty(value = "维修评价", required = true)
    private Integer repairEvaluate;

    @ApiModelProperty(value = "维修备注", required = true)
    private String repairDesc;

    @ApiModelProperty(value = "故障原因", required = true)
    private String faultReason;

    @ApiModelProperty(value = "修复操作--1驱动参数修改2卡钞清理3零件更换4外设更换", required = true)
    private String repairOperate;

    @ApiModelProperty(value = "关闭时间")
    private Date closeTime;

    @ApiModelProperty(value = "关闭方式")
    private String closeType;

    @ApiModelProperty(value = "修复时间")
    private String repairTime;

    @ApiModelProperty(value = "机具名称")
    private String devClassName;

}
