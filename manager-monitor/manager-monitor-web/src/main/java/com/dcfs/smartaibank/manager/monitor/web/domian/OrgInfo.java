package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * 机构信息实体
 *
 * @author wangjzm
 * @since 1.0.0
 */
@ApiModel(value = "OrgInfo", description = "机构信息实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "TL9_ORGBASIC")
public class OrgInfo {

    @ApiModelProperty(value = "机构代号")
    @Id
    @Column(name = "ORGID")
    @NotBlank(message = "{orgInfo.orgId.notBlank}")
    @Size(max = 12, message = "{orgInfo.orgId.size}")
    private String orgId;

    @ApiModelProperty(value = "机构中文名称")
    @Column(name = "ORGNAME_CN")
    @Size(max = 60, message = "{orgInfoDomain.orgNameCn.size}")
    private String orgNameCn;

    @ApiModelProperty(value = "上级机构编号")
    @Column(name = "BUSINESS_SUPERIOR")
    private String businessSuperior;

    @ApiModelProperty(value = "机构级别")
    @Column(name = "ORGLEVELID")
    private String orgLevelId;

    @ApiModelProperty(value = "地址")
    @Column(name = "ADDRESS")
    private String address;

    @ApiModelProperty(value = "机构类型")
    @Column(name = "ORGTYPEID")
    private String orgTypeId;
}
