package com.dcfs.smartaibank.manager.monitor.web.param;

import com.dcfs.smartaibank.springboot.core.validation.annotation.MaxWithConfig;
import io.swagger.annotations.Api;
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
 *  
 *  * @author &何则    
 *  * @date   2020/7/19 14:34  
 *  * @version V1.0.0 
 *  
 */
@Api(value = "WaittingTimebranchParams", description = "监控报表-支行视角客户等候时间数据报表查询参数")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class WaittingTimebranchParams {

    @ApiModelProperty(value = "设备类型", required = true)
    @NotEmpty(message = "{deviceType.not.empty}")
    private String devClassKey;

    @ApiModelProperty(value = "报表周期类型，日、月、季、年报表，[day,month,quarter,year]", required = true)
    @NotEmpty(message = "{reportType.not.empty}")
    private String reportType;

    @ApiModelProperty(value = "厂商id数组")
    private List<String> manufacturerIds;

    @ApiModelProperty(value = "当前页数", required = true)
    @Min(value = 0, message = "{page.pageNum.min}")
    @NotNull
    private Integer pageNum;

    @ApiModelProperty(value = "每页大小", required = true)
    @MaxWithConfig(value = "${page.maxPageSize}", message = "{page.pageSize.max}")
    @NotNull
    private Integer pageSize;

    @ApiModelProperty(value = "开始日期，日期格式yyyyMMdd")
    private String startDate;

    @ApiModelProperty(value = "结束日期，日期格式yyyyMMdd")
    private String endDate;

    @ApiModelProperty(value = "月，格式MM")
    private String month;

    @ApiModelProperty(value = "季,，可传入[1,2,3,4]格式：1")
    private String quarter;

    @ApiModelProperty(value = "年")
    private String year;

    @ApiModelProperty(value = "是否为营业兼管理机构", required = true)
    private boolean manager;
}
