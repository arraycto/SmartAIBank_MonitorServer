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
@ApiModel(value = "ClientFlowTimeInfo", description = "时间段")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ClientFlowTimeInfo {


    @ApiModelProperty(value = "时间段")
    private String applyTimeStep;


    @ApiModelProperty(value = "机构编号")
    private String branchNo;
}
