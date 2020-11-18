package com.dcfs.smartaibank.manager.operations.web.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户排名实体
 *
 * @author
 */
@ApiModel(value = "UserRankDomain", description = "用户排名实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserRankDomain {
	@ApiModelProperty(value = "排名")
	private int rankNo;

	@ApiModelProperty(value = "用户编号")
	private String userId;

	@ApiModelProperty(value = "用户名称")
	private String userName;

	@ApiModelProperty(value = "数量")
	private Float value;

	@ApiModelProperty(value = "全行均值")
	private Float totalAvg;

	@ApiModelProperty(value = "本行平均")
	private Float branchAvg;

	@ApiModelProperty(value = "环比")
	private Float rate;
}

