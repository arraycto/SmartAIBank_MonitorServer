package com.dcfs.smartaibank.manager.monitor.remote.config;

import com.dcfs.smartaibank.springboot.websocket.EnableAutoWebSocket;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.scheduling.support.PeriodicTrigger;

/**
 * 远程调用配置
 *
 * @author jiazw
 */
@Configuration
@EnableAutoWebSocket
public class RemoteConfiguration {
	/**
	 * 通知参数
	 *
	 * @return 通知参数
	 */
	@Bean
	@ConditionalOnMissingBean
	public NotifyProperties notifyProperties() {
		return new NotifyProperties();
	}

	/**
	 * 默认轮询器元数据
	 *
	 * @return 默认轮询器元数据
	 */
	@Bean(name = PollerMetadata.DEFAULT_POLLER)
	@ConditionalOnMissingBean
	public PollerMetadata defaultPollerMetadata() {
		PollerMetadata metadata = new PollerMetadata();
		metadata.setTrigger(new PeriodicTrigger(10));
		return metadata;
	}
}
