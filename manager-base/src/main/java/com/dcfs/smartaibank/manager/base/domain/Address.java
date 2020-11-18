package com.dcfs.smartaibank.manager.base.domain;

import com.dcfs.smartaibank.springboot.core.validation.group.InsertOrUpdate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

/**
 * 地址信息
 *
 * @author jiazw
 */
@ApiModel(value = "Address", description = "地址信息")
@Data
@Embeddable
public class Address {
	@ApiModelProperty(value = "国家代码")
	@Column(name = "ADDRESS_COUNTRY")
	private String country;

	@ApiModelProperty(value = "省代码")
	@Column(name = "ADDRESS_PROVINCE")
	private String province;

	@ApiModelProperty(value = "市代码")
	@Column(name = "ADDRESS_CITY")
	private String city;

	@ApiModelProperty(value = "区/县代码")
	@Column(name = "ADDRESS_COUNTY")
	private String county;

	@ApiModelProperty(value = "镇代码")
	@Column(name = "ADDRESS_TOWN")
	private String town;

	@ApiModelProperty(value = "街道")
	@Column(name = "ADDRESS_STREET")
	@Size(max = 50, message = "{address.street.size}", groups = {InsertOrUpdate.class})
	private String street;

    /*机构和用户没有邮编
    @ApiModelProperty(value = "邮编")
    @Column(name = "POSTNO")
    @Size(min = 6, max=6,message = "{address.zipcode.size}", groups = {InsertOrUpdate.class})
    private String zipCode;*/
}
