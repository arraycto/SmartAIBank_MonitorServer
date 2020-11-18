package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * 机构营业时间
 *
 * @author wangjzm
 * @data 2019/07/17 13:17
 * @since 1.0.0
 */
@ApiModel(value = "OrgBusinessHours", description = "机构营业时间")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrgBusinessHours {
    @ApiModelProperty(value = "机构id")
    private String branchId;

    @ApiModelProperty(value = "营业开始时间")
    private String startTime;

    @ApiModelProperty(value = "营业结束时间")
    private String endTime;
}
