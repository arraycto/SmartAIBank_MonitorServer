package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 业务受理类型实体
 *
 * @author
 */
@ApiModel(value = "BusinessDomain", description = "业务受理类型实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BusinessDomain {

	@ApiModelProperty(value = "推送客户数")
	private Long pushCount;

	@ApiModelProperty(value = "接待客户数")
	private Long receiveCount;

	@ApiModelProperty(value = "接待客户率")
	private Float recevieRate;

	@ApiModelProperty(value = "差评数")
	private Long negativeCount;

	@ApiModelProperty(value = "差评处理数")
	private long negativeHandleCount;

	@ApiModelProperty(value = "差评处理率")
	private Float negativeHandleRate;

	@ApiModelProperty(value = "授权业务处理数")
	private Long authHandleCount;

	@ApiModelProperty(value = "柜员呼叫处理数")
	private long callHandleCount;

	@ApiModelProperty(value = "队列管理处理数")
	private Long queueHandleCount;

	@ApiModelProperty(value = "客户身份识别数")
	private Long identifiedCount;

	@ApiModelProperty(value = "预约维护数")
	private Long reserveHandleCount;
}
