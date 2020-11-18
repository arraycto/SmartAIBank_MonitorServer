package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 排名信息实体
 *
 * @author
 */
@ApiModel(value = "CommonRankDomain", description = "排名信息实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommonRankDomain {
	@ApiModelProperty(value = "排名")
	private Integer rankNo;

	@ApiModelProperty(value = "机构编号")
	private String branchNo;

	@ApiModelProperty(value = "机构名称")
	private String branchName;

	@ApiModelProperty(value = "数值")
	private Float value;

	@ApiModelProperty(value = "全行均值")
	private Float totalAvg;

	@ApiModelProperty(value = "本行平均")
	private Float branchAvg;

	@ApiModelProperty(value = "环比")
	private Float rate;
}
