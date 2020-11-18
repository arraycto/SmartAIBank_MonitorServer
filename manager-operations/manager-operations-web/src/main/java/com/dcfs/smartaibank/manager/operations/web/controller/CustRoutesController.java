package com.dcfs.smartaibank.manager.operations.web.controller;

import com.dcfs.smartaibank.manager.operations.web.domain.CustRoutesDomain;
import com.dcfs.smartaibank.manager.operations.web.service.CustRoutesService;
import com.dcfs.smartaibank.manager.operations.web.utils.ParamValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户动线模块
 *
 * @author
 */
@Api(value = "/api/v1/", description = "客户动线模块")
@RequestMapping("/api/v1/")
@RestController
public class CustRoutesController {
	@Autowired
	private CustRoutesService custRoutesService;

	@ApiOperation(value = "查询指定机构的客户动线信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgid", value = "机构编号",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true)
	})
	@GetMapping(value = "/cust-routes/{orgid}/{dateType}/{timeValue}")
	public CustRoutesDomain getCustRoutesInfo(@PathVariable(required = true) String orgid,
											  @PathVariable(required = true) String dateType,
											  @PathVariable(required = true) String timeValue) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		Map<String, Object> paramMap = new HashMap<String, Object>(4);
		paramMap.put("orgid", orgid);
		paramMap.put("dateType", dateType);
		paramMap.put("timeValue", timeValue);
		CustRoutesDomain result = custRoutesService.getCustRoutesDetailInfo(paramMap);
		return result;

	}
}
