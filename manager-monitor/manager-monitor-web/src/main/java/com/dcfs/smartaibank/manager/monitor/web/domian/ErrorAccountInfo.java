package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * @author wangtingo
 * @date 2019/6/20 13:38
 * @since
 */
@ApiModel(value = "ErrorAccountInfo", description = "错账信息实体")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ErrorAccountInfo {

    @ApiModelProperty(value = "设备编号")
    private String deviceId;

    @ApiModelProperty(value = "加钞周期")
    private String cycleId;

    @ApiModelProperty(value = "对账结果")
    private String checkResult;

    @ApiModelProperty(value = "错账数量")
    private String accountNum;

    @ApiModelProperty(value = "开始处理时间，格式yyyy-MM-dd HH24:mi:ss")
    private String startTime;

    @ApiModelProperty(value = "错账修复时间，格式yyyy-MM-dd HH24:mi:ss")
    private String endTime;

    @ApiModelProperty(value = "错账处理人员")
    private String operateUser;

    @ApiModelProperty(value = "操作备注")
    private String operateDesc;

}
