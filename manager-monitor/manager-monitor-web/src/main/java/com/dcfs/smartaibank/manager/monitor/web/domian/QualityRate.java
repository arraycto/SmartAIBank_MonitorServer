package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 评价占比信息
 *
 * @author tanchen
 */
@ApiModel(value = "QualityRate", description = "评价占比信息")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class QualityRate {

    @ApiModelProperty(value = "评价率")
    private Float evaluateRate;

    @ApiModelProperty(value = "好评率")
    private Float evaluateGoodRate;

    @ApiModelProperty(value = "中评率")
    private Float evaluateMidRate;

    @ApiModelProperty(value = "差评率")
    private Float evaluateBadRate;

    @ApiModelProperty(value = "客户身份识别率")
    private Float customerRate;

    @ApiModelProperty(value = "差评数")
    private Integer evaluateBadCount;

    @ApiModelProperty(value = "总网点数")
    private Integer total;

    @ApiModelProperty(value = "网点优数")
    private Integer statusGoodCount;

    @ApiModelProperty(value = "网点优数占比")
    private Float statusGoodRate;

    @ApiModelProperty(value = "网点良数")
    private Integer statusNiceCount;

    @ApiModelProperty(value = "网点良数占比")
    private Float statusNiceRate;

    @ApiModelProperty(value = "网点中数")
    private Integer statusMiddleCount;

    @ApiModelProperty(value = "网点中数占比")
    private Float statusMiddleRate;

    @ApiModelProperty(value = "网点差数")
    private Integer statusBadCount;

    @ApiModelProperty(value = "网点差数占比")
    private Float statusBadRate;

    @ApiModelProperty(value = "未服务的数")
    private Integer statusUnDealCount;

    @ApiModelProperty(value = "未服务数比例")
    private Float statusUnDealRate;

}
