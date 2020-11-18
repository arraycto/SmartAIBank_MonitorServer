package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 下拉框备选数据实体
 * @author tanchen1
 * @date 2019/6/21 10:56
 * @since
 */
@ApiModel(value = "AlterNativeInfo", description = "下拉框备选数据实体")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AlterNativeInfo {

    @ApiModelProperty(value = "机构编号")
    private String branchNo;

    @ApiModelProperty(value = "机构名称")
    private String branchName;

    @ApiModelProperty(value = "设备类型Id")
    private String devClassKey;

    @ApiModelProperty(value = "设备类型名称")
    private String devClassName;

    @ApiModelProperty(value = "设备型号Id")
    private String devModelId;

    @ApiModelProperty(value = "设备型号 名称")
    private String devModelName;

    @ApiModelProperty(value = "厂商Id")
    private String manufacturerId;

    @ApiModelProperty(value = "厂商名称")
    private String name;

    @ApiModelProperty(value = "设备编号")
    private String deviceId;
}
