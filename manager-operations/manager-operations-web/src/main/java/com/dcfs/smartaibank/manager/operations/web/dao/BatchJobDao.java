package com.dcfs.smartaibank.manager.operations.web.dao;

import com.dcfs.smartaibank.manager.operations.web.domain.BatchHistoryQuery;
import com.dcfs.smartaibank.manager.operations.web.domain.BatchJobExecution;
import com.dcfs.smartaibank.manager.operations.web.domain.BatchStepExecution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 运营批处理管理DAO
 *
 * @author wangjzm
 */
@Mapper
public interface BatchJobDao {
	/**
	 * 根据条件查询
	 *
	 * @param params 批处理执行历史记录查询条件
	 * @return Org
	 */
	List<BatchJobExecution> selectByEntity(@Param("params") BatchHistoryQuery params);

	/**
	 * 根据job实例id查询step详情信息
	 *
	 * @param executionId job实例id
	 * @return step详情信息
	 */
	List<BatchStepExecution> selectJobStepsByExecutionId(@Param("executionId") String executionId);

	/**
	 * 查询最新一笔的跑批实例id
	 *
	 * @param date 当前日期
	 * @return 实例id
	 */
	String selectLatestJobExecution(@Param("nowDate") Date date);

	/**
	 * 查询最新一笔的跑批实例id
	 *
	 * @param executionId job实例id
	 * @return 实例执行状态
	 */
	String selectExecutionStatusByExecutionId(@Param("executionId") String executionId);

	/**
	 * 查询JobParameters中数据文件日期
	 *
	 * @param executionId job实例id
	 * @return 数据文件日期
	 */
	Date selectOdsDataDate(@Param("executionId") String executionId);

	/**
	 * 查询今天是否有还处于STARTED状态的任务
	 * * @param date 当前日期
	 *
	 * @return 今天处于STARTED状态任务数量
	 */
	int selectStartedBatchJob(@Param("nowDate") Date date);

	/**
	 * 查询指定id的任务是否在参数中传入uuid
	 *
	 * @param executionId job实例id
	 * @return uuid
	 */
	String selectUuidByExecutionId(@Param("executionId") String executionId);
}
