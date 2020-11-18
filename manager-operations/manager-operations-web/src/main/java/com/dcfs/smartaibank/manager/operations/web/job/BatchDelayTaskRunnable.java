package com.dcfs.smartaibank.manager.operations.web.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;

import java.io.File;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author wangjzm
 * @since 1.0.0
 */
@Slf4j
public class BatchDelayTaskRunnable implements Runnable {
	private SimpleJobLauncher jobLauncher;
	private JobParameters jobParameters;
	private Job smartBatchJob;
	private ScheduledExecutorService batchScheduledExecutorService;
	private File confirmFile;
	private Integer batchScheduledDelayTime;

	public BatchDelayTaskRunnable(SimpleJobLauncher jobLauncher,
								  JobParameters jobParameters,
								  Job smartBatchJob,
								  ScheduledExecutorService batchScheduledExecutorService,
								  File confirmFile,
								  Integer batchScheduledDelayTime) {
		this.jobLauncher = jobLauncher;
		this.jobParameters = jobParameters;
		this.smartBatchJob = smartBatchJob;
		this.batchScheduledExecutorService = batchScheduledExecutorService;
		this.confirmFile = confirmFile;
		this.batchScheduledDelayTime = batchScheduledDelayTime;
	}

	@Override
	public void run() {
		if (!odsDataConfirm(confirmFile)) {
			log.warn("跑批确认文件不存在，创建schedule线程30min中后重新执行！");
			batchScheduledExecutorService.schedule(
				new BatchDelayTaskRunnable(jobLauncher,
					jobParameters, smartBatchJob, batchScheduledExecutorService, confirmFile, batchScheduledDelayTime),
				batchScheduledDelayTime, TimeUnit.MINUTES);
		} else {
			log.info("批处理smartBatchJob执行开始");
			try {
				JobExecution jobExecution = jobLauncher.run(smartBatchJob, jobParameters);
				log.info("批处理smartBatchJob执行完成，执行后的任务状态为：{}", jobExecution.getStatus());
			} catch (Exception e) {
				log.error("批处理smartBatchJob执行失败！", e);
			}
		}
	}

	private boolean odsDataConfirm(File file) {
		return file.exists();
	}
}
