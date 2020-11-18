package com.dcfs.smartaibank.manager.operations.web.controller;

import com.dcfs.smartaibank.manager.operations.web.constant.Constant;
import com.dcfs.smartaibank.manager.operations.web.utils.ParamValidator;
import com.dcfs.smartaibank.manager.operations.web.domain.BusinessDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.HallMarketDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.HallMarketSumDomain;
import com.dcfs.smartaibank.manager.operations.web.service.HallMarketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * 厅堂营销模块
 *
 * @author
 */
@Api(value = "/api/v1/", description = "厅堂营销模块")
@RestController
@RequestMapping("/api/v1/")
@Validated
@Slf4j
public class HallMarketController {
	@Autowired
	private HallMarketService hallMarketService;

	@ApiOperation(value = "查询机构营销信息", notes = "查询指定机构的在指定时间的营销客户数、"
		+ "营销产品数、潜在客户登记数、预约购买客户数及其随时间变化的规律统计")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgid", value = "机构编号",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "branchStatsType", value = "机构运营类型 默认为0，营业兼管理机构类型时，营业=1，管理=2",
			dataType = "int", paramType = "path", required = true)
	})
	@GetMapping(value = "/hallmarket/{orgid}/{dateType}/{timeValue}/{branchStatsType}")
	public HallMarketSumDomain getHallMarketSumInfo(@PathVariable(required = true) String orgid,
													@PathVariable(required = true) String dateType,
													@PathVariable(required = true) String timeValue,
													@PathVariable(required = true) Integer branchStatsType) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		Map<String, Object> paramMap = new HashMap<String, Object>(4);
		paramMap.put("orgid", orgid);
		paramMap.put("dateType", dateType);
		paramMap.put("timeValue", timeValue);
		paramMap.put("branchStatsType", branchStatsType);
		HallMarketSumDomain result = hallMarketService.getHallMarketSuminfo(paramMap);
		return result;
	}

	@ApiOperation(value = "查询网点的业务接收类型和客户接收类型", notes = "查询指定机构的在指定时间的客户接待情况及业务受理情况统计信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgid", value = "机构编号",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "branchStatsType", value = "机构运营类型 默认为0，营业兼管理机构类型时，营业=1，管理=2",
			dataType = "int", paramType = "path", required = true)
	})
	@GetMapping(value = "/hallmarket-detail/{orgid}/{dateType}/{timeValue}/{branchStatsType}")
	public BusinessDomain getHallMarketBusinDetail(@PathVariable(required = true) String orgid,
												   @PathVariable(required = true) String dateType,
												   @PathVariable(required = true) String timeValue,
												   @PathVariable(required = true) Integer branchStatsType) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		Map<String, Object> paramMap = new HashMap<String, Object>(4);
		paramMap.put("orgid", orgid);
		paramMap.put("dateType", dateType);
		paramMap.put("timeValue", timeValue);
		paramMap.put("branchStatsType", branchStatsType);
		BusinessDomain result = hallMarketService.getHallMarketBusinDetail(paramMap);
		return result;
	}

	@ApiOperation(value = "查询指定机构指定员工的营销信息--大堂经理看板的信息", notes = "查询指定机构"
		+ "的指定柜员在指定时间的客户接待情况及业务受理情况统计信息、并"
		+ "且统计该柜员的营销客户数、营销产品数、潜在客户登记数、预约购买客户数")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgid", value = "机构编号",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "tellerid", value = "柜员编号",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,mo"
			+ "nth,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "branchStatsType", value = "机构运营类型 默认为0，营业兼管理机构类型时，营业=1，管理=2",
			dataType = "int", paramType = "path", required = true)
	})
	@GetMapping(value = "/hallmarket-detail/{orgid}/{tellerid}/{dateType}/{timeValue}/{branchStatsType}")
	public HallMarketSumDomain getUserHallMarketBusinDetail(@PathVariable(required = true) String orgid,
															@PathVariable(required = true) String tellerid,
															@PathVariable(required = true) String dateType,
															@PathVariable(required = true) String timeValue,
															@PathVariable(required = true) Integer branchStatsType) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		Map<String, Object> paramMap = new HashMap<String, Object>(8);
		paramMap.put("orgid", orgid);
		paramMap.put("tellerid", tellerid);
		paramMap.put("dateType", dateType);
		paramMap.put("timeValue", timeValue);
		paramMap.put("branchStatsType", branchStatsType);
		HallMarketSumDomain result = hallMarketService.getHallMarketBusinUserDetail(paramMap);
		return result;
	}

	@ApiOperation(value = "查询子机构营销排名信息", notes = "查询指定机构的子机构在指定时间的"
		+ "营销客户数排名、营销产品数排名、潜在客户登记数排名、预约购买客户数排名")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgid", value = "机构编号",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "branchStatsType", value = "机构运营类型 默认为0，营业兼管理机构类型时，营业=1，管理=2",
			dataType = "int", paramType = "path", required = true)
	})
	@GetMapping(value = "/hallmarket-rank/{orgid}/{dateType}/{timeValue}/{branchStatsType}")
	public HallMarketSumDomain getSubBranchHallMarketRank(@PathVariable(required = true) String orgid,
														  @PathVariable(required = true) String dateType,
														  @PathVariable(required = true) String timeValue,
														  @PathVariable(required = true) Integer branchStatsType) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		Map<String, Object> paramMap = new HashMap<String, Object>(4);
		paramMap.put("orgid", orgid);
		paramMap.put("dateType", dateType);
		paramMap.put("timeValue", timeValue);
		paramMap.put("branchStatsType", branchStatsType);
		HallMarketSumDomain result = hallMarketService.getSubBranchHallMarketRank(paramMap);
		return result;
	}

	@ApiOperation(value = "查询柜员营销排名信息", notes = "查询指定机构的柜员在指定时间的营销客户数排名、营销"
		+ "产品数排名、潜在客户登记数排名、预约购买客户数排名top3")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgid", value = "查询的网点编号",
			dataType = "String", paramType = "path", required = true),
		@ApiImplicitParam(name = "tranCode",
			value = "营销客户数：marketingCustomer，潜在客户登记数：potentialCustomer，营销产品次数：marketingProduct,"
				+ "预约购买登记数:purchasesRegistered)",
			dataType = "String", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询的时间类型(day,month,quarter,year)",
			dataType = "String", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "查询时间值(day:YYYYMMHH,month:YYYYMM,quarter:YYYYMM,year:YYYY)",
			dataType = "String", paramType = "path", required = true),
		@ApiImplicitParam(name = "branchStatsType", value = "机构运营类型 默认为0，营业兼管理机构类型时，营业=1，管理=2",
			dataType = "int", paramType = "path", required = true)
	})
	@RequestMapping(value = "/hallmarket-user/{orgid}/{tranCode}/{dateType}/{timeValue}/{branchStatsType}",
		method = RequestMethod.GET)
	public HallMarketDomain getUserHallMarketRank(@PathVariable(required = true) String orgid,
												  @PathVariable(required = true) String tranCode,
												  @PathVariable(required = true) String dateType,
												  @PathVariable(required = true) String timeValue,
												  @PathVariable(required = true) Integer branchStatsType) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		ParamValidator.containsValidate("tranCode", tranCode,
			"marketingCustomer,marketingProduct,potentialCustomer,purchasesRegistered");
		Map<String, Object> paramMap = new HashMap<String, Object>(16);
		paramMap.put("orgid", orgid);
		tranTranCode(paramMap, tranCode);
		paramMap.put("dateType", dateType);
		paramMap.put("timeValue", timeValue);
		paramMap.put("branchStatsType", branchStatsType);
		HallMarketDomain result = hallMarketService.getUserHallMarketRank(paramMap);
		return result;
	}

	private void tranTranCode(Map<String, Object> paramMap, String tranCode) {
		if (Constant.MARKETING_CUSTOMER.equals(tranCode)) {
			paramMap.put("tradeType", "marketCustomer");
			paramMap.put("saleType", "identify");
		}
		if (Constant.MARKETING_PRODUCT.equals(tranCode)) {
			paramMap.put("tradeType", "marketProduct");
			paramMap.put("saleType", "product");
		}
		if (Constant.POTENTIAL_CUSTOMER.equals(tranCode)) {
			paramMap.put("tradeType", "potential");
			paramMap.put("saleType", "protential");
		}
		if (Constant.PURCHASES_REGISTERED.equals(tranCode)) {
			paramMap.put("tradeType", "reserveBuy");
			paramMap.put("saleType", "reserve");
		}
	}
}
