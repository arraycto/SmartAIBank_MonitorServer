package com.dcfs.smartaibank.manager.monitor.web.domian;

import com.dcfs.smartaibank.springboot.core.validation.group.InsertOrUpdate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 资源实体
 *
 * @author jiazw wangjzm
 * @since 1.0.0
 */
@ApiModel(value = "Resource", description = "资源实体类")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Resource {
	@ApiModelProperty(value = "资源ID", required = true)
	@Size(min = 1, max = 30, message = "{resource.id.size}", groups = {InsertOrUpdate.class})
	private String id;

	@ApiModelProperty(value = "上级资源")
	@Size(max = 30, message = "{resource.parent.size}", groups = {InsertOrUpdate.class})
	private String parent;

	@ApiModelProperty(value = "资源名称")
	@Size(min = 1, max = 30, message = "{resource.name.size}", groups = {InsertOrUpdate.class})
	private String name;

	@ApiModelProperty(value = "详细描述")
	@Size(min = 1, max = 200, message = "{resource.detail.size}", groups = {InsertOrUpdate.class})
	private String details;

	@ApiModelProperty(value = "英文描述")
	@Size(min = 1, max = 200, message = "{resource.givenname.size}", groups = {InsertOrUpdate.class})
	private String givenName;

	@ApiModelProperty(value = "资源类型")
	private ResourceType type;

	@ApiModelProperty(value = "URL")
	private String url;

	@ApiModelProperty(value = "图标")
	private String icon;

	@ApiModelProperty(value = "显示标志")
	private ShowFlag showFlag;

	@ApiModelProperty(value = "所属系统")
	private String systemId;

	@ApiModelProperty(value = "互斥资源")
	private List<String> mutexs;

	@ApiModelProperty(value = "操作方式集合")
	private List<OperationMode> operationMode;

	@ApiModelProperty(value = "创建时间", hidden = true)
	private Date createTime;

	@ApiModelProperty(value = "更新时间", hidden = true)
	private Date updateTime;

}
