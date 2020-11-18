package com.dcfs.smartaibank.manager.monitor.web.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author liangchenglong
 * @date 2019/7/30 9:03
 * @since
 */
@Data
@ToString
public class AccountRecordParams {

    @ApiModelProperty(value = "mid", required = true)
    private String mid;

    @ApiModelProperty(value = "startDate")
    private String startDate;

    @ApiModelProperty(value = "endDate")
    private String endDate;

    @ApiModelProperty(value = "user", required = true)
    private String user;

    @ApiModelProperty(value = "orgId", required = true)
    private String orgId;
}
