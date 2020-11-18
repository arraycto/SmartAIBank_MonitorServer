/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Phone
 * Author:   jiazw
 * Date:     2019/4/28 15:12
 * Description: 座机电话信息
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
 * 座机电话信息
 *
 * @author jiazw
 * @since 1.0.0
 */
@ApiModel(value = "Phone", description = "座机电话信息")
@Data
@Embeddable
public class Phone {
	@ApiModelProperty(value = "国家代码")
	@NumberString(max = 8, message = "{phone.country.numberString}", groups = {InsertOrUpdate.class})
	@Column(name = "PHONE_COUNTRY")
	private String country;

	@ApiModelProperty(value = "地区区号")
	@Column(name = "PHONE_AREA")
	@Size(max = 6, message = "{phone.area.length}", groups = {InsertOrUpdate.class})
	private String area;

	@ApiModelProperty(value = "座机号码")
	@Column(name = "PHONE_NO")
	@Size(max = 8, message = "{phone.no.length}", groups = {InsertOrUpdate.class})
	private String no;

	@ApiModelProperty(value = "分机号码")
	@Column(name = "PHONE_SUBNO")
	@Size(max = 20, message = "{phone.sub.length}", groups = {InsertOrUpdate.class})
	private String sub;
}
