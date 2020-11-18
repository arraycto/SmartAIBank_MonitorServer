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
 *  * @date   2020/7/19 13:07  
 *  * @version V1.0.0 
 *  
 */
@ApiModel(value = "ServiceTimeReportInfo", description = "支行视角柜员服务客户时间统计报表查询")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ServiceTimeReportInfo {

    @ApiModelProperty(value = "分行")
    private String unitName;

    @ApiModelProperty(value = "机构号")
    private String branchNo;

    @ApiModelProperty(value = "机构名称")
    private String branchName;

    @ApiModelProperty(value = "工号")
    private String userNo;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "办理业务总时长")
    private String handleTimeTotal;

}
