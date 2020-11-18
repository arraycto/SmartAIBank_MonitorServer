package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tanchena
 * @date 2019/7/31 10:16
 */
@ApiModel(value = "HistorySimple", description = "表头和信息实体")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class HistoryHeaderAndinfo {

    @ApiModelProperty("设备类型")
    private String deviceClassKey;

    @ApiModelProperty("设备类型名称")
    private String deviceClassName;

    @ApiModelProperty("外设id")
    private String mId;

    @ApiModelProperty("故障时间")
    private Integer normalTime;

    @ApiModelProperty("故障率")
    private Float rate;

}
