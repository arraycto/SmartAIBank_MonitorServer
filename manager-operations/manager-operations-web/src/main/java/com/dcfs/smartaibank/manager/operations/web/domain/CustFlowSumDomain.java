package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 客流汇总统计实体
 *
 * @author
 */
@ApiModel(value = "CustFlowSumDomain", description = "客流汇总统计实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CustFlowSumDomain {

	@ApiModelProperty(value = "客流汇总信息-接待客户数、全行均值，随时间变化的规律")
	private CustFlowDomain custFlowInfo;

	@ApiModelProperty(value = "不同时间段的对私/对公客流变化信息")
	private CustFlowDomain timePartCustFlow;


	@ApiModelProperty(value = "不同性别段的客流占比信息")
	private List<CommonGroupDomain> custFlowPartBySexList;

	@ApiModelProperty(value = "不同年龄段的客流占比信息")
	private List<CommonGroupDomain> custFlowPartByAgeList;

	@ApiModelProperty(value = "不同资产段的客流占比信息")
	private List<CommonGroupDomain> custFlowPartByAssetsList;

}
