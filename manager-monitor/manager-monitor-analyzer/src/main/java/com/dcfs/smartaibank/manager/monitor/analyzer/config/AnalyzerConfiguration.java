package com.dcfs.smartaibank.manager.monitor.analyzer.config;

import com.dcfs.smartaibank.manager.monitor.analyzer.engine.AnalyzerEngineFactory;
import com.dcfs.smartaibank.manager.monitor.analyzer.scheduler.TimedRuleJobLoader;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.AnalyzerEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * 分析模块配置
 *
 * @author jiazw
 */
@Configuration
public class AnalyzerConfiguration {
	/**
	 * 创建分析引擎工厂
	 *
	 * @return 分析引擎工厂
	 * @throws Exception 创建异常
	 */
	@Bean
	@DependsOn("appPropertyNodeProcessor")
	public AnalyzerEngineFactory analyzerEngineFactory() throws Exception {
		return new AnalyzerEngineFactory();
	}

	/**
	 * 创建分析引擎
	 *
	 * @param analyzerEngineFactory 分析引擎工厂
	 * @return 分析引擎
	 * @throws Exception 创建异常
	 */
	@Bean
	public AnalyzerEngine analyzerEngine(AnalyzerEngineFactory analyzerEngineFactory) throws Exception {
		return analyzerEngineFactory.getObject();
	}

	/**
	 * 创建定时任务加载器
	 *
	 * @return 定时任务加载器
	 */
	@Bean(name = "timedRuleJobLoader", initMethod = "load")
	@DependsOn({"ruleCenter", "scheduleManager", "ruleLoader"})
	public TimedRuleJobLoader timedRuleJobLoader() {
		return new TimedRuleJobLoader();
	}
}
