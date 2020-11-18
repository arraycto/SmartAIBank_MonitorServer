package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 排队等待时长-按客户类型统计
 *
 * @author
 */
@ApiModel(value = "QueueTimeByCustTypeDomain", description = "排队等待时长-按客户类型统计")

@Getter
@Setter
@ToString
@NoArgsConstructor
public class QueueTimeByCustTypeDomain extends CommonDistriAndCtDomain {

	@ApiModelProperty(value = "时长分组列表")
	private List<CommonGroupDomain> groupList;
}
