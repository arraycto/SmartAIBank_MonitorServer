package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 外设信息实体类
 *
 * @author wangjzm
 * @data 2019/07/23 16:21
 * @since 1.0.0
 */
@ApiModel(value = "PeripheralModel", description = "外设信息实体类")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeripheralModel {
    @ApiModelProperty(value = "外设模块id")
    private String peripheralId;

    @ApiModelProperty(value = "外设模块名称")
    private String peripheralName;
}
