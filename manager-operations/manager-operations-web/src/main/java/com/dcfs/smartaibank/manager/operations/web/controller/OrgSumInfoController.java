package com.dcfs.smartaibank.manager.operations.web.controller;

import com.dcfs.smartaibank.manager.operations.web.domain.SumInfoDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.SumItemInfoDomain;
import com.dcfs.smartaibank.manager.operations.web.service.OrgSumInfoService;
import com.dcfs.smartaibank.manager.operations.web.utils.ParamValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * 概况统计模块
 *
 * @author qiuch wangjzm
 */
@Api(value = "/api/v1/", description = "概况统计模块")
@RestController
@RequestMapping("/api/v1/")
public class OrgSumInfoController {
	@Autowired
	private OrgSumInfoService orgSumInfoService;

	@ApiOperation(value = "查询指定机构概况统计", notes = "查询指定机构的在指定时间的客流、营销、排队、服务质量等统计信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgid", value = "机构编号", dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "branchStatsType", value = "机构数据统计类型 : 1-作为营业机构进行统计,2-作为上级机构进行汇总",
			dataType = "int", paramType = "path", required = true)
	})
	@GetMapping(value = "/orgsuminfo/{orgid}/{dateType}/{timeValue}/{branchStatsType}")
	public SumInfoDomain getOrgSumInfo(@PathVariable(required = true) String orgid,
									   @PathVariable(required = true) String dateType,
									   @PathVariable(required = true) String timeValue,
									   @PathVariable(required = true) Integer branchStatsType) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		Map<String, Object> paramMap = new HashMap<String, Object>(4);
		paramMap.put("orgid", orgid);
		paramMap.put("dateType", dateType);
		paramMap.put("timeValue", timeValue);
		paramMap.put("branchStatsType", branchStatsType);
		SumInfoDomain orgSumInfo = orgSumInfoService.getOrgSumInfo(paramMap);
		return orgSumInfo;
	}

	@ApiOperation(value = "查询指定机构客流概况统计", notes = "查询指定机构的在指定时间的客流的接待数、全行均值等统计信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgid", value = "机构编号", dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "branchStatsType", value = "机构数据统计类型 : 1-作为营业机构进行统计,2-作为上级机构进行汇总",
			dataType = "int", paramType = "path", required = true)
	})
	@GetMapping(value = "/custflow/{orgid}/{dateType}/{timeValue}/{branchStatsType}")
	public SumItemInfoDomain getCustFlowSumInfo(@PathVariable(required = true) String orgid,
												@PathVariable(required = true) String dateType,
												@PathVariable(required = true) String timeValue,
												@PathVariable(required = true) Integer branchStatsType) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		Map<String, Object> paramMap = new HashMap<String, Object>(4);
		paramMap.put("orgid", orgid);
		paramMap.put("dateType", dateType);
		paramMap.put("timeValue", timeValue);
		paramMap.put("branchStatsType", branchStatsType);
		SumItemInfoDomain result = orgSumInfoService.getCustFlowSumInfo(paramMap);
		return result;
	}

	@ApiOperation(value = "查询指定机构排队概况统计", notes = "查询指定机构的在指定时间的平均等待时间、全行均值等统计信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgid", value = "机构编号", dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "branchStatsType", value = "机构数据统计类型 : 1-作为营业机构进行统计,2-作为上级机构进行汇总",
			dataType = "int", paramType = "path", required = true)
	})
	@GetMapping(value = "/queue/{orgid}/{dateType}/{timeValue}/{branchStatsType}")
	public SumItemInfoDomain getQueueSumInfo(@PathVariable String orgid,
											 @PathVariable String dateType,
											 @PathVariable String timeValue,
											 @PathVariable Integer branchStatsType) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		Map<String, Object> paramMap = new HashMap<>(4);
		paramMap.put("orgid", orgid);
		paramMap.put("dateType", dateType);
		paramMap.put("timeValue", timeValue);
		paramMap.put("branchStatsType", branchStatsType);
		SumItemInfoDomain result = orgSumInfoService.getQueueSumInfo(paramMap);
		return result;
	}

	@ApiOperation(value = "查询指定机构营销概况统计", notes = "查询指定机构的在指定时间的预约购买客户记录数、全行均值等统计信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgid", value = "机构编号", dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "branchStatsType", value = "机构数据统计类型 : 1-作为营业机构进行统计,2-作为上级机构进行汇总",
			dataType = "int", paramType = "path", required = true)
	})
	@GetMapping(value = "/hallmarket-sum/{orgid}/{dateType}/{timeValue}/{branchStatsType}")
	public SumItemInfoDomain getHallMarketSumInfo(@PathVariable String orgid,
												  @PathVariable String dateType,
												  @PathVariable String timeValue,
												  @PathVariable Integer branchStatsType) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		Map<String, Object> paramMap = new HashMap<String, Object>(4);
		paramMap.put("orgid", orgid);
		paramMap.put("dateType", dateType);
		paramMap.put("timeValue", timeValue);
		paramMap.put("branchStatsType", branchStatsType);
		SumItemInfoDomain result = orgSumInfoService.getHallMarketSumInfo(paramMap);
		return result;
	}

	@ApiOperation(value = "查询指定机构服务质量概况统计", notes = "查询指定机构的在指定时间的好评率、全行均值等统计信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgid", value = "机构编号", dataType = "string",
			paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值", dataType = "string",
			paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy", dataType = "string",
			paramType = "path", required = true),
		@ApiImplicitParam(name = "branchStatsType", value = "机构数据统计类型 : 1-作为营业机构进行统计,2-作为上级机构进行汇总",
			dataType = "int", paramType = "path", required = true)
	})
	@GetMapping(value = "/service/{orgid}/{dateType}/{timeValue}/{branchStatsType}")
	public SumItemInfoDomain getServiceQualitySumInfo(@PathVariable(required = true) String orgid,
													  @PathVariable(required = true) String dateType,
													  @PathVariable(required = true) String timeValue,
													  @PathVariable(required = true) Integer branchStatsType) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		Map<String, Object> paramMap = new HashMap<String, Object>(4);
		paramMap.put("orgid", orgid);
		paramMap.put("dateType", dateType);
		paramMap.put("timeValue", timeValue);
		paramMap.put("branchStatsType", branchStatsType);
		SumItemInfoDomain result = orgSumInfoService.getServiceQualitySumInfo(paramMap);
		return result;
	}

	@ApiOperation(value = "查询指定机构业务效率概况统计", notes = "查询指定机构的在指定时间的交易平均处理时间、全行均值等统计信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgid", value = "机构编号",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "branchStatsType", value = "机构数据统计类型 : 1-作为营业机构进行统计,2-作为上级机构进行汇总",
			dataType = "int", paramType = "path", required = true)
	})
	@GetMapping(value = "/business/{orgid}/{dateType}/{timeValue}/{branchStatsType}")
	public SumItemInfoDomain getBusiEffictSumInfo(@PathVariable(required = true) String orgid,
												  @PathVariable(required = true) String dateType,
												  @PathVariable(required = true) String timeValue,
												  @PathVariable(required = true) Integer branchStatsType) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		Map<String, Object> paramMap = new HashMap<String, Object>(4);
		paramMap.put("orgid", orgid);
		paramMap.put("dateType", dateType);
		paramMap.put("timeValue", timeValue);
		paramMap.put("branchStatsType", branchStatsType);
		SumItemInfoDomain result = orgSumInfoService.getBusiEffictSumInfo(paramMap);
		return result;
	}

}
