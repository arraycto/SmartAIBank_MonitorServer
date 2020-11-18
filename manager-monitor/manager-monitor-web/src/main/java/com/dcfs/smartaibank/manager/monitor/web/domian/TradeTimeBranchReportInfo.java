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
 *  * @date   2020/7/18 14:46  
 *  * @version V1.0.0 
 *  
 */
@ApiModel(value = "TradeTimeBranchReportInfo", description = "支行网点视角交易代码时长数据报表查询")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TradeTimeBranchReportInfo {

    @ApiModelProperty(value = "分行")
    private String unitName;

    @ApiModelProperty(value = "机构号")
    private String branchNo;

    @ApiModelProperty(value = "机构名称")
    private String branchName;

    @ApiModelProperty(value = "机构平均时长")
    private String handleTimeAvg;

    @ApiModelProperty(value = "全行均值")
    private String handleTimeTAvg;

    @ApiModelProperty(value = "网点交易比率")
    private String touchAvg;

}
