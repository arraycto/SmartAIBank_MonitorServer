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
@ApiModel(value = "HistoryTransParams", description = "查询参数")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class HistoryTransParams {

    @ApiModelProperty(value = "设备类型")
    private String devClassKey;

    @ApiModelProperty(value = "设备Id")
    private String deviceId;

    @ApiModelProperty(value = "开始日期，格式为yyyy-mm-dd", required = true)
    private String startDate;

    @ApiModelProperty(value = "结束日期，格式为yyyy-mm-dd", required = true)
    private String endDate;

    @ApiModelProperty(value = "当前页数", required = true)
    @Min(value = 0, message = "{page.pageNum.min}")
    private Integer pageNum;

    @ApiModelProperty(value = "每页大小", required = true)
    @MaxWithConfig(value = "${page.maxPageSize}", message = "{page.pageSize.max}")
    private Integer pageSize;



}
