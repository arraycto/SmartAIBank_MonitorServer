package com.dcfs.smartaibank.manager.base.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**备选数据
 * @author wangtingo
 */
@ApiModel(value = "CombinedSpareDomain", description = "备选数据组合实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CombinedSpareDomain {

    /*@ApiModelProperty(value = "机构名称备选数据")
    private List<SpareDataDomain> orgList;*/

    @ApiModelProperty(value = "机构类型备选数据")
    private List<SpareDataDomain> orgTypeList;

    @ApiModelProperty(value = "机构级别备选数据")
    private List<SpareDataDomain> orgLevelList;

    @ApiModelProperty(value = "机构报表级别备选数据")
    private List<SpareDataDomain> reportLevelList;

    /*@ApiModelProperty(value = "角色名称备选数据")
    private List<SpareDataDomain> roleList;*/

    @ApiModelProperty(value = "介质备选数据")
    private List<SpareDataDomain> mediumList;

    @ApiModelProperty(value = "系统名称备选数据")
    private List<SpareDataDomain> systemList;

    /*@ApiModelProperty(value = "用户列表备选数据")
    private List<SpareDataDomain> userList;*/

    /*@ApiModelProperty(value = "用户类型备选数据")
    private List<SpareDataDomain> userTypeList;*/

    /*@ApiModelProperty(value = "部门备选数据")
    private List<SpareDataDomain> departmentList;*/
}




