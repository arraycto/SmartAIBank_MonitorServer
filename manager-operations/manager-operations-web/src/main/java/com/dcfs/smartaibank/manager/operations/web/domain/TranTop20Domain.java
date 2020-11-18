package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 高频交易前20笔的统计实体
 *
 * @author
 */
@ApiModel(value = "TranTop20Domain", description = "高频交易前20笔的统计实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TranTop20Domain {
	@ApiModelProperty(value = "交易排名及统计列表")
	List<TellerViewTranRankDomain<UserRankDomain>> tranTop20List;

}
