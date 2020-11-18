package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 排名信息实体,包括排名列表及均值
 *
 * @param <T> 数据类型
 * @author
 */
@ApiModel(value = "CommonRank1Domain", description = "排名信息实体,包括排名列表及均值")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommonRank1Domain<T> {
	@ApiModelProperty(value = "全行均值")
	private Float totalAvg;

	@ApiModelProperty(value = "本行平均")
	private Float branchAvg;

	@ApiModelProperty(value = "排名列表")
	private List<T> rankList;
}
