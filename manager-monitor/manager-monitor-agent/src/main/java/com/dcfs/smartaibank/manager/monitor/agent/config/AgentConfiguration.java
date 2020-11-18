package com.dcfs.smartaibank.manager.monitor.agent.config;

import com.dcfs.smartaibank.manager.monitor.agent.sink.AgentApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * 数据采集配置
 *
 * @author jiazw
 */
@Configuration
public class AgentConfiguration {

	/**
	 * 创建数据采集应用
	 *
	 * @return 数据采集应用
	 */
	@Bean(name = "agentApplication", initMethod = "start", destroyMethod = "stop")
	@DependsOn({"analyzerEngine"})
	public AgentApplication agentApplication() {
		return new AgentApplication();
	}
}
