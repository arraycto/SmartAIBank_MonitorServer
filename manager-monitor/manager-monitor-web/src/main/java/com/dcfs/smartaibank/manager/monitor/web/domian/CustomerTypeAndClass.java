package com.dcfs.smartaibank.manager.monitor.web.domian;

import com.dcfs.smartaibank.manager.monitor.web.enums.CustomerClassEnum;
import com.dcfs.smartaibank.manager.monitor.web.enums.CustomerTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 客户类型、客户级别实体类
 *
 * @author wangjzm
 * @data 2019/07/22 09:21
 * @since 1.0.0
 */
@ApiModel(value = "CustomerTypeAndClass", description = "客户类型、客户级别实体类")
@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTypeAndClass {
    @ApiModelProperty(value = "客户类型（个人客户、公司客户）")
    CustomerTypeEnum customerTypeEnum;

    @ApiModelProperty(value = "客户级别（VIP客户、普通客户）")
    CustomerClassEnum customerClassEnum;
}
