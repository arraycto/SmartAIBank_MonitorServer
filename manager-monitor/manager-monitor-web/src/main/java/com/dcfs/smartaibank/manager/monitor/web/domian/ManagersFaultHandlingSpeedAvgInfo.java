package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 设备管理人员故障处理速度考核平均值信息
 *
 * @author wangjzm
 * @data 2019/07/26 17:24
 * @since 1.0.0
 */
@ApiModel(value = "ManagersFaultHandlingSpeedAvgInfo", description = "设备管理人员故障处理速度考核平均值信息")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ManagersFaultHandlingSpeedAvgInfo {
    @ApiModelProperty(value = "响应时间-均值（分钟/个）")
    private Float avgResponseTime;

    @ApiModelProperty(value = "预警等级处理速度—均值")
    private Float avgSolveTime;
}
