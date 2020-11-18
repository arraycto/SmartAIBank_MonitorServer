package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 设备管理人员故障处理速度考核图表信息(包含所有设备管理人员信息和设备管理人员故障处理速度考核图表中的柱状图信息)
 *
 * @author wangjzm
 * @data 2019/07/26 17:24
 * @since 1.0.0
 */
@ApiModel(value = "ManagersFaultHandlingSpeedGraphicGatherInfo",
        description = "设备管理人员故障处理速度考核图表信息"
                + "(包含所有设备管理人员信息和设备管理人员故障处理速度考核图表中的柱状图信息")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ManagersFaultHandlingSpeedGraphicGatherInfo {
    @ApiModelProperty(value = "设备管理人员故障处理速度考核图表中的柱状图信息")
    private List<ManagersFaultHandlingSpeedGraphicInfo> managersFaultHandlingSpeedGraphicInfoList;

    @ApiModelProperty(value = "所有设备管理人员信息")
    private List<Operator> operatorList;
}
