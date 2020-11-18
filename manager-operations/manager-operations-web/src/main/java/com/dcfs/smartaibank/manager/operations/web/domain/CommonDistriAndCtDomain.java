package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 时间分组统计实体
 *
 * @author
 */
@ApiModel(value = "CommonDistriAndCtDomain", description = "时间分组统计实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommonDistriAndCtDomain {
	@ApiModelProperty(value = "排名")
	private Integer rankNo;

	@ApiModelProperty(value = "类型id")
	private String id;

	@ApiModelProperty(value = "类型名称")
	private String name;

	@ApiModelProperty(value = "数量")
	private Object value;

	@ApiModelProperty(value = "全行平均")
	private Object totalAvg;

	@ApiModelProperty(value = "本行平均")
	private Object branchAvg;

	@ApiModelProperty(value = "环比")
	private Float rate;

	@ApiModelProperty(value = "时间分布列表")
	private List<CommonDistributionDomain> distributionList;
}
