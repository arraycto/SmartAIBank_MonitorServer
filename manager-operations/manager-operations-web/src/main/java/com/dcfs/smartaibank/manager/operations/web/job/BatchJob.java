/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: BatchJob
 * Author:   jiazw
 * Date:     2019/3/13 11:11
 * Description: 批处理任务
 * History:
 * 作者姓名           修改时间           版本号              描述
 */
package com.dcfs.smartaibank.manager.operations.web.job;

import com.dcfs.smartaibank.manager.operations.web.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.File;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 批处理任务
 * 不允许并发执行
 *
 * @author jiazw
 * @since 1.0.0
 */
@DisallowConcurrentExecution
@Slf4j
public class BatchJob extends QuartzJobBean {
	@Autowired
	Environment env;

	@Autowired
	@Qualifier("smartBatchJob")
	private Job smartBatchJob;

	@Value("${batch.import.customer.trade.file.path}")
	private String tradeFile;

	@Value("${batch.import.customer.private.file.path}")
	private String customerInfoFile;

	@Value("${batch.import.customer.public.file.path}")
	private String publicCustomerInfoFile;

	@Value("${batch.import.customer.private.assets.file.path}")
	private String privateCustomerInfoFile;

	@Value("${batch.import.confirm.file}")
	private String importConfirmFile;

	@Value("${batch.scheduled.delay-time}")
	private Integer batchScheduledDelayTime;
	@Autowired
	private SimpleJobLauncher batchJobLauncher;

	@Autowired
	private ScheduledExecutorService batchScheduledExecutorService;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		JobDataMap mergedJobDataMap = jobExecutionContext.getMergedJobDataMap();
		Date date = getDate(mergedJobDataMap);
		String lastDay = DateUtils.getLastDay(date);
		JobParametersBuilder builder = new JobParametersBuilder()
			.addString("batch.import.customer.trade.file.path",
				tradeFile + lastDay + ".txt")
			.addString("batch.import.customer.private.file.path",
				customerInfoFile + lastDay + ".txt")
			.addString("batch.import.customer.public.file.path",
				publicCustomerInfoFile + lastDay + ".txt")
			.addString("batch.import.customer.private.assets.file.path",
				privateCustomerInfoFile + lastDay + ".txt")
			.addDate("inputDate", date);
		Object uuid = mergedJobDataMap.get("uuid");
		if (uuid != null) {
			builder.addString("uuid", (String) uuid);
		}
		JobParameters jobParameters = builder.toJobParameters();
		// 此处是和ods约定的标记，当该方法返回值为true时，才进行跑批流程
		File confirmFile = new File(importConfirmFile.replace("$", lastDay));
		if (!odsDataConfirm(confirmFile)) {
			log.warn("跑批确认文件不存在，创建schedule线程30min中后重新执行！");
			// 发现ods未完成数据推送则创建一个定时任务线程重新执行
			batchScheduledExecutorService.schedule(
				new BatchDelayTaskRunnable(batchJobLauncher,
					jobParameters, smartBatchJob, batchScheduledExecutorService, confirmFile, batchScheduledDelayTime),
				batchScheduledDelayTime, TimeUnit.MINUTES);
		} else {
			log.info("批处理smartBatchJob执行开始");
			try {
				JobExecution jobExecution = batchJobLauncher.run(smartBatchJob, jobParameters);
				log.info("批处理smartBatchJob执行完成，执行后的任务状态为：{}", jobExecution.getStatus());
			} catch (Exception e) {
				log.error("批处理smartBatchJob执行失败！", e);
			}
		}
	}

	private Date getDate(JobDataMap mergedJobDataMap) {
		// 从传入的Context对象中获取可能存在的输入日期
		Object inputDate = mergedJobDataMap.get("inputDate");
		Date date;
		// 如果存在输入的日期，那么整个跑批过程将优先采用传入日期，其次是spring environment日期，最后采用当天日期
		if (inputDate != null) {
			return (Date) inputDate;
		} else {
			String dateStr = env.getProperty("batch.date.str");
			date = DateUtils.getDate(dateStr);
		}
		// 将获取到的日期-1，得到前一天日期
		return date;
	}

	private boolean odsDataConfirm(File file) {
		return file.exists();
	}
}
