package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author sunbba
 * @date 20200327
 * @since
 */
@ApiModel(value = "BranchWaitTimeReport", description = "支行视角客户等候时间报表")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BranchWaitTimeReport {

    @ApiModelProperty(value = "序号")
    private String sequenceNumber;

    @ApiModelProperty(value = "分行")
    private String branch;

    @ApiModelProperty(value = "机构号")
    private String orgID;

    @ApiModelProperty(value = "机构名称")
    private String orgName;

    @ApiModelProperty(value = "票号数")
    private String ticketNumber;

    @ApiModelProperty(value = "等候时间")
    private String waitTime;


}
