/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Mobile
 * Author:   jiazw
 * Date:     2019/4/30 17:55
 * Description: 手机信息
 * History:
 * 作者姓名           修改时间           版本号              描述
 */
package com.dcfs.smartaibank.manager.base.domain;

import com.dcfs.smartaibank.springboot.core.validation.annotation.NumberString;
import com.dcfs.smartaibank.springboot.core.validation.group.InsertOrUpdate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

/**
 * 手机信息
 *
 * @author jiazw
 * @since 1.0.0
 */
@ApiModel(value = "Mobile", description = "手机信息")
@Data
@Embeddable
public class Mobile {
	@ApiModelProperty(value = "国家代码")
	@Column(name = "MOBILE_COUNTRY")
	@NumberString(max = 4, message = "{mobile.country.numberString}", groups = {InsertOrUpdate.class})
	private String country;

	@ApiModelProperty(value = "手机号码")
	@Column(name = "MOBILE_NO")
	@Size(max = 11, message = "{mobile.no.size}", groups = {InsertOrUpdate.class})
	private String no;
}
