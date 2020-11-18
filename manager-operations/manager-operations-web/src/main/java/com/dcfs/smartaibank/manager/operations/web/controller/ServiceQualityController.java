package com.dcfs.smartaibank.manager.operations.web.controller;

import com.dcfs.smartaibank.manager.operations.web.service.ServiceQualityService;
import com.dcfs.smartaibank.manager.operations.web.domain.QualityServiceDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.QualityServiceSumDomain;
import com.dcfs.smartaibank.manager.operations.web.utils.ParamValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 服务质量模块
 *
 * @author tanchen1
 */
@Api(value = "/api/v1/", description = "服务质量模块")
@RestController
@RequestMapping("/api/v1/")
public class ServiceQualityController {

	@Autowired
	private ServiceQualityService serviceQualityService;

	@ApiOperation(value = "服务质量模块获取多维度统计信息",
		notes = "获取机构的服务质量统计信息，包括好评率，评价率、差评率以及对应的子机构的相关服务质量排名。")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgId", value = "要查询的指定机构编号",
			dataType = "String", paramType = "path", required = true),
		@ApiImplicitParam(name = "qualityType",
			value = "服务质量类型(评价率:evaluteRate，好评率:praiseRate，差评率:navigateRate)",
			dataType = "String", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询的时间类型(day,month,quarter,year)",
			dataType = "String", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue",
			value = "查询时间值(day:YYYYMMHH-YYYYMMHH,month:YYYYMM,quarter:YYYYMM,year:YYYY)",
			dataType = "String", paramType = "path", required = true),
		@ApiImplicitParam(name = "branchStatsType", value = "机构数据统计类型 : 1-作为营业机构进行统计,2-作为上级机构进行汇总",
			dataType = "int", paramType = "path", required = true)
	})
	@GetMapping("/service-quality/{orgId}/{qualityType}/{dateType}/{timeValue}/{branchStatsType}")
	public QualityServiceDomain getServiceQuality(@PathVariable(required = true) String orgId,
												  @PathVariable(required = true) String qualityType,
												  @PathVariable(required = true) String dateType,
												  @PathVariable(required = true) String timeValue,
												  @PathVariable(required = true) Integer branchStatsType) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		ParamValidator.containsValidate("qualityType",
			qualityType, "evaluteRate,praiseRate,navigateRate");
		return serviceQualityService.getServiceQuality(orgId, dateType, timeValue, qualityType, branchStatsType);
	}

	@ApiOperation(value = "服务质量模块获取指定网点汇总评价信息",
		notes = "获取某一个机构详细的服务质量数据，包括评价率，好评率，差评率及其全行对应的相应的指标均值，对应指标的环比百分比信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgId", value = "要查询的机构编号",
			dataType = "String", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询的时间类型(day,month,quarter,year)",
			dataType = "String", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "查询时间值(day:YYYYMMHH,month:YYYYMM,quarter:YYYYMM,year:YYYY)",
			dataType = "String", paramType = "path", required = true),
		@ApiImplicitParam(name = "branchStatsType", value = "机构数据统计类型 : 1-作为营业机构进行统计,2-作为上级机构进行汇总",
			dataType = "int", paramType = "path", required = true)
	})
	@GetMapping("/service-quality-sum/{orgId}/{dateType}/{timeValue}/{branchStatsType}")
	public QualityServiceSumDomain getServiceQualitySum(@PathVariable(required = true) String orgId,
														@PathVariable(required = true) String dateType,
														@PathVariable(required = true) String timeValue,
														@PathVariable(required = true) Integer branchStatsType) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		return serviceQualityService.getServiceQualitySum(orgId, dateType, timeValue, branchStatsType);
	}

}
