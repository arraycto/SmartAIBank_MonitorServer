package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 高频交易信息实体
 *
 * @author
 */
@ApiModel(value = "HighFrequencyTradeDomain", description = "高频交易信息实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class HighFrequencyTradeDomain {

	@ApiModelProperty(value = "交易码")
	private String tradeCode;

	@ApiModelProperty(value = "交易名称")
	private String tradeName;

	@ApiModelProperty(value = "高频交易总量")
	private Long trandCount;

	@ApiModelProperty(value = "交易量环比增加百分比")
	private Float tranCountRing;

	@ApiModelProperty(value = "交易总量全行平均值")
	private Long tranCountTotalAvg;

	@ApiModelProperty(value = "交易平均处理时间")
	private Float handleTimeAvg;

	@ApiModelProperty(value = "交易平均处理时间全行均值")
	private Float handleTimeTotalAvg;

	@ApiModelProperty(value = "高频/耗时交易处理总时间")
	private Float handleTimeTotal;

	@ApiModelProperty(value = "交易平均处理时间环比增加百分比")
	private Float handleTimeRing;

	@ApiModelProperty(value = "最快处理时长时间")
	private Float mostQuickTime;

	@ApiModelProperty(value = "时间")
	private String time;

	@ApiModelProperty(value = "高频/耗时交易前20名或者是耗时交易后20名")
	private List<HighFrequencyTradeDomain> list;

	@ApiModelProperty(value = "高频/耗时交易的处理时间趋势变化信息")
	private List<HighFrequencyTradeDomain> tendList;

}
