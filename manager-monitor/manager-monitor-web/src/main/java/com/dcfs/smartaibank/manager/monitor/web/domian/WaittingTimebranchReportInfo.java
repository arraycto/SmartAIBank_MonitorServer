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
 *  * @date   2020/7/19 14:33  
 *  * @version V1.0.0 
 *  
 */
@ApiModel(value = "WaittingTimebranchReportInfo", description = "支行视角客户等候时间数据")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class WaittingTimebranchReportInfo {

    @ApiModelProperty(value = "分行")
    private String unitName;

    @ApiModelProperty(value = "机构号")
    private String branchNo;

    @ApiModelProperty(value = "机构名称")
    private String branchName;

    @ApiModelProperty(value = "票号数")
    private Integer receiveCount;

    @ApiModelProperty(value = "等候时间")
    private String waitTimeAvg;
}
