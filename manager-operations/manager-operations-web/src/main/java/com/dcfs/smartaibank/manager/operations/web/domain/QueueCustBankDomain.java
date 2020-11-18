package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 排队等待时长网点排名信息实体
 *
 * @author
 */
@ApiModel(value = "QueueCustBankDomain", description = "排队等待时长网点排名信息实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class QueueCustBankDomain {
	@ApiModelProperty(value = "排队等待时长-网点排名信息")
	private List<CommonRankDomain> queueTimeRankList;
}
