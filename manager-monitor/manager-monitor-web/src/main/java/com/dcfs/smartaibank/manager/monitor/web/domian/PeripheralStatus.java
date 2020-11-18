package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wangjzm
 * @data 2019/06/21 16:41
 * @since 1.0.0
 */
@ApiModel(value = "PeripheralStatus", description = "外设状态实体类")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PeripheralStatus {
    @ApiModelProperty(value = "外设名称")
    private String peripheralName;

    @ApiModelProperty(value = "外设状态")
    private String peripheralStatus;

    @ApiModelProperty(value = "外设状态详情描述")
    private String peripheralStatusDetail;
}
