package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author tanchen1
 * @date 2019/7/2 15:16
 * @since
 */
@ApiModel(value = "AlarmSimpleResult", description = "预警信息实体")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AlarmSimpleResult {

    @ApiModelProperty(value = "低级预警")
    private List<AlarmNum> alarmOne;

    @ApiModelProperty(value = "中级预警")
    private List<AlarmNum> alarmTwo;

    @ApiModelProperty(value = "高级预警")
    private List<AlarmNum> alarmThree;

    @ApiModelProperty(value = "预警信息（简单展示）")
    private List<AlarmInfo> alarmInfo;

}
