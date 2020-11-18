package com.dcfs.smartaibank.manager.monitor.web.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
/**/

/**
 * @author liangchenglong
 * @date 2019/7/12 9:03
 * @since
 */
@ApiModel(value = "AlarmTransParams", description = "预警交易参数查询")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AlarmTransParams {
    @ApiModelProperty(value = "判断是否为管理机构:false不是，true是,默认为true")
    private Boolean isManager = true;

    @ApiModelProperty(value = "机构编号")
    private String branchNo;

    @ApiModelProperty(value = "设备类型")
    private String devType;

    @ApiModelProperty(value = "设备编号")
    private String devId;

    @ApiModelProperty(value = "交易流水号")
    private String seqId;

    @ApiModelProperty(value = "当前页数", required = true)
    @NotNull
    private Integer pageNum;

    @ApiModelProperty(value = "每页大小", required = true)
    @NotNull
    private Integer pageSize;

    @ApiModelProperty(value = "设备状态:0-未使用、1-正常使用、2-正在维修、9-已报废", hidden = true)
    private Integer alarmStatus = 4;

    @ApiModelProperty(value = "预警级别排序字段")
    private String alarmOrder;

    private String startDateTime;

    private String endDateTime;

}
