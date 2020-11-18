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
public class AccountDetailParams {

    @ApiModelProperty(value = "机构编号", required = true)
    private String orgId;

    @ApiModelProperty(value = "用户", required = true)
    private String user;

    @ApiModelProperty(value = "右键，按钮描述")
    private String target;

    @ApiModelProperty(value = "periodId", required = true)
    private String periodId;

    @ApiModelProperty(value = "busType", required = true)
    private String busType;
}
