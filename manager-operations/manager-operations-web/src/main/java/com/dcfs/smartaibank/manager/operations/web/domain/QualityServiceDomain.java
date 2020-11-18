package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 服务质量返回数据实体类
 *
 * @author tanchen
 */
@ApiModel(value = "QualityServiceDomain", description = "服务质量信息实体类")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class QualityServiceDomain {

	@ApiModelProperty(value = "服务质量类型")
	private Object qualityType;

	@ApiModelProperty(value = "平均信息的汇总")
	private CommonDistriAndCtDomain avgRateSum;

	@ApiModelProperty(value = "当前分行下属支行的指定服务类型top10排名")
	private List<CommonRankDomain> subBranchRankTop10List;


}
