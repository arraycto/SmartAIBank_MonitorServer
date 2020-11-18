package com.dcfs.smartaibank.manager.operations.web.controller;

import com.dcfs.smartaibank.manager.operations.web.domain.TellerViewPersonalDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.TellerViewRankAndDetailDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.TranTop20Domain;
import com.dcfs.smartaibank.manager.operations.web.utils.ParamValidator;
import com.dcfs.smartaibank.manager.operations.web.service.TellerViewService;
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
 * 柜员视角考核信息统计控制器
 *
 * @author qiuch
 */
@Api(value = "/api/v1/", description = "柜员视角统计模块")
@RestController
@RequestMapping("/api/v1/")
public class TellerViewController {
	@Autowired
	private TellerViewService service;

	@ApiOperation(value = "查询指定机构下交易处理时长-柜员视角详细信息", notes = "查询指定机构下各柜员交易处理时长的统计信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgId", value = "机构编号", dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true)
	})
	@GetMapping(value = "/teller-view/busi-efficiency/tranHandleTime/{orgId}/{dateType}/{timeValue}",
		produces = "application/json")
	public TellerViewRankAndDetailDomain getHandleTimeInfo(
		@PathVariable("orgId") String orgId,
		@PathVariable("dateType") String dateType,
		@PathVariable("timeValue") String timeValue) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		Map<String, Object> paramMap = createMap(orgId, dateType, timeValue);
		TellerViewRankAndDetailDomain result = service.getHandleTimeInfo(paramMap);
		return result;
	}

	@ApiOperation(value = "查询指定机构下交易数量-柜员视角详细信息", notes = "查询指定机构下各柜员交易处理数量的统计信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgId", value = "机构编号", dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true)
	})
	@GetMapping(value = "/teller-view/busi-efficiency/tranCount/{orgId}/{dateType}/{timeValue}",
		produces = "application/json")
	public TellerViewRankAndDetailDomain getTranCountInfo(
		@PathVariable("orgId") String orgId,
		@PathVariable("dateType") String dateType,
		@PathVariable("timeValue") String timeValue) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		Map<String, Object> paramMap = createMap(orgId, dateType, timeValue);
		TellerViewRankAndDetailDomain result = service.getTranCountInfo(paramMap);
		return result;
	}

	@ApiOperation(value = "查询指定机构下柜员视角-高频交易详细信息", notes = "查询指定机构下柜员视角-高频交易统计详情")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgId", value = "机构编号", dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true)
	})
	@GetMapping(value = "/teller-view/busi-efficiency/TranTop20HandleTime/{orgId}/{dateType}/{timeValue}",
		produces = "application/json")
	public TranTop20Domain getTranTop20HandleTimeInfo(
		@PathVariable("orgId") String orgId,
		@PathVariable("dateType") String dateType,
		@PathVariable("timeValue") String timeValue) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		Map<String, Object> paramMap = createMap(orgId, dateType, timeValue);
		TranTop20Domain result = service.getTranTop20HandleTimeInfo(paramMap);
		return result;
	}

	@ApiOperation(value = "查询指定机构下服务质量-柜员视角详细信息",
		notes = "查询指定机构下各柜员服务质量的统计信息，包括评价率、好评率、差评率")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "qualityType", value = "服务质量类型，只能是evaluateRate、positiveRate、negativeRate中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "orgId", value = "机构编号", dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true)
	})
	@GetMapping(value = "/teller-view/service-quality/{qualityType}/{orgId}/{dateType}/{timeValue}",
		produces = "application/json")
	public TellerViewRankAndDetailDomain getEvaluateInfo(
		@PathVariable("qualityType") String qualityType,
		@PathVariable("orgId") String orgId,
		@PathVariable("dateType") String dateType,
		@PathVariable("timeValue") String timeValue) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		Map<String, Object> paramMap = createMap(orgId, dateType, timeValue);
		paramMap.put("type", qualityType);
		TellerViewRankAndDetailDomain result = service.getServiceQualityInfo(paramMap);
		return result;
	}

	@ApiOperation(value = "查询指定机构下柜员视角个人详细信息", notes = "查询指定机构下柜员个人视角各维度统计详情")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgId", value = "机构编号", dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true)
	})
	@GetMapping(value = "/teller-view/personal/{tellerId}/{orgId}/{dateType}/{timeValue}",
		produces = "application/json")
	public TellerViewPersonalDomain getPersonalDetailInfo(
		@PathVariable("tellerId") String tellerId,
		@PathVariable("orgId") String orgId,
		@PathVariable("dateType") String dateType,
		@PathVariable("timeValue") String timeValue) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		Map<String, Object> paramMap = createMap(orgId, dateType, timeValue);
		paramMap.put("tellerId", tellerId);
		TellerViewPersonalDomain result = service.getPersonalDetailInfo(paramMap);
		return result;
	}

	private Map<String, Object> createMap(String orgId, String dateType, String timeValue) {
		Map<String, Object> paramMap = new HashMap<>(16);
		paramMap.put("ORG_ID", orgId);
		paramMap.put("DATE_TYPE", dateType);
		paramMap.put("TIME_VALUE", timeValue);
		return paramMap;
	}
}
