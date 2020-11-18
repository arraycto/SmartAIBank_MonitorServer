package com.dcfs.smartaibank.manager.base.domain;

import com.dcfs.smartaibank.springboot.core.validation.group.InsertOrUpdate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author jiazw wangjzm
 * 角色联合主键
 */
@ApiModel(value = "RoleKey", description = "角色实体类联合主键")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class RoleKey {
	@ApiModelProperty(value = "角色ID", required = true)
	@Size(min = 1, max = 30, message = "{role.id.size}", groups = {InsertOrUpdate.class})
	private String roleId;

	@ApiModelProperty(value = "系统编号", required = true)
	@NotBlank(message = "{role.systemId,notBlank}", groups = {InsertOrUpdate.class})
	private String systemId;
}
