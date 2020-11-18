package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author tanchena
 * @date 2019/7/31 10:15
 */
@ApiModel(value = "HistoryResultList", description = "外设详情信息实体")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class HistoryResultList {

    @ApiModelProperty("终端号")
    private String mId;

    @ApiModelProperty("计划开机时间")
    private String normalTime;

    @ApiModelProperty("外设模块信息列表")
    private List<HistoryHeaderAndinfo> lists;

}
