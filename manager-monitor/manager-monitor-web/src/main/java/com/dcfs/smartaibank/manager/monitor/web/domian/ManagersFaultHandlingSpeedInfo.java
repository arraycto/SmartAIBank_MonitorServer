package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 设备管理人员故障处理速度考核信息
 *
 * @author wangjzm
 * @data 2019/07/26 17:24
 * @since 1.0.0
 */
@ApiModel(value = "ManagersFaultHandlingSpeedInfo", description = "设备管理人员故障处理速度考核信息")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ManagersFaultHandlingSpeedInfo {
    @ApiModelProperty(value = "设备管理员姓名")
    private String userName;

    @ApiModelProperty(value = "所属机构id")
    private String branchId;

    @ApiModelProperty(value = "所属机构名称")
    private String branchName;

    @ApiModelProperty(value = "预警等级")
    private String warningLevel;

    @ApiModelProperty(value = "预警等级描述")
    private String warningLevelDesc;

    @ApiModelProperty(value = "响应时间（分钟/个）")
    private Float responseTime;

    @ApiModelProperty(value = "响应时间-均值（分钟/个）")
    private Float avgResponseTime;

    @ApiModelProperty(value = "预警等级处理速度")
    private Float solveTime;

    @ApiModelProperty(value = "预警等级处理速度—均值")
    private Float avgSolveTime;

    @ApiModelProperty(value = "预警等级处理速度对比结果")
    private String solveTimeCompareResult;

    @ApiModelProperty(value = "响应速度对比结果")
    private String responseTimeCompareResult;
}
