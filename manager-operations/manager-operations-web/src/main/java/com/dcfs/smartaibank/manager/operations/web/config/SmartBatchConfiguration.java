package com.dcfs.smartaibank.manager.operations.web.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 智能运营赔处理配置
 *
 * @author
 */
@EnableBatchProcessing
@Configuration
@ImportResource("classpath:batch/batch-job.xml")
@Slf4j
public class SmartBatchConfiguration {

	/**
	 * @param dataSource
	 * @param transactionManager
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public JobRepositoryFactoryBean jobRepositoryFactoryBean(DataSource dataSource,
															 PlatformTransactionManager transactionManager) {
		JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
		jobRepositoryFactoryBean.setTransactionManager(transactionManager);
		jobRepositoryFactoryBean.setDataSource(dataSource);
		jobRepositoryFactoryBean.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
		try {
			jobRepositoryFactoryBean.afterPropertiesSet();
		} catch (Exception e) {
			log.error("创建jobRepositoryFactoryBean异常：{}", e.getMessage());
		}
		return jobRepositoryFactoryBean;
	}

	/**
	 * @param jobRepositoryFactoryBean
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public JobRepository jobRepository(JobRepositoryFactoryBean jobRepositoryFactoryBean) {
		JobRepository jobRepository = null;
		try {
			jobRepository = jobRepositoryFactoryBean.getObject();
		} catch (Exception e) {
			log.error("创建jobRepository异常{}", e.getMessage());
		}
		return jobRepository;
	}

	/**
	 * @param jobRepository
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public SimpleJobLauncher simpleJobLauncher(JobRepository jobRepository) {
		SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
		simpleJobLauncher.setJobRepository(jobRepository);
		try {
			simpleJobLauncher.afterPropertiesSet();
		} catch (Exception e) {
			log.error("创建simpleJobLauncher异常：{}", e.getMessage());
		}
		return simpleJobLauncher;
	}

	/**
	 * 创建延迟任务线程池
	 *
	 * @return 延迟任务线程池
	 */
	@Bean("batchScheduledExecutorService")
	public ScheduledExecutorService scheduledExecutorService() {
		ThreadFactory factory = new ThreadFactoryBuilder()
			.setNameFormat("batch-delay-pool-%d")
			.build();
		return new ScheduledThreadPoolExecutor(1, factory);
	}

	/**
	 * 创建延迟任务线程池
	 *
	 * @return 延迟任务线程池
	 */
	@Bean("batchJobRestartExecutorService")
	public ExecutorService batchJobRestartExecutorService() {
		ThreadFactory factory = new ThreadFactoryBuilder()
			.setNameFormat("batch-restart-pool-%d")
			.build();
		return new ThreadPoolExecutor(1, 1,
			0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>(10),
			factory);
	}
}
