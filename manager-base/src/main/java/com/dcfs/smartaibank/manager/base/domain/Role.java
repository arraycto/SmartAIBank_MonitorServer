package com.dcfs.smartaibank.manager.base.domain;

import com.dcfs.smartaibank.springboot.core.validation.group.InsertOrUpdate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 角色实体
 *
 * @author jiazw wangjzm
 * @since 1.0.0
 */
@ApiModel(value = "Role", description = "角色实体类")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Role {
	@ApiModelProperty(value = "角色ID", required = true)
	@Size(min = 1, max = 30, message = "{role.id.size}", groups = {InsertOrUpdate.class})
	private String id;

	@ApiModelProperty(value = "角色名称")
	@Size(min = 1, max = 30, message = "{role.name.size}", groups = {InsertOrUpdate.class})
	private String name;

	@ApiModelProperty(value = "角色描述")
	@Size(min = 1, max = 100, message = "{role.desc.size}", groups = {InsertOrUpdate.class})
	private String desc;

	@ApiModelProperty(value = "系统编号", required = true)
	@NotBlank(message = "{role.systemId.notBlank}", groups = {InsertOrUpdate.class})
	private String systemId;

	@ApiModelProperty(value = "可访问资源")
	@NotEmpty(message = "{role.resources.notEmpty}", groups = {InsertOrUpdate.class})
	private List<Resource> resources;

	@ApiModelProperty(value = "更新日期", hidden = true)
	private Date updateTime;

	@ApiModelProperty(value = "生效日期", hidden = true)
	private Date effectTime;
}


