package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 交易处理各状态数量及占比实体类
 *
 * @author wangjzm
 * @data 2019/06/14 17:15
 * @since 1.0.0
 */
@ApiModel(value = "TranStatusSum", description = "交易处理各状态数量及占比实体类")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TranStatusSum {
    @ApiModelProperty(value = "状态交易总数")
    private Integer totalCount;

    @ApiModelProperty(value = "正常交易笔数")
    private Integer successCount;

    @ApiModelProperty(value = "异常交易笔数")
    private Integer exceptionCount;

    @ApiModelProperty(value = "失败交易笔数")
    private Integer failureCount;

    @ApiModelProperty(value = "正常交易笔数占比")
    private Float successRate;

    @ApiModelProperty(value = "异常交易笔数占比")
    private Float exceptionRate;

    @ApiModelProperty(value = "失败交易笔数占比")
    private Float failureRate;

}
