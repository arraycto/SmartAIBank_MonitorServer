package com.dcfs.smartaibank.manager.base.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 密码信息实体
 *
 * @author
 */
@ApiModel(value = "MediumInfo", description = "密码信息实体")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MediumInfo {

	@ApiModelProperty(value = "用户代号", required = true)
	private String id;

	@ApiModelProperty(value = "使用介质")
	private MediumType mediumType;

	@ApiModelProperty(value = "介质信息")
	private String mediumInfo;

	@ApiModelProperty(value = "修改时间")
	private Date mediumInfoUpdateTime;

	@ApiModelProperty(value = "异常次数")
	private int errorNum;

	@ApiModelProperty(value = "状态")
	private PwdType status;

	@ApiModelProperty(value = "上锁时间")
	private Date mediumInfoLockTime;

	@ApiModelProperty(value = " ")
	private String systemId;

}
