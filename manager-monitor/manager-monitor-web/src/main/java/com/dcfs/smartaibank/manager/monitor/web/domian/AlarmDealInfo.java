package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author tanchen1
 * @date 2019/6/20 13:38
 * @since
 */
@ApiModel(value = "AlarmDealInfo", description = "预警处理信息实体")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AlarmDealInfo {

    @ApiModelProperty(value = "主键Id")
    private String seqId;

    @ApiModelProperty(value = "预警流水号")
    private String alarmId;

    @ApiModelProperty(value = "开始时间")
    private Date beginTime;

    @ApiModelProperty(value = "报修时间")
    private Date repairTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "关闭时间")
    private Date closeTime;

    @ApiModelProperty(value = "关闭方式")
    private String closeType;

    @ApiModelProperty(value = "故障原因")
    private String faultReason;

    @ApiModelProperty(value = "用户Id")
    private String userId;

}
