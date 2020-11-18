package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wangtingo
 * @date 2019/7/11 13:38
 * @since
 */
@ApiModel(value = "TradeTimeZhInfo", description = "总行视角机构交易处理时长报表(高频交易前100)")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TradeTimeZhInfo {

    @ApiModelProperty(value = "分行")
    private String unitName;

    @ApiModelProperty(value = "机构号")
    private String branchNo;

    @ApiModelProperty(value = "机构名称")
    private String branchName;

    @ApiModelProperty(value = "机构代码平均办理时长")
    private float handleTimeAvg;

    @ApiModelProperty(value = "全行均值")
    private float handleTimeTAvg;

    @ApiModelProperty(value = "机构代码时长与全行均值比值")
    private float  handleTimeAvgTAvgAndAvg;



}
