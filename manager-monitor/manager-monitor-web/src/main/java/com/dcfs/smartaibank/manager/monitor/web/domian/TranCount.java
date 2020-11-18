package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 交易处理分设备类型交易笔数实体类
 *
 * @author wangjzm
 * @data 2019/06/14 17:15
 * @since 1.0.0
 */
@ApiModel(value = "TranCount", description = "交易处理分设备类型交易笔数实体类")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TranCount {
    @ApiModelProperty(value = "交易总数")
    private Integer totalCount;

    @ApiModelProperty(value = "ATM交易笔数")
    private Integer atmCount;

    @ApiModelProperty(value = "ATM交易笔数")
    private Float atmRate;

    @ApiModelProperty(value = "STM交易笔数")
    private Integer stmCount;

    @ApiModelProperty(value = "STM交易笔数占比")
    private Float stmRate;

    @ApiModelProperty(value = "BSR交易笔数")
    private Integer bsrCount;

    @ApiModelProperty(value = "BSR交易笔数占比")
    private Float bsrRate;
}
