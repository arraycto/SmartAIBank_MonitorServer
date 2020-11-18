package com.dcfs.smartaibank.manager.monitor.web.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tanchena
 * @date 2019/7/17 10:40
 */
@ApiModel(value = "NegativeCustomerInfo", description = "差评客户信息实体")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class NegativeCustomerInfo {

    @ApiModelProperty(value = "时间")
    private String time;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "客户联系方式")
    private String phone;

}
