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
 * @date 2019/7/22 13:38
 * @since
 */
@ApiModel(value = "HistoryGraphInfo", description = "历史图表信息")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class HistoryGraphInfo {

    @ApiModelProperty(value = "设备类型key")
    private String devClassKey;

    @ApiModelProperty(value = "设备类型名称")
    private String devClassName;

    @ApiModelProperty(value = "设备类型的横坐标")
    private List<AlterNativeInfo> deviceType;

    @ApiModelProperty(value = "纵坐标，厂商下的设备信息")
    private List<ManufFaultInfo> mInfos;

    @ApiModelProperty(value = "厂商故障率对象")
    private List<ManufFaultInfo> manufFaultInfo;

}
