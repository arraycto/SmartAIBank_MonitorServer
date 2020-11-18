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
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 用户实体
 *
 * @author fanph
 * @since 1.0.0
 */
@ApiModel(value = "User", description = "用户信息实体")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
    @ApiModelProperty(value = "用户代号", required = true)
    @Size(min = 1, max = 20, message = "{user.id.size}", groups = {InsertOrUpdate.class})
    private String id;

    @ApiModelProperty(value = "用户名称", required = true)
    @Size(min = 1, max = 100, message = "{user.name.size}", groups = {InsertOrUpdate.class})
    private String name;

    @ApiModelProperty(value = "性别，M-男，F-女，U-未知")
    private Gender gender;

    @ApiModelProperty(value = "手机号码")
    @Embedded
    @Valid
    private Mobile mobile;

    @ApiModelProperty(value = "座机号码")
    @Embedded
    @Valid
    private Phone phone;

    @ApiModelProperty(value = "微信号")
    private String weChat;

    @ApiModelProperty(value = "传真")
    @Size(max = 20, message = "{common.fax.size}", groups = {InsertOrUpdate.class})
    private String fax;

    @ApiModelProperty(value = "邮箱")
    @Size(max = 50, message = "{common.email.size}", groups = {InsertOrUpdate.class})
    @Email(message = "{common.email.format}", groups = {InsertOrUpdate.class})
    private String email;

    @ApiModelProperty(value = "生效日期")
    private Date effectTime;

    @ApiModelProperty(value = "所属机构代码", required = true)
    @Size(min = 1, max = 12, message = "{user.org.size}", groups = {InsertOrUpdate.class})
    private String orgId;

    @ApiModelProperty(value = "所属机构名称", hidden = true)
    private String orgName;

    @ApiModelProperty(value = "所属部门代号")
    private String departmentId;

    @ApiModelProperty(value = "所属部门名称", hidden = true)
    private String departmentName;

    @ApiModelProperty(value = "职位代码")
    private String positionId;

    @ApiModelProperty(value = "使用介质")
    private MediumType mediumType;

    @ApiModelProperty(value = "使用状态")
    private UseFlag useFlag;

    @ApiModelProperty(value = "角色列表")
    @Size(min = 1, message = "{user.roles.size}", groups = {InsertOrUpdate.class})
    private List<String> roles;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;

    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updateTime;
}
