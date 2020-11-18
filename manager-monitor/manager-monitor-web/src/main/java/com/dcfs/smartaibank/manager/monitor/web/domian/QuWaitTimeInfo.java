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
@ApiModel(value = "QuWaitTimeInfo", description = "总行视角等候时间数据报表")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class QuWaitTimeInfo {

    @ApiModelProperty(value = "机构号")
    private String branchNo;

    @ApiModelProperty(value = "机构号称")
    private String branchName;

    @ApiModelProperty(value = "票号数")
    private Integer receiveCount;

    @ApiModelProperty(value = "分行")
    private String unitName;

    @ApiModelProperty(value = "等候时间")
    private Integer waitTimeAvg;


}
