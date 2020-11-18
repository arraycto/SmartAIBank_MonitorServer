package com.dcfs.smartaibank.manager.monitor.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tanchena
 * @date 2019/8/5 10:45
 */
@ApiModel(value = "HistoryTransQualityParams", description = "交易质量报表统计的查询参数")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class HistoryTransQualityParams {

    @ApiModelProperty(value = "机构编号", required = true)
    private String branchNo;

    @ApiModelProperty(value = "报表类型(day-日 month-月 quarter-季 year-年)", required = true)
    private String reportType;

    @ApiModelProperty(value = "当前页数", required = true)
    private Integer pageNum;

    @ApiModelProperty(value = "每页大小", required = true)
    private Integer pageSize;

    @ApiModelProperty(value = "日的开始时间")
    private String reportDateStart;

    @ApiModelProperty(value = "日的截止时间")
    private String reportDateEnd;

    @ApiModelProperty(value = "年份")
    private String reportYear;

    @ApiModelProperty(value = "月份")
    private String reportMonth;

    @ApiModelProperty(value = "季度")
    private String reportQuarter;

    @ApiModelProperty(value = "设备类型")
    private String classKey;

    @ApiModelProperty(value = "业务类型(如果是网点详情的话，该值不需要传参)")
    private String bizType;

}
