package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 交易排名实体
 *
 * @author
 */
@ApiModel(value = "TranRankDomain", description = "交易排名实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TranRankDomain {
	@ApiModelProperty(value = "排名")
	private Integer rankNo;

	@ApiModelProperty(value = "交易码")
	private String tranCode;

	@ApiModelProperty(value = "交易名称")
	private String tranName;

	@ApiModelProperty(value = "数量")
	private Float value;

	@ApiModelProperty(value = "全行均值")
	private Float totalAvg;

	@ApiModelProperty(value = "本行平均")
	private Float branchAvg;
}
