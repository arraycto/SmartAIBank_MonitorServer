package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author liangchenglong
 * @date 2019/7/31 13:38
 * @since
 */
@ApiModel(value = "MonitorRemoteDevice", description = "通过设备编号获取外设信息")
@Data
@NoArgsConstructor
@ToString
public class MonitorRemoteDevice {

    @ApiModelProperty(value = "设备类型key")
    private String devClassKey;

    @ApiModelProperty(value = "设备类型名称")
    private String devClassName;
}
