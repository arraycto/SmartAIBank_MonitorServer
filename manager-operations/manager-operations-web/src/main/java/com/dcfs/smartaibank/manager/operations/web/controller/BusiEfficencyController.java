package com.dcfs.smartaibank.manager.operations.web.controller;

import com.dcfs.smartaibank.manager.operations.web.utils.ParamValidator;
import com.dcfs.smartaibank.manager.operations.web.domain.BusiEffectSumDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.HighFrequencyTradeDomain;
import com.dcfs.smartaibank.manager.operations.web.service.BusiEfficencyService;
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
 * 业务效率模块
 *
 * @author
 */
@Api(value = "/api/v1/", description = "业务效率模块")
@RestController
@RequestMapping("/api/v1/")
public class BusiEfficencyController {
	@Autowired
	private BusiEfficencyService busiEfficencyService;

	@ApiOperation(value = "查询指定机构业务效率汇总信息",
		notes = "查询指定机构的在指定时间的业务总量、总量全行均值、交易平均处理时间、全行均值等统计信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgid", value = "机构编号", dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "branchStatsType", value = "机构运营类型 默认为0，营业兼管理机构类型时，营业=1，管理=2",
			dataType = "int", paramType = "path", required = true)
	})
	@GetMapping(value = "/busi-efficiency/{orgid}/{dateType}/{timeValue}/{branchStatsType}")
	public BusiEffectSumDomain getBusinessDetailInfo(@PathVariable(required = true) String orgid,
													 @PathVariable(required = true) String dateType,
													 @PathVariable(required = true) String timeValue,
													 @PathVariable(required = true) Integer branchStatsType) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		Map<String, Object> paramMap = new HashMap<String, Object>(4);
		paramMap.put("orgid", orgid);
		paramMap.put("dateType", dateType);
		paramMap.put("timeValue", timeValue);
		paramMap.put("branchStatsType", branchStatsType);
		BusiEffectSumDomain result = busiEfficencyService.getBusinessDetailInfo(paramMap);
		return result;
	}

	@ApiOperation(value = "查询指定机构子机构的业务排名、交易处理时间top10",
		notes = "查询指定机构的在指定时间的业务总量、交易平均处理时间排名等统计信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgid", value = "机构编号", dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "branchStatsType", value = "机构运营类型 默认为0，营业兼管理机构类型时，营业=1，管理=2",
			dataType = "int", paramType = "path", required = true)
	})
	@GetMapping(value = "/busi-efficiency-rank/{orgid}/{dateType}/{timeValue}/{branchStatsType}")
	public BusiEffectSumDomain getSubBranchBusinessRank(@PathVariable(required = true) String orgid,
														@PathVariable(required = true) String dateType,
														@PathVariable(required = true) String timeValue,
														@PathVariable(required = true) Integer branchStatsType) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		Map<String, Object> paramMap = new HashMap<String, Object>(4);
		paramMap.put("orgid", orgid);
		paramMap.put("dateType", dateType);
		paramMap.put("timeValue", timeValue);
		paramMap.put("branchStatsType", branchStatsType);
		BusiEffectSumDomain result = busiEfficencyService.getSubBranchBusinessRank(paramMap);
		return result;
	}

	@ApiOperation(value = "查询指定机构高频交易top20、耗时交易top20", notes = "查询指定机构指定时间内的高频交易top20、耗时交易top20")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgid", value = "机构编号", dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "tradeType", value = "查询交易类型：1-高频交易 2-耗时交易",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "branchStatsType", value = "机构运营类型 默认为0，营业兼管理机构类型时，营业=1，管理=2",
			dataType = "int", paramType = "path", required = true)
	})
	@GetMapping(value = "/high-frequency-trade/{orgid}/{tradeType}/{dateType}/{timeValue}/{branchStatsType}")
	public BusiEffectSumDomain getHignFrequencyTrade(@PathVariable(required = true) String orgid,
													 @PathVariable(required = true) String tradeType,
													 @PathVariable(required = true) String dateType,
													 @PathVariable(required = true) String timeValue,
													 @PathVariable(required = true) Integer branchStatsType) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		ParamValidator.containsValidate("tradeType", tradeType, "1,2");
		Map<String, Object> paramMap = new HashMap<String, Object>(8);
		paramMap.put("orgid", orgid);
		paramMap.put("tradeType", tradeType);
		paramMap.put("dateType", dateType);
		paramMap.put("timeValue", timeValue);
		paramMap.put("branchStatsType", branchStatsType);
		BusiEffectSumDomain result = busiEfficencyService.getHignFrequencyTradeRank(paramMap);
		return result;
	}

	@ApiOperation(value = "查询指定交易的交易量、平均处理时间的变化趋势信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgid", value = "机构编号", dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "tradeCode", value = "查询的交易码", dataType = "string", paramType = "path"),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "tranFlag", value = "查询交易的标识：1-高频交易 2-耗时交易",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "branchStatsType", value = "机构运营类型 默认为0，营业兼管理机构类型时，营业=1，管理=2",
			dataType = "int", paramType = "path", required = true)
	})
	@GetMapping(value = "/trade-trend/{orgid}/{tradeCode}/{dateType}/{timeValue}/{tranFlag}/{branchStatsType}")
	public HighFrequencyTradeDomain getHignFrequencyTradeTrend(@PathVariable(required = true) String orgid,
															   @PathVariable(required = true) String tradeCode,
															   @PathVariable(required = true) String dateType,
															   @PathVariable(required = true) String timeValue,
															   @PathVariable(required = true) String tranFlag,
															   @PathVariable(required = true) Integer branchStatsType) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		ParamValidator.containsValidate("tranFlag", tranFlag, "1,2");
		Map<String, Object> paramMap = new HashMap<String, Object>(8);
		paramMap.put("orgid", orgid);
		paramMap.put("dateType", dateType);
		paramMap.put("tradeType", tradeCode);
		paramMap.put("timeValue", timeValue);
		paramMap.put("tranFlag", tranFlag);
		paramMap.put("branchStatsType", branchStatsType);
		HighFrequencyTradeDomain result = busiEfficencyService.getTradeTrend(paramMap);
		return result;
	}
}
