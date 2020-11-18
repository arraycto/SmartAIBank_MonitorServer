package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tanchena
 * @date 2019/7/31 14:11
 */
@Api(value = "HistoryErrotAccountInfo", description = "错账处理速度实体")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class HistoryErrotAccountInfo {

    @ApiModelProperty(value = "机构编号")
    private String branchNo;

    @ApiModelProperty(value = "机构名称")
    private String branchName;

    @ApiModelProperty(value = "错账数量")
    private String wrongCount;

    @ApiModelProperty(value = "总处理时间")
    private Float sumDealTime;

    @ApiModelProperty(value = "错账处理时间")
    private Float wrongDealTime;

    @ApiModelProperty(value = "平均错账处理时间")
    private Float dealTimeAvg;

    @ApiModelProperty(value = "均值对比")
    private String desc;

}
