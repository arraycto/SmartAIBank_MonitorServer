package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 客流单独统计项实体
 *
 * @author
 */
@ApiModel(value = "CustFlowDomain", description = "客流单独统计项实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CustFlowDomain {

	@ApiModelProperty(value = "客户接待数")
	private Long receiveCount;

	@ApiModelProperty(value = "环比增加")
	private Float rate;

	@ApiModelProperty(value = "全行平均")
	private Long totalAvg;

	@ApiModelProperty(value = "对私客户接待数")
	private Long receivePrivateCount;

	@ApiModelProperty(value = "对公客户接待数")
	private Long receivePublicCount;

	@ApiModelProperty(value = "对公客户比例")
	private Long publicRate;

	@ApiModelProperty(value = "排名数")
	private Long rownum;

	@ApiModelProperty(value = "对私客户比例")
	private Long privateRate;

	@ApiModelProperty(value = "时间区间")
	private String timeStep;

	@ApiModelProperty(value = "客流随时间变化的趋势列表")
	private List<CommonDistributionDomain> timeGroupCustFlowList;

	@ApiModelProperty(value = "子机构排名列表")
	private List<CommonRankDomain> list;

	@ApiModelProperty(value = "对公对私客流变化")
	private List<CustFlowDomain> timePartCustFlowForPublicList;

}
