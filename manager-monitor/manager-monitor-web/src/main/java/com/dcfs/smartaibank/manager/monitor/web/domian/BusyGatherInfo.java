package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 繁忙度信息汇总实体类
 *
 * @author wangjzm
 * @data 2019/07/03 15:23
 * @since 1.0.0
 */
@ApiModel(value = "BusyGatherInfo", description = "繁忙度信息汇总实体类")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BusyGatherInfo {
    @ApiModelProperty(value = "等待客户数")
    private Integer waitingCustomers;

    @ApiModelProperty(value = "窗口平均等待客户数")
    private Integer avgWindowWaitingCustomers;

    @ApiModelProperty(value = "客户平均等待时间")
    private Integer avgWaitingTime;

    @ApiModelProperty(value = "客户最长等待时间")
    private Integer maxWaitingTime;

    @ApiModelProperty(value = "已开窗口数")
    private Integer openWindowsCount;

    @ApiModelProperty(value = "未开窗口数")
    private Integer unopenedWindowsCount;

    @ApiModelProperty(value = "网点总数")
    private Integer totalCount;

    @ApiModelProperty(value = "饱和网点数")
    private Integer saturationCount;

    @ApiModelProperty(value = "饱和网点数量占比")
    private Float saturationRate;

    @ApiModelProperty(value = "忙碌网点数")
    private Integer busyCount;

    @ApiModelProperty(value = "忙碌网点数量占比")
    private Float busyRate;

    @ApiModelProperty(value = "正常网点数")
    private Integer normalCount;

    @ApiModelProperty(value = "正常网点数量占比")
    private Float normalRate;

    @ApiModelProperty(value = "空闲网点数")
    private Integer idleCount;

    @ApiModelProperty(value = "空闲网点数量占比")
    private Float idleRate;

    @ApiModelProperty(value = "未营业网点数")
    private Integer unopenedCount;

    @ApiModelProperty(value = "未营业网点数量占比")
    private Float unopenedRate;

    @ApiModelProperty(value = "客户最长等待时间所属网点id")
    private String maxWaitingTimeBranchId;

    @ApiModelProperty(value = "客户最长等待时间所属网点名称")
    private String maxWaitingTimeBranchName;
}
