package com.dcfs.smartaibank.manager.monitor.web.param;

import com.dcfs.smartaibank.manager.monitor.web.domian.AlarmDealStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tanchen1
 * @date 2019/6/20 9:03
 * @since
 */
@ApiModel(value = "AlarmParams", description = "查询参数")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class AlarmParams {

    @ApiModelProperty(value = "用户Id")
    private String userId;

    @ApiModelProperty(value = "机构Id")
    private String branchNo;

    @ApiModelProperty(value = "设备类型")
    private String devType;

    @ApiModelProperty(value = "设备Id")
    private String devId;

    @ApiModelProperty(value = "处理状态(1-未处理，2-处理中，3-已报修，4-已解除，5-已关闭 ,6-已挂起)")
    private AlarmDealStatus alarmDealStatus;

    @ApiModelProperty(value = "预警级别排序字段  包括排序规则  例如:  time desc")
    private String orderColumn;

    @ApiModelProperty(value = "当前页数", required = true)
    private Integer pageNum;

    @ApiModelProperty(value = "每页大小", required = true)
    private Integer pageSize;

    @ApiModelProperty(value = "交易流水Id")
    private String seqId;

    private String startDateTime;

    private String endDateTime;

}
