package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * @author wangtingo
 * @date 2019/7/16 9:06
 * @since
 */
@ApiModel(value = "QualityBranchSum", description = "营业机构服务质量汇总")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class QualityBranchSum {

    @ApiModelProperty(value = "评价率")
    private Float evaluateRate;

    @ApiModelProperty(value = "好评率")
    private Float positiveRate;

    @ApiModelProperty(value = "中评率")
    private Float evaluateMidRate;

    @ApiModelProperty(value = "差评率")
    private Float negativeRate;

    @ApiModelProperty(value = "客户身份识别率")
    private Float customerRate;

    @ApiModelProperty(value = "差评数")
    private Integer negativeCount;

}
