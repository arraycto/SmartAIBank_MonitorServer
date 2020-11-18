package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 用户排名及详情统计实体
 *
 * @author
 */
@ApiModel(value = "TellerViewRankAndDetailDomain", description = "用户排名及详情统计实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TellerViewRankAndDetailDomain {
	@ApiModelProperty(value = "用户排名列表")
	private CommonRank1Domain<UserRankDomain> userRankList;

	@ApiModelProperty(value = "用户详情列表")
	private List<CommonDistriAndCtDomain> userDetailList;
}
