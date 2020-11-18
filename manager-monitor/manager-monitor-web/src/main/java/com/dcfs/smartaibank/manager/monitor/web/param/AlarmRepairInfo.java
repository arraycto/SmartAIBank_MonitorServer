package com.dcfs.smartaibank.manager.monitor.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wangtingo
 * @date 2019/6/25 17:03
 * @since
 */
@ApiModel(value = "AlarmRepairInfo", description = "预警报修信息")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AlarmRepairInfo {

    @ApiModelProperty(value = "预警id", required = true)
    private String alarmId;

    @ApiModelProperty(value = "维修人员姓名", required = true)
    private String repairUserName;

    @ApiModelProperty(value = "设备id", required = true)
    private String deviceId;

    @ApiModelProperty(value = "报修id", readOnly = true)
    private String repairId;
}
