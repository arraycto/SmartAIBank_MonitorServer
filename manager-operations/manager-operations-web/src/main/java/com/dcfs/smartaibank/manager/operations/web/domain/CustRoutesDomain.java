package com.dcfs.smartaibank.manager.operations.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 客户动线实体
 *
 * @author
 */
@ApiModel(value = "CustRoutesDomain", description = "客户动线实体")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CustRoutesDomain {

	@ApiModelProperty(value = "客户动线信息")
	private String custRoutes;

	@ApiModelProperty(value = "客户动线数量")
	private Long routesCount;

	@ApiModelProperty(value = "客户动线列表")
	private List<CustRoutesDomain> routeList;
}
