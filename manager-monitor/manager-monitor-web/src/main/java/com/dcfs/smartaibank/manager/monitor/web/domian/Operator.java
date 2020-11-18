package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wangjzm
 * @data 2019/06/21 14:48
 * @since 1.0.0
 */
@ApiModel(value = "Operator", description = "操作人员实体类")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Operator {
    @ApiModelProperty(value = "操作人员id")
    private String userId;

    @ApiModelProperty(value = "操作人员名称")
    private String userName;
}
