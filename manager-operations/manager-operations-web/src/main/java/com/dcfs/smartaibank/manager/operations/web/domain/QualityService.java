package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

/**
 * 服务质量实体类
 *
 * @author tanchen
 */
@ApiModel(value = "QualityServiceInfo", description = "服务质量信息实体类")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class QualityService {

	@ApiModelProperty(value = "机构编号")
	private String orgId;

	@ApiModelProperty(value = "服务质量类型名称")
	private String qualityType;

	@ApiModelProperty(value = "机构名称")
	private String branchName;

	@ApiModelProperty(value = "平均信息的汇总")
	private QualityService avgRateSum;

	@ApiModelProperty(value = "评价率")
	private Long rate;

	@ApiModelProperty(value = "评价率信息")
	private QualityService evaluteRate;

	@ApiModelProperty(value = "好评率信息")
	private QualityService praiseRate;

	@ApiModelProperty(value = "差评率信息")
	private QualityService navigateRate;

	@ApiModelProperty(value = "评价值")
	private Long evaluteValue;

	@ApiModelProperty(value = "环比增加百分比")
	private Long percenTage;

	@ApiModelProperty(value = "指定时间段内的指定服务质量")
	private List<QualityService> timeGroupServiceQualityList;

	@ApiModelProperty(value = "时间")
	private Long time;

	@ApiModelProperty(value = "当前值")
	private Long currentValue;

	@ApiModelProperty(value = "全行平均值")
	private Long avgValue;

	@ApiModelProperty(value = "当前分行下属支行的指定服务类型top10排名")
	private List<QualityService> subBranchRankTop10List;

	private List<QualityService> subBranchRankTop10List2;


}
