package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tanchena
 * @date 2019/7/18 10:49
 */
@ApiModel(value = "QualitySumLevelInfo", description = "关于首页按网点维度评价占比信息")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class QualitySumLevelInfo {

    @ApiModelProperty(value = "网点优数")
    private Integer statusGoodCount;

    @ApiModelProperty(value = "网点优数占比")
    private float statusGoodRate;

    @ApiModelProperty(value = "网点良数")
    private Integer statusNiceCount;

    @ApiModelProperty(value = "网点良数占比")
    private float statusNiceRate;

    @ApiModelProperty(value = "网点中数")
    private Integer statusMiddleCount;

    @ApiModelProperty(value = "网点中数占比")
    private float statusMiddleRate;

    @ApiModelProperty(value = "网点差数")
    private Integer statusBadCount;

    @ApiModelProperty(value = "网点差数占比")
    private float statusBadRate;

    @ApiModelProperty(value = "网点未服务数")
    private Integer statusUnDealCount;

    @ApiModelProperty(value = "网点未服务占比")
    private float statusUnDealRate;

}
