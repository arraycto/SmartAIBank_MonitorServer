package com.dcfs.smartaibank.manager.operations.web.utils;

import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取 Spring Batch 步骤的 description
 * <p>
 * description d的来源为batch 任务配置文件：batch/batch-job.xml
 * 对应标签为<batch:description></batch:description>
 * </p>
 *
 * @author wangjzm
 * @since 1.0.0
 */
@Component
public class AcquireBatchStepsDescUtil implements ApplicationContextAware, InitializingBean {
	/**
	 * Spring应用上下文环境
	 */
	private ApplicationContext applicationContext;

	private Map<String, String> stepDesc = new HashMap<>(64);

	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
		throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		DefaultListableBeanFactory beanFactory =
			(DefaultListableBeanFactory) this.applicationContext.getAutowireCapableBeanFactory();
		Map<String, TaskletStep> beansOfType = beanFactory.getBeansOfType(TaskletStep.class);
		beansOfType.keySet().stream().forEach(s -> {
			AbstractBeanDefinition privateCustomerInfoImportStep =
				(AbstractBeanDefinition) beanFactory.getBeanDefinition(s);
			this.stepDesc.put(s, privateCustomerInfoImportStep.getDescription());
		});
	}

	/**
	 * 根据stepId获取desc描述
	 *
	 * @param stepId batch中step id
	 * @return 描述
	 */
	public String getStepDescByStepId(@NotNull String stepId) {
		String s = this.stepDesc.get(stepId);
		return s != null ? s : "";
	}
}
