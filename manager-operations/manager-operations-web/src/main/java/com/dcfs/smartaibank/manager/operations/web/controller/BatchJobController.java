package com.dcfs.smartaibank.manager.operations.web.controller;

import com.dcfs.smartaibank.manager.operations.web.domain.BatchHistoryQuery;
import com.dcfs.smartaibank.manager.operations.web.domain.BatchJobExecution;
import com.dcfs.smartaibank.manager.operations.web.domain.BatchStepExecution;
import com.dcfs.smartaibank.manager.operations.web.service.BatchJobService;
import com.dcfs.smartaibank.springboot.core.validation.annotation.MaxWithConfig;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 运营批处理管理模块
 *
 * @author wangjzm
 * @since 1.0.0
 */
@Api(value = "/api/v1/batch-jobs")
@RestController
@RequestMapping("/api/v1/batch-jobs")
public class BatchJobController {
	@Autowired
	BatchJobService batchJobService;

	@ApiOperation(value = "分页查询Job执行信息", notes = "分页查询Job执行信息")
	@PostMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pageSize", value = "每页记录数", dataType = "int", paramType = "query"),
		@ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", paramType = "query"),
		@ApiImplicitParam(name = "batchHistoryQuery", value = "查询参数",
			dataType = "BatchHistoryQuery", paramType = "body")
	})
	public PageInfo<BatchJobExecution> selectByPage(
		@RequestBody BatchHistoryQuery batchHistoryQuery,
		@Min(value = 0, message = "{page.pageNum.min}")
		@RequestParam(defaultValue = "1")
			Integer pageNum,
		@MaxWithConfig(value = "${page.maxPageSize}", message = "{page.pageSize.max}")
		@RequestParam(defaultValue = "${page.defaultPageSize}")
			Integer pageSize) {
		return batchJobService.selectByPage(batchHistoryQuery, pageNum, pageSize);
	}

	@ApiOperation(value = "根据job实例id查询step详情信息", notes = "根据job实例id查询step详情信息")
	@GetMapping(value = "/steps/{executionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "executionId", value = "job实例id", dataType = "string", paramType = "path")
	})
	public List<BatchStepExecution> getStepsByJobExecutionId(
		@NotNull @PathVariable String executionId) {
		return batchJobService.selectJobStepsByExecutionId(executionId);
	}

	@ApiOperation(value = "查询指定的executionId的任务是否允许重新执行或者从断点处重新执行，"
		+ "1-允许重新执行，0-不允许重新执行",
		notes = "查询指定的executionId的任务是否允许重新执行或者从断点处重新执行，1-允许重新执行，0-不允许重新执行")
	@GetMapping(value = "/break-status/{executionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "executionId", value = "job实例id", dataType = "string", paramType = "path")
	})
	public int selectAllowRestartJobInBreakPoint(@NotNull @PathVariable String executionId) {
		return batchJobService.selectAllowRestartJobInBreakPoint(executionId);
	}

	@ApiOperation(value = "从头开始执行job任务", notes = "从头开始执行job任务")
	@GetMapping(value = "/scratch-restart/{executionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "executionId", value = "job实例id", dataType = "string", paramType = "path")
	})
	public void restartJobFromScratch(@NotNull @PathVariable String executionId) {
		batchJobService.restartJobFromScratch(executionId);
	}

	@ApiOperation(value = "从断点处开始执行job任务", notes = "从断点处开始执行job任务")
	@GetMapping(value = "/break-restart/{executionId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "executionId", value = "job实例id", dataType = "string", paramType = "path")
	})
	public void restartJobFromBreakOut(@NotNull @PathVariable String executionId) {
		batchJobService.restartJobInBreakPoint(executionId);
	}
}
