package com.dcfs.smartaibank.manager.monitor.core.config;

import com.dcfs.smartaibank.manager.monitor.core.executor.ExecutorManager;
import com.dcfs.smartaibank.manager.monitor.core.executor.ExecutorType;
import com.dcfs.smartaibank.manager.monitor.core.executor.impl.BeanExpressionExecutor;
import com.dcfs.smartaibank.manager.monitor.core.executor.impl.CustomerExpressionExecutor;
import com.dcfs.smartaibank.manager.monitor.core.executor.impl.ExecutorManagerImpl;
import com.dcfs.smartaibank.manager.monitor.core.executor.impl.MvelExpressionExecutor;
import com.dcfs.smartaibank.manager.monitor.core.template.TemplateManager;
import com.dcfs.smartaibank.manager.monitor.core.template.TemplateType;
import com.dcfs.smartaibank.manager.monitor.core.template.impl.PatternTemplateImpl;
import com.dcfs.smartaibank.manager.monitor.core.template.impl.TemplateManagerImpl;
import com.dcfs.smartaibank.manager.monitor.core.template.impl.VelocityTemplateImpl;
import com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern.PatternTemplate;
import com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern.impl.ContextTemplateVarProvider;
import com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern.impl.DateTemplateVarConverter;
import com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern.impl.DefaultTemplatePatternDefine;
import com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern.impl.DefaultTemplateVarConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * 模块配置
 *
 * @author jiazw
 */
@Configuration
public class CoreConfig {

	/**
	 * 创建执行器管理器
	 *
	 * @return 执行器管理器
	 */
	@Bean
	public ExecutorManager executorManager() {
		ExecutorManagerImpl executorManager = new ExecutorManagerImpl();
		executorManager.addExecutor(ExecutorType.MVEL, new MvelExpressionExecutor());
		executorManager.addExecutor(ExecutorType.CUSTOMER, new CustomerExpressionExecutor());
		executorManager.addExecutor(ExecutorType.BEAN, new BeanExpressionExecutor());
		return executorManager;
	}

	/**
	 * 创建模板管理器
	 *
	 * @return 模板管理器
	 */
	@Bean
	public TemplateManager templateManager() {
		PatternTemplate template = new PatternTemplateImpl();
		template.setPatternHandle(new DefaultTemplatePatternDefine());
		template.addTemplateVarConverter(Object.class, new DefaultTemplateVarConverter());
		template.addTemplateVarConverter(Date.class, new DateTemplateVarConverter());
		template.addTemplateVarProvider("", new ContextTemplateVarProvider());

		TemplateManagerImpl manager = new TemplateManagerImpl();
		manager.addTemplate(TemplateType.PATTERN, template);
		manager.addTemplate(TemplateType.VELOCITY, new VelocityTemplateImpl());
		return manager;
	}
}
