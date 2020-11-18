package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tanchen1
 * @date 2019/7/3 14:25
 * @since
 */
@ApiModel(value = "QualityListInfo", description = "列表展示服务质量信息")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class QualityListInfo {

    @ApiModelProperty(value = "机构编号")
    private String branchNo;

    @ApiModelProperty(value = "机构名称")
    private String branchName;

    @ApiModelProperty(value = "等级")
    private String qualityLevel;

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

    @ApiModelProperty(value = "差评个数")
    private Integer evaluateBadCount;

    @ApiModelProperty(value = "行数(用于排名的时候)")
    private Integer rowNum;

}
