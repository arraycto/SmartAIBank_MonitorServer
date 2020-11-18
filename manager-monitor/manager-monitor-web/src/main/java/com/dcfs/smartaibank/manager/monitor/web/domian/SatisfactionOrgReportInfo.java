package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author qinfeng
 * @date 2019/7/31 14:11
 */
@Api(value = "SatisfactionTellerInfo", description = "柜员满意度评价实体")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SatisfactionOrgReportInfo {

    @ApiModelProperty(value = "分行")
    private String unitName;

    @ApiModelProperty(value = "机构号")
    private String branchNo;

    @ApiModelProperty(value = "机构名称")
    private String branchName;

    @ApiModelProperty(value = "好评数")
    private Integer positiveCount;

    @ApiModelProperty(value = "中评数")
    private Integer midCount;

    @ApiModelProperty(value = "差评数")
    private Integer negativeCount;

    @ApiModelProperty(value = "未评价")
    private Integer unevaluateCount;

    @ApiModelProperty(value = "评价数")
    private Integer evaluateCount;

}
