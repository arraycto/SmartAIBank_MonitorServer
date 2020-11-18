package com.dcfs.smartaibank.manager.monitor.web.domian;

import com.dcfs.smartaibank.manager.monitor.web.annotation.ExcelAnnotation;
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
@ApiModel(value = "ManagersSolveTimeInfo", description = "设备管理人员故障处理速度考核信息")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ManagersSolveTimeInfo {
    @ApiModelProperty(value = "设备管理员姓名")
    @ExcelAnnotation(header = "人员姓名")
    private String userName;

    @ApiModelProperty(value = "所属机构id")
    private String branchId;

    @ExcelAnnotation(header = "网点名称")
    @ApiModelProperty(value = "所属机构名称")
    private String branchName;

    @ApiModelProperty(value = "预警等级")
    private String warningLevel;

    @ExcelAnnotation(header = "预警等级")
    @ApiModelProperty(value = "预警等级描述")
    private String warningLevelDesc;

    @ApiModelProperty(value = "预警等级处理速度")
    @ExcelAnnotation(header = "处理时间（分钟）")
    private Float solveTime;

    @ExcelAnnotation(header = "处理时间-均值（分钟）")
    @ApiModelProperty(value = "预警等级处理速度—均值")
    private Float avgSolveTime;

    @ExcelAnnotation(header = "均值对比")
    @ApiModelProperty(value = "预警等级处理速度对比结果")
    private String solveTimeCompareResult;

}
