package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 服务质量信息实体类
 *
 * @author
 */
@ApiModel(value = "QualityServiceSumDomain", description = "服务质量信息实体类")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class QualityServiceSumDomain {

	@ApiModelProperty(value = "评价信息的汇总")
	private CommonDistriAndCtDomain evaluteRate;

	@ApiModelProperty(value = "好评信息的汇总")
	private CommonDistriAndCtDomain praiseRate;

	@ApiModelProperty(value = "差评信息的汇总")
	private CommonDistriAndCtDomain navigateRate;
}
