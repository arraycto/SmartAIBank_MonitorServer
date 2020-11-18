package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 设备耗材统计信息
 *
 * @author wangjzm
 * @data 2019/07/26 17:24
 * @since 1.0.0
 */
@ApiModel(value = "EquipmentConsumablesInfo", description = "设备耗材统计信息")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EquipmentConsumablesInfo {
    @ApiModelProperty(value = "设备类型id")
    private String devClassId;

    @ApiModelProperty(value = "设备类型名称")
    private String devClassName;

    @ApiModelProperty(value = "厂商id")
    private String manufacturerId;

    @ApiModelProperty(value = "厂商名称")
    private String manufacturerName;

    @ApiModelProperty(value = "使用时间（小时）")
    private Float useTime;

    @ApiModelProperty(value = "更换次数")
    private Integer numberOfReplacements;

    @ApiModelProperty(value = "平均使用小时-自身")
    private Float selfAvgUseTime;

    @ApiModelProperty(value = "平均使用小时-均值（查询条件内的所有记录的平均使用小时)")
    private Float allAvgUseTime;

    @ApiModelProperty(value = "平均使用小时均值对比")
    private String useTimeCompareResult;
}
