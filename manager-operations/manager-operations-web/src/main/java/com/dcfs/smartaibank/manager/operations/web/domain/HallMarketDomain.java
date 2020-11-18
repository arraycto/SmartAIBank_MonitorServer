package com.dcfs.smartaibank.manager.operations.web.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 厅堂营销实体类
 *
 * @author
 */
@ApiModel(value = "HallMarketDomain", description = "厅堂营销实体类")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class HallMarketDomain {

	@ApiModelProperty(value = "机构编号")
	private String branchNo;

	@ApiModelProperty(value = "机构名称")
	private String branchName;

	@ApiModelProperty(value = "柜员编号")
	private String tellerId;

	@ApiModelProperty(value = "柜员名称")
	private String tellerName;

	@ApiModelProperty(value = "机构统计类型1-作为营业机构，2-作为上级机构统计")
	private String branchStatsType;

	@ApiModelProperty(value = "申请日期，格式为：YYYYMMDD/申请月、季，格式为：YYYYMM/申请年份，格式为：YYYY")
	private Long time;

	@ApiModelProperty(value = "营销客户数/产品数/潜在客户登记数/预约购买客户登记数量")
	private Long count;

	@ApiModelProperty(value = "全行平均值")
	private Float avgValue;

	@ApiModelProperty(value = "本行平均值")
	private Float branchAvg;

	@ApiModelProperty(value = "环比增加百分比")
	private Float rate;

	@ApiModelProperty(value = "营销客户数/产品数/潜在客户登记数/预约购买客户数/信息随时间变化列表")
	private List<CommonDistributionDomain> timeGroupList;

	@ApiModelProperty(value = "营销客户数/产品数/潜在客户登记数/预约购买客户数/信息随时间变化列表")
	private List<HallMarketDomain> list;

	@ApiModelProperty(value = "营销客户数/产品数/潜在客户登记数/预约购买客户数/子机构排名列表")
	private List<CommonRankDomain> rankList;

	@ApiModelProperty(value = "营销数量top3排名列表——柜员营销详细信息、包括本行均值、全行均值、营销数")
	private List<HallMarketDomain> top3;

	@ApiModelProperty(value = "营销数量top3排名列表——包含柜员营销数目、姓名、编号")
	private List<HallMarketDomain> brokerRank;


}
