package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 厅堂营销汇总实体
 *
 * @author
 */
@ApiModel(value = "HallMarketSumDomain", description = "厅堂营销汇总实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class HallMarketSumDomain {

	@ApiModelProperty(value = "营销客户数信息")
	private HallMarketDomain saleCustCount;

	@ApiModelProperty(value = "潜在客户登记数信息")
	private HallMarketDomain potentialCustRegister;

	@ApiModelProperty(value = "营销产品数信息")
	private HallMarketDomain saleProductCount;

	@ApiModelProperty(value = "预约购买客户数信息")
	private HallMarketDomain reserveBuyCust;

	@ApiModelProperty(value = "客户接待类型和业务受理类型")
	private BusinessDomain busineAcceptList;


}
