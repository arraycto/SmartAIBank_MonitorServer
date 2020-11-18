package com.dcfs.smartaibank.manager.monitor.web.domian;

import com.dcfs.smartaibank.manager.monitor.web.annotation.ExcelAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tanchena
 * @date 2019/8/5 11:07
 */
@Api(value = "HistoryTransQualityInfo", description = "交易质量报表-结果返回信息实体")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class HistoryTransQualityInfo {


    @ApiModelProperty(value = "网点Id")
    private String branchNo;

    @ExcelAnnotation(header = "网点名称")
    @ApiModelProperty(value = "网点名称")
    private String branchName;

    @ApiModelProperty(value = "业务类型Id")
    private String typeId;

    @ExcelAnnotation(header = "业务类型")
    @ApiModelProperty(value = "业务类型名称")
    private String typeName;

    @ExcelAnnotation(header = "办理成功数量")
    @ApiModelProperty(value = "办理成功数量")
    private Integer dealSuccessCount;

    @ExcelAnnotation(header = "办理成功率")
    @ApiModelProperty(value = "办理成功率")
    private Float dealSuccessRate;

    @ExcelAnnotation(header = "办理成功率-均值")
    @ApiModelProperty(value = "办理成功率-均值")
    private Float dealSuccessAvgRate;

    @ExcelAnnotation(header = "均值对比")
    @ApiModelProperty(value = "均值对比")
    private String desc;

}
