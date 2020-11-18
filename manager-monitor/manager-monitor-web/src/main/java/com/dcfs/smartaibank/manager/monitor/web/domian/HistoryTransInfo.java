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
 * @author wangtingo
 * @date 2019/7/11 13:38
 * @since
 */
@ApiModel(value = "HistoryTransInfo", description = "历史交易信息")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class HistoryTransInfo {

    @ApiModelProperty(value = "交易流水号")
    private String seqNo;

    @ApiModelProperty(value = "交易状态")
    private String tranStatus;

    @ApiModelProperty(value = "交易类型")
    private String tranName;

    @ApiModelProperty(value = "交易时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date tranDate;

    @ApiModelProperty(value = "交易地点")
    private String fieldName;

    @ApiModelProperty(value = "设备Id")
    private String deviceId;

    @ApiModelProperty(value = "设备类型")
    private String devClassName;

    @ApiModelProperty(value = "设备归属机构")
    private String branchName;

    @ApiModelProperty(value = "交易金额")
    private Integer tranAmt;

    @ApiModelProperty(value = "交易卡号")
    private String tranCardNo;

    @ApiModelProperty(value = "卡类型")
    private String tranCardType;

    @ApiModelProperty(value = "错误码")
    private String tranRetCode;

    @ApiModelProperty(value = "错误码描述")
    private String tranRetDesc;

}
