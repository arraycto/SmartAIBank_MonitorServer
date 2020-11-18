package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tanchena
 * @date 2019/7/18 10:05
 */

@ApiModel(value = "QualitySumBranchInfo", description = "首页服务质量汇总")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class QualitySumBranchInfo {

    @ApiModelProperty(value = "评价数")
    private Integer evaluateCount;

    @ApiModelProperty(value = "邀约总数")
    private Integer inviteCount;

    @ApiModelProperty(value = "好评数")
    private Integer evaluateGoodCount;

    @ApiModelProperty(value = "差评数")
    private Integer evaluateBadCount;

    @ApiModelProperty(value = "评价率")
    private Float evaluateRate;

    @ApiModelProperty(value = "好评率")
    private Float evaluateGoodRate;

    @ApiModelProperty(value = "中评率")
    private Float evaluateMediumRate;

    @ApiModelProperty(value = "差评率")
    private Float evaluateBadRate;

}
