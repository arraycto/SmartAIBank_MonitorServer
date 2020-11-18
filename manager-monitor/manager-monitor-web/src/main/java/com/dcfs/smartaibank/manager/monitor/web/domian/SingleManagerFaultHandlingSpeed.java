package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 单个设备管理人员故障处理速度考核图表信息
 *
 * @author wangjzm
 * @data 2019/07/26 17:24
 * @since 1.0.0
 */
@ApiModel(value = "SingleManagerFaultHandlingSpeed", description = "单个设备管理人员故障处理速度考核图表信息")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SingleManagerFaultHandlingSpeed {
    @ApiModelProperty(value = "设备管理员id")
    private String userId;

    @ApiModelProperty(value = "设备管理员姓名")
    private String userName;

    @ApiModelProperty(value = "响应时间（分钟/个）")
    private Float responseTime;

    @ApiModelProperty(value = "预警等级处理速度")
    private Float solveTime;
}
