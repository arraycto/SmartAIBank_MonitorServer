package com.dcfs.smartaibank.manager.operations.web.service.impl;

import com.dcfs.smartaibank.core.exception.BusinessException;
import com.dcfs.smartaibank.manager.operations.web.dao.BatchJobDao;
import com.dcfs.smartaibank.manager.operations.web.domain.BatchHistoryQuery;
import com.dcfs.smartaibank.manager.operations.web.domain.BatchJobExecution;
import com.dcfs.smartaibank.manager.operations.web.domain.BatchStepExecution;
import com.dcfs.smartaibank.manager.operations.web.enums.BatchExecutionStatus;
import com.dcfs.smartaibank.manager.operations.web.service.BatchJobService;
import com.dcfs.smartaibank.manager.operations.web.utils.AcquireBatchStepsDescUtil;
import com.dcfs.smartaibank.manager.operations.web.utils.DateUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

/**
 * 运营批处理管理service层实现
 *
 * @author wangjzm
 * @since 1.0.0
 */
@Service
@Slf4j
public class BatchJobServiceImpl implements BatchJobService {
	@Autowired
	private BatchJobDao batchJobDao;

	@Value("${batch.import.confirm.file}")
	private String importConfirmFile;

	@Autowired
	AcquireBatchStepsDescUtil batchStepsDescUtil;

	@Autowired
	private Scheduler scheduler;

	@Autowired
	ExecutorService batchJobRestartExecutorService;

	@Override
	public PageInfo<BatchJobExecution> selectByPage(BatchHistoryQuery batchHistoryQuery, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<BatchJobExecution> list = batchJobDao.selectByEntity(batchHistoryQuery);
		list.stream().forEach(batchJobExecution -> {
			batchJobExecution.setDataDate(DateUtils.getDate(DateUtils.getLastDay(batchJobExecution.getTempDate())));
			batchJobExecution.setBatchExecutionStatusDesc(batchJobExecution.getBatchExecutionStatus().getDesc());
		});
		return new PageInfo<>(list);
	}

	@Override
	public List<BatchStepExecution> selectJobStepsByExecutionId(String executionId) {
		List<BatchStepExecution> batchStepExecutions = batchJobDao.selectJobStepsByExecutionId(executionId);
		batchStepExecutions.stream().forEach(batchStepExecution -> {
			batchStepExecution.setStepDesc(batchStepsDescUtil.getStepDescByStepId(batchStepExecution.getStepId()));
			batchStepExecution.setBatchExecutionStatusDesc(batchStepExecution.getBatchExecutionStatus().getDesc());
		});
		return batchStepExecutions;
	}

	@Override
	public int selectAllowRestartJobInBreakPoint(String executionId) {
		// 查询最新一笔的跑批实例id
		Date date = new Date();
		String latestJobExecution = batchJobDao.selectLatestJobExecution(date);
		// 查询当前传入的executionId所对应的执行状态
		String status = batchJobDao.selectExecutionStatusByExecutionId(executionId);
		if (!StringUtils.isEmpty(executionId)
			&& executionId.equals(latestJobExecution)
			&& BatchExecutionStatus.FAILED.getCode().equals(status)) {
			return 1;
		}
		return 0;
	}

	@Override
	public void restartJobFromScratch(String executionId) {
		Date date = new Date();
		Date odsDate = batchJobDao.selectOdsDataDate(executionId);
		// 1.首先检查txt源文件是否存在
		if (ObjectUtils.isEmpty(odsDate)
			|| !new File(importConfirmFile.replace("$", DateUtils.getLastDay(odsDate))).exists()) {
			throw new BusinessException("batch.ods.files.not.exist");
		}
		// 2.查询当日是否有正在执行的job
		if (batchJobDao.selectStartedBatchJob(date) > 0) {
			throw new BusinessException("batch.started.job.exist");
		}
		batchJobRestartExecutorService.execute(() -> {
			JobKey operations = new JobKey("operations-batch", "operations");
			JobDataMap jobDataMap = new JobDataMap();
			jobDataMap.put("inputDate", odsDate);
			jobDataMap.put("uuid", UUID.randomUUID().toString().replaceAll("-", ""));
			try {
				scheduler.triggerJob(operations, jobDataMap);
			} catch (SchedulerException e) {
				log.error("restartJobFromScratch（）执行异常！", e);
			}
		});
	}

	@Override
	public void restartJobInBreakPoint(String executionId) {
		Date date = new Date();
		Date odsDate = batchJobDao.selectOdsDataDate(executionId);
		String uuid = batchJobDao.selectUuidByExecutionId(executionId);
		// 1.首先检查txt源文件是否存在
		if (ObjectUtils.isEmpty(odsDate)
			|| !new File(importConfirmFile.replace("$", DateUtils.getLastDay(odsDate))).exists()) {
			throw new BusinessException("batch.ods.files.not.exist");
		}
		// 2.查询当日是否有正在执行的job
		if (batchJobDao.selectStartedBatchJob(date) > 0) {
			throw new BusinessException("batch.started.job.exist");
		}
		// 3.再次查询指定的executionId的任务是否允许从断点处重新执行
		if (this.selectAllowRestartJobInBreakPoint(executionId) == 0) {
			throw new BusinessException("batch.job.not.allowedStart");
		}
		batchJobRestartExecutorService.execute(() -> {
			JobKey operations = new JobKey("operations-batch", "operations");
			JobDataMap jobDataMap = new JobDataMap();
			jobDataMap.put("inputDate", odsDate);
			if (!StringUtils.isEmpty(uuid)) {
				jobDataMap.put("uuid", uuid);
			}
			try {
				scheduler.triggerJob(operations, jobDataMap);
			} catch (SchedulerException e) {
				log.error("restartJobFromScratch（）执行异常！", e);
			}
		});
	}
}
