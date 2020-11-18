package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author wangtingo
 * @date 2019/7/11 13:38
 * @since
 */
@ApiModel(value = "ClientFlowReportInfo", description = "支行视角各时段客户流量曲线")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ClientFlowReportInfo {

    @ApiModelProperty(value = "机构号")
    private String branchNo;

    @ApiModelProperty(value = "机构名称")
    private String branchName;

    @ApiModelProperty(value = "分行")
    private String unitName;

    @ApiModelProperty(value = "纵坐标票号数")
    private List<ClientFlowTimezInfo> receiveCount;


    @ApiModelProperty(value = "横坐标时间段")
    private List<ClientFlowTimeInfo> applyTimeStep;



}
