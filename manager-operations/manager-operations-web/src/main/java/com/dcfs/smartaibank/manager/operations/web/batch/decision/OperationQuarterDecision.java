package com.dcfs.smartaibank.manager.operations.web.batch.decision;

import com.dcfs.smartaibank.manager.operations.web.utils.DateUtils;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 判断季报表是否执行
 *
 * @author wangjzm
 * @data
 * @since 1.0.0
 */
@Component
public class OperationQuarterDecision implements JobExecutionDecider {
	public OperationQuarterDecision() {
	}

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		Date inputDate = jobExecution.getJobParameters().getDate("inputDate");
		if (DateUtils.isFirstDayOfQuarter(inputDate)) {
			return FlowExecutionStatus.COMPLETED;
		} else {
			return FlowExecutionStatus.FAILED;
		}
	}
}
