package com.dcfs.smartaibank.manager.operations.web.service;


import com.dcfs.smartaibank.manager.operations.web.domain.BatchHistoryQuery;
import com.dcfs.smartaibank.manager.operations.web.domain.BatchJobExecution;
import com.dcfs.smartaibank.manager.operations.web.domain.BatchStepExecution;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 运营批处理管理service层
 *
 * @author wangjzm
 * @since 1.0.0
 */
public interface BatchJobService {
	/**
	 * 分页查询批处理执行摘要信息
	 *
	 * @param batchHistoryQuery 批处理执行历史记录查询条件
	 * @param pageNum           页号
	 * @param pageSize          页大小
	 * @return Job执行信息
	 */
	PageInfo<BatchJobExecution> selectByPage(BatchHistoryQuery batchHistoryQuery, int pageNum, int pageSize);

	/**
	 * 根据job实例id查询step详情信息
	 *
	 * @param executionId job实例id
	 * @return step详情信息
	 */
	List<BatchStepExecution> selectJobStepsByExecutionId(String executionId);

	/**
	 * 查询指定的executionId的任务是否允许从断点处重新执行
	 * <p>
	 * 规则如下：
	 * 当满足以下两个条件时，才允许从断点处重新执行
	 * 1.任务是当天最新一次的执行
	 * 2.任务为失败状态
	 *
	 * </p>
	 *
	 * @param executionId job实例id
	 * @return 1-允许重新执行，0-不允许重新执行
	 */
	int selectAllowRestartJobInBreakPoint(String executionId);

	/**
	 * 从头开始执行job任务
	 * <p>
	 * 1.首先检查txt源文件是否存在
	 * 2.查询当日是否有正在执行的job
	 * </p>
	 *
	 * @param executionId job实例id
	 */
	void restartJobFromScratch(String executionId);

	/**
	 * 从头开始执行job任务
	 * <p>
	 * 1.首先检查txt源文件是否存在
	 * 2.查询当日是否有正在执行的job
	 * 3.需要再次查询指定的executionId的任务是否允许从断点处重新执行
	 * </p>
	 *
	 * @param executionId job实例id
	 */
	void restartJobInBreakPoint(String executionId);
}
