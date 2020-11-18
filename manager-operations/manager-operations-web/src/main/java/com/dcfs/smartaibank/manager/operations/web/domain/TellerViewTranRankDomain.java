package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 柜员视角-交易排名信息实体
 *
 * @param <T> 数据类型
 * @author
 */
@ApiModel(value = "TellerViewTranRankDomain", description = "柜员视角-交易排名信息实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TellerViewTranRankDomain<T> extends CommonRank1Domain<T> {
	@ApiModelProperty(value = "排名")
	private String rankNo;

	@ApiModelProperty(value = "交易码")
	private String tranCode;

	@ApiModelProperty(value = "交易名称")
	private String tranName;
}
