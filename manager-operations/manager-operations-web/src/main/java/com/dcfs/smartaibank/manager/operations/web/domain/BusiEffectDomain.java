package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 业务效率实体
 *
 * @author
 */
@ApiModel(value = "BusiEffectDomain", description = "业务效率实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BusiEffectDomain {

	@ApiModelProperty(value = "交易总笔数")
	private Long tranCount;

	@ApiModelProperty(value = "交易总量全行平均")
	private Float tranCounTotalAvg;

	@ApiModelProperty(value = "交易笔数环比增加")
	private Float tranCountRing;

	@ApiModelProperty(value = "交易平均处理时间")
	private Float handleTimeAvg;

	@ApiModelProperty(value = "交易平均处理时间全行均值")
	private Float handleTimeTotalAvg;

	@ApiModelProperty(value = "交易平均处理时间环比增加百分比")
	private Float handleTimeRing;

	@ApiModelProperty(value = "子机构业务总量排名top10列表")
	private List<CommonRankDomain> busiSumRankTop10List;

	@ApiModelProperty(value = "子机构平均处理时间排名top10列表")
	private List<CommonRankDomain> dealTimeRankTop10List;

	@ApiModelProperty(value = "业务总量随时间的变化趋势列表")
	private List<CommonDistributionDomain> timeGroupBusiSumList;

	@ApiModelProperty(value = "平均处理时间随着时间的变化趋势列表")
	private List<CommonDistributionDomain> timeGroupDealTimeList;
}
