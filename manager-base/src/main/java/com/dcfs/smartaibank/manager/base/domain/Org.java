package com.dcfs.smartaibank.manager.base.domain;

import com.dcfs.smartaibank.springboot.core.validation.group.InsertOrUpdate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embedded;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 机构信息实体类
 *
 * @author wangting
 */
@ApiModel(value = "Org", description = "机构信息实体")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Org {
    @ApiModelProperty(value = "机构代号", required = true)
    @Size(min = 1, max = 20, message = "{org.id.size}", groups = {InsertOrUpdate.class})
    private String id;

    @ApiModelProperty(value = "机构中文名称", required = true)
    @Size(min = 1, max = 60, message = "{org.name.size}", groups = {InsertOrUpdate.class})
    private String name;

    @ApiModelProperty(value = "办公电话", required = true)
    @Embedded
    @Valid
    private Phone phone;

    @ApiModelProperty(value = "地址", required = true)
    @Embedded
    @Valid
    private Address address;

    @ApiModelProperty(value = "用户的机构代号", required = true)
    private String userOrgId;

    @ApiModelProperty(value = "机构类型", required = true)
    private OrgType type;

    @ApiModelProperty(value = "机构类型描述", readOnly = true)
    private String typeName;

    @ApiModelProperty(value = "机构级别", required = true)
    private OrgLevel level;

    @ApiModelProperty(value = "机构级别描述", readOnly = true)
    private String levelName;

    @ApiModelProperty(value = "报表查询级别", required = true)
    private OrgLevel reportLevel;

    @ApiModelProperty(value = "报表查询描述", readOnly = true)
    private String reportLevelName;

    @ApiModelProperty(value = "业务管理上级", required = true)
    private String businessSuperior;

    @ApiModelProperty(value = "业务管理上级机构名称", readOnly = true)
    private String busSupName;

    @ApiModelProperty(value = "账务机构")
    private String accountSuperior;

    @ApiModelProperty(value = "账务机构名称", readOnly = true)
    private String accountSupName;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;

    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updateTime;

    @ApiModelProperty(value = "地址详情", hidden = true)
    private String addressDesc;
}
