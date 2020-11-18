package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 业务效率汇总信息实体
 *
 * @author
 */
@ApiModel(value = "BusiEffectSumDomain", description = "业务效率汇总信息实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BusiEffectSumDomain {

	@ApiModelProperty(value = "业务汇总信息")
	private BusiEffectDomain businessSum;

	@ApiModelProperty(value = "交易平均处理时间汇总信息")
	private BusiEffectDomain tradeDealAvgTime;

	@ApiModelProperty(value = "高频交易/耗时交易top20信息")
	private HighFrequencyTradeDomain highFrequencyTrade;
}
