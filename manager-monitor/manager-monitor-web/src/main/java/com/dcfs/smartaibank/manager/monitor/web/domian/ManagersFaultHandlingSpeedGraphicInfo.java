package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 设备管理人员故障处理速度考核图表信息
 *
 * @author wangjzm
 * @data 2019/07/26 17:24
 * @since 1.0.0
 */
@ApiModel(value = "ManagersFaultHandlingSpeedGraphicInfo", description = "设备管理人员故障处理速度考核图表信息")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ManagersFaultHandlingSpeedGraphicInfo {
    @ApiModelProperty(value = "预警等级")
    private String warningLevel;

    @ApiModelProperty(value = "预警等级描述")
    private String warningLevelDesc;

    @ApiModelProperty(value = "预警等级描述")
    private List<SingleManagerFaultHandlingSpeed> singleManagerFaultHandlingSpeedList;
}
