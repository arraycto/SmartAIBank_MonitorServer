package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *  
 *  * @author &何则    
 *  * @date   2020/7/14 15:28  
 *  * @version V1.0.0 
 *  
 */
@ApiModel(value = "TradeTimeCounterClerkReportInfo", description = "支行柜员视角柜员交易代码处理时长")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TradeTimeCounterClerkReportInfo {

    @ApiModelProperty(value = "机构号")
    private String branchNo;

    @ApiModelProperty(value = "机构名称")
    private String branchName;

    @ApiModelProperty(value = "工号")
    private String userNo;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "高频交易时长")
    private String handleTimeTotal;

    @ApiModelProperty(value = "高频交易全行平均时长")
    private String handleTimeTAvg;

    @ApiModelProperty(value = "高频交易比率")
    private String touchAvg;
}
