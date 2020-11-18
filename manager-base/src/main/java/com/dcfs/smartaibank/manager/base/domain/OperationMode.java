package com.dcfs.smartaibank.manager.base.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 操作方式实体
 *
 * @author wangjzm
 * @since 1.0.0
 */
@ApiModel(value = "OperationMode", description = "操作方式实体类")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class OperationMode {
	@ApiModelProperty(value = "操作方式ID", required = true)
	private String id;

	@ApiModelProperty(value = "操作方式名称", required = true)
	private String name;
}
