package com.dcfs.smartaibank.manager.monitor.web.param;

import com.dcfs.smartaibank.springboot.core.validation.annotation.MaxWithConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wangjzm
 * @data 2019/07/26 15:56
 * @since 1.0.0
 */
@ApiModel(value = "ManagersFaultHandlingSpeedParams", description = "设备管理人员故障处理速度考核报表查询参数")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ManagersFaultHandlingSpeedParams {
    @ApiModelProperty(value = "报表周期类型，日、月、季、年报表，[day,month,quarter,year]",
            required = true)
    @NotEmpty(message = "{reportType.not.empty}")
    private String reportType;

    @ApiModelProperty(value = "报表类型，处理时间-1，响应速度-2", required = true)
    @NotEmpty(message = "{dateType.not.empty}")
    private Integer dataType;

    @ApiModelProperty(value = "预警等级")
    private List<String> warningLevel;

    @ApiModelProperty(value = "当前页数", required = true)
    @Min(value = 0, message = "{page.pageNum.min}")
    @NotNull
    private Integer pageNum;

    @ApiModelProperty(value = "每页大小", required = true)
    @MaxWithConfig(value = "${page.maxPageSize}", message = "{page.pageSize.max}")
    @NotNull
    private Integer pageSize;

    @ApiModelProperty(value = "开始日期")
    private String startDate;

    @ApiModelProperty(value = "结束日期")
    private String endDate;

    @ApiModelProperty(value = "月")
    private String month;

    @ApiModelProperty(value = "季")
    private String quarter;

    @ApiModelProperty(value = "年")
    private String year;

    @ApiModelProperty(value = "是否为营业见管理机构", required = true)
    private boolean isManager;

}
