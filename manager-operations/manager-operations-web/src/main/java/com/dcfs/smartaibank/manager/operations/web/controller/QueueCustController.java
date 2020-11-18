package com.dcfs.smartaibank.manager.operations.web.controller;

import com.dcfs.smartaibank.manager.operations.web.utils.ParamValidator;
import com.dcfs.smartaibank.manager.operations.web.domain.QueueCustBankDomain;
import com.dcfs.smartaibank.manager.operations.web.domain.QueueCustDetailDomain;
import com.dcfs.smartaibank.manager.operations.web.service.QueueCustService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 客户排队状况控制器
 *
 * @author qiuch
 */
@Api(value = "/api/v1/", description = "排队等待时长统计模块")
@RestController
@RequestMapping(value = "/api/v1")
public class QueueCustController {
	@Autowired
	private QueueCustService service;

	/**
	 * @return java.lang.Object
	 */
	@ApiOperation(value = "查询指定机构排队等待时长详细信息",
		notes = "查询指定机构的平均排队等待时长、各时间段时长、各客户类型的排队时长等统计信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgId", value = "机构编号", dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "branchStatsType", value = "机构数据统计类型 : 1-作为营业机构进行统计,2-作为上级机构进行汇总",
			dataType = "int", paramType = "path", required = true)
	})
	@GetMapping(value = "/queue-detail/{orgId}/{dateType}/{timeValue}/{branchStatsType}", produces = "application/json")
	public QueueCustDetailDomain getQueueDetailInfo(
		@PathVariable("orgId") String orgId,
		@PathVariable("dateType") String dateType,
		@PathVariable("timeValue") String timeValue,
		@PathVariable("branchStatsType") Integer branchStatsType) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		QueueCustDetailDomain result = service.getQueueDetailInfo(orgId, dateType, timeValue, branchStatsType);
		return result;
	}

	/**
	 * @return java.lang.Object
	 */
	@ApiOperation(value = "查询指定机构下属机构排队等待时长统计排名", notes = "查询该机构下级各机构的排队时长排名信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "orgId", value = "机构编号", dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "dateType", value = "查询日期类型，只能是day,month,quarter,year中的值",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "timeValue", value = "日期值，日-yyyymmdd,月、季-yyyymm,年-yyyy",
			dataType = "string", paramType = "path", required = true),
		@ApiImplicitParam(name = "branchStatsType", value = "机构数据统计类型 : 1-作为营业机构进行统计,2-作为上级机构进行汇总",
			dataType = "int", paramType = "path", required = true)
	})
	@GetMapping(value = "queue-rank/{orgId}/{dateType}/{timeValue}/{branchStatsType}", produces = "application/json")
	public QueueCustBankDomain getQueueRankInfo(
		@PathVariable("orgId") String orgId,
		@PathVariable("dateType") String dateType,
		@PathVariable("timeValue") String timeValue,
		@PathVariable("branchStatsType") Integer branchStatsType) {
		ParamValidator.containsValidate("dateType", dateType, "day,month,quarter,year");
		QueueCustBankDomain result = service.getQueueRankInfo(orgId, dateType, timeValue, branchStatsType);
		return result;
	}

}
