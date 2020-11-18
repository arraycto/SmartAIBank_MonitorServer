package com.dcfs.smartaibank.manager.operations.web.controller;

import com.dcfs.smartaibank.manager.operations.web.utils.ParamValidator;
import com.dcfs.smartaibank.manager.operations.web.domain.CustFlowDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.CustFlowSumDomain;
import com.dcfs.smartaibank.manager.operations.web.service.CustFlowDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 客流模块
 *
 * @author
 */
@Api(value = "/api/v1/", description = "客流模块")
@RestController
@RequestMapping("/api/v1/")
public class CustFlowController {

	@Autowired
	private CustFlowDataService custFlowDataService;

	@ApiOperation(value = "查询机构客流汇总信息", notes = "查询指定机构的在指定时间接待客户数及其随时间变化的规律统计")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgid", value = "机构编号",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "branchStatsType", value = "机构类别，1-营业机构，2-管理机构",
			dataType = "int", paramType = "path", required = true)
	})
	@GetMapping(value = "/custflow-detail/{orgid}/{dateType}/{timeValue}/{branchStatsType}")
	public CustFlowSumDomain getCustFlowDetialInfo(@PathVariable(required = true) String orgid,
												   @PathVariable(required = true) String dateType,
												   @PathVariable(required = true) String timeValue,
												   @PathVariable(required = true) Integer branchStatsType) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		Map<String, Object> paramMap = new HashMap<String, Object>(4);
		paramMap.put("orgid", orgid);
		paramMap.put("dateType", dateType);
		paramMap.put("timeValue", timeValue);
		paramMap.put("branchStatsType", branchStatsType);
		CustFlowSumDomain custFlowDetailInfo = custFlowDataService.getCustFlowDetailInfo(paramMap);
		CustFlowSumDomain result = custFlowDetailInfo;
		return result;
	}

	@ApiOperation(value = "查询子机构客流排名信息", notes = "查询指定机构的子机构在指定时间接待客户数排名统计")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgid", value = "机构编号",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "branchStatsType", value = "机构类别，1-营业机构，2-管理机构",
			dataType = "int", paramType = "path", required = true)
	})
	@GetMapping(value = "/custflow-rank/{orgid}/{dateType}/{timeValue}/{branchStatsType}")
	public CustFlowDomain getSubBranchCustFlowRank(@PathVariable(required = true) String orgid,
												   @PathVariable(required = true) String dateType,
												   @PathVariable(required = true) String timeValue,
												   @PathVariable(required = true) Integer branchStatsType) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		Map<String, Object> paramMap = new HashMap<String, Object>(4);
		paramMap.put("orgid", orgid);
		paramMap.put("dateType", dateType);
		paramMap.put("timeValue", timeValue);
		paramMap.put("branchStatsType", branchStatsType);
		CustFlowDomain result = custFlowDataService.getSubBranchCustFlowRank(paramMap);
		return result;
	}
}
