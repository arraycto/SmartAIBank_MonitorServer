package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 概况汇总单独统计项实体
 *
 * @author
 */
@ApiModel(value = "SumInfoDomain", description = "概况汇总单独统计项实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SumInfoDomain {
	@ApiModelProperty(value = "客流-接待客户数概况信息")
	private SumItemInfoDomain customer;

	@ApiModelProperty(value = "营销-预约购买客户概况信息")
	private SumItemInfoDomain reserve;

	@ApiModelProperty(value = "服务质量-好评率概况信息")
	private SumItemInfoDomain praise;

	@ApiModelProperty(value = "业务效率-平均处理时间概况信息")
	private SumItemInfoDomain avgDealTime;

	@ApiModelProperty(value = "排队-交易平均处理时间概况信息")
	private SumItemInfoDomain avgWaitTime;
}
