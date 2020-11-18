package com.dcfs.smartaibank.manager.monitor.web.param;

import com.dcfs.smartaibank.springboot.core.validation.annotation.MaxWithConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;

/**
 * @author wangtingo
 * @date 2019/7/11 9:03
 * @since
 */
@ApiModel(value = "HistoryReportParams", description = "查询参数")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class HistoryReportParams {


    @ApiModelProperty(value = "设备类型-字符串数组")
    private String[] devClassKey;

    @ApiModelProperty(value = "厂商-字符串数组")
    private String[] manufId;

    @ApiModelProperty(value = "是否为营业兼管理")
    private Boolean isManager;

    @ApiModelProperty(value = "报表周期，日报表，月报表，季报表，年报表", required = true)
    private String reportType;

    @ApiModelProperty(value = "报表类型", required = true)
    private String dataType;

    @ApiModelProperty(value = "开始日期，日期格式:yyyyMMdd")
    private String startDate;

    @ApiModelProperty(value = "结束日期,日期格式yyyyMMdd")
    private String endDate;

    @ApiModelProperty(value = "月,格式MM")
    private String month;

    @ApiModelProperty(value = "季")
    private String quarter;

    @ApiModelProperty(value = "年")
    private String year;

    @ApiModelProperty(value = "当前页数", required = false)
    @Min(value = 0, message = "{page.pageNum.min}")
    private Integer pageNum;

    @ApiModelProperty(value = "每页大小", required = false)
    @MaxWithConfig(value = "${page.maxPageSize}", message = "{page.pageSize.max}")
    private Integer pageSize;


}
