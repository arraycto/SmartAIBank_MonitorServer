package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 柜员视角-时间分布及当前时间信息实体
 *
 * @author
 */
@ApiModel(value = "TellerViewDistriAndCtDomain", description = "柜员视角-时间分布及当前时间信息实体")

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TellerViewDistriAndCtDomain extends CommonDistriAndCtDomain {
	@ApiModelProperty(value = "排名趋势，上升下降")
	private int rankTrend;

}
