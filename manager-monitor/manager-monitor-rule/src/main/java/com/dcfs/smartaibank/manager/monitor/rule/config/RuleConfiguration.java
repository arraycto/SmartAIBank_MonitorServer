package com.dcfs.smartaibank.manager.monitor.rule.config;

import com.dcfs.smartaibank.manager.monitor.rule.RuleLoader;
import com.dcfs.smartaibank.manager.monitor.rule.impl.RuleLoaderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * 规则中心配置
 *
 * @author jiazw
 */
@Configuration
public class RuleConfiguration {

	/**
	 * 创建规则加载器并加载
	 *
	 * @return 规则加载器
	 */
	@Bean(name = "ruleLoader", initMethod = "load")
	@DependsOn({"ruleDao", "ruleParamDao", "ruleCenter"})
	public RuleLoader ruleLoader() {
		return new RuleLoaderImpl();
	}
}
