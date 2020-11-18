package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 时长分组统计实体
 *
 * @author
 */
@ApiModel(value = "CommonGroupDomain", description = "时长分组统计实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommonGroupDomain {
	@ApiModelProperty(value = "分组标识")
	private String group;

	@ApiModelProperty(value = "分组描述")
	private String desc;

	@ApiModelProperty(value = "数值")
	private Double value;

}
