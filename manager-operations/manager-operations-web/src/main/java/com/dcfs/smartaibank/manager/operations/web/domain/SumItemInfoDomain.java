package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 概况汇总统计实体
 *
 * @author
 */
@ApiModel(value = "SumItemInfoDomain", description = "概况汇总统计实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SumItemInfoDomain {

	@ApiModelProperty(value = "机构编号")
	private String branchNo;

	@ApiModelProperty(value = "机构名称")
	private String branchName;

	@ApiModelProperty(value = "接待客户数")
	private Long recieveCount;

	@ApiModelProperty(value = "最大接待客户数")
	private Long maxRecieve;

	@ApiModelProperty(value = "预约购买客户数")
	private Long reserveCount;

	@ApiModelProperty(value = "最大预约购买客户数")
	private Long maxReserveCount;

	@ApiModelProperty(value = "差评率")
	private Float praiseRate;

	@ApiModelProperty(value = "最大差评率")
	private Float maxNegativeRate;

	@ApiModelProperty(value = "平均处理时间")
	private Float avgDealTime;

	@ApiModelProperty(value = "最长平均处理时间")
	private Float maxAvgDealTime;

	@ApiModelProperty(value = "平均等待时间")
	private Float avgWaitTime;

	@ApiModelProperty(value = "最长平均等待时间")
	private Float maxAvgWaitTime;

	@ApiModelProperty(value = "环比增加百分比")
	private Float rate;

	@ApiModelProperty(value = "全行平均值")
	private Float avgValue;

	@ApiModelProperty(value = "子机构排名top5")
	private List<CommonRankDomain> top5;

	@ApiModelProperty(value = "子机构排名倒数top5")
	private List<CommonRankDomain> bottom5;


}
