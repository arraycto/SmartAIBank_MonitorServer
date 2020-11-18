package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



/**
 * 监控交易处理列表详情实体类
 *
 * @author wangjzm
 * @data 2019/07/01 15:51
 * @since 1.0.0
 */
@ApiModel(value = "QueueTime", description = "监控交易处理列表详情实体类")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class QueueTime {

    @ApiModelProperty(value = "序号")
    private String rankNo;

    @ApiModelProperty(value = "机构号")
    private String branchNo;

    @ApiModelProperty(value = "机构号")
    private String branchName;

    @ApiModelProperty(value = "日期")
    private String time;

    @ApiModelProperty(value = "总抽号数")
    private String queueNum;

    @ApiModelProperty(value = "平均排队等待时长")
    private String avgWaitTime;
}
