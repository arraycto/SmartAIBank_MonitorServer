package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 排队等待时长统计信息实体
 *
 * @author
 */
@ApiModel(value = "QueueCustDetailDomain", description = "排队等待时长统计信息实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class QueueCustDetailDomain {
	@ApiModelProperty(value = "排队等待时长-汇总信息")
	private CommonDistriAndCtDomain queueTimeGather;

	@ApiModelProperty(value = "排队等待-时长分组统计信息")
	private List<CommonGroupDomain> queueTimeGroupList;

	@ApiModelProperty(value = "排队等待-时间段统计信息")
	private List<CommonGroupDomain> queueTimeDomainList;

	@ApiModelProperty(value = "排队等待时长-按客户类型统计信息")
	private List<QueueTimeByCustTypeDomain> queueTimeByCustTypeList;

}
