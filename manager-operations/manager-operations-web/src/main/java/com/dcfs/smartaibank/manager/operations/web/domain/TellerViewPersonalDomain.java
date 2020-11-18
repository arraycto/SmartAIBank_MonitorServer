package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 柜员视角-个人详情实体
 *
 * @author
 */
@ApiModel(value = "TellerViewPersonalDomain", description = "柜员视角-个人详情实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TellerViewPersonalDomain {
	@ApiModelProperty(value = "处理时长详情")
	private TellerViewDistriAndCtDomain handleTimeDistributionList;

	@ApiModelProperty(value = "交易数量详情")
	private TellerViewDistriAndCtDomain tranCountDistributionList;

	@ApiModelProperty(value = "评价率详情")
	private TellerViewDistriAndCtDomain evaluateRateList;

	@ApiModelProperty(value = "好评率详情")
	private TellerViewDistriAndCtDomain positiveRateList;

	@ApiModelProperty(value = "差评率详情")
	private TellerViewDistriAndCtDomain negativeRateList;

	@ApiModelProperty(value = "高频交易排名列表")
	private List<TranRankDomain> ranTop20HandleTimeList;


}
