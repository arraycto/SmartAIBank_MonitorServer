package com.dcfs.smartaibank.manager.monitor.remote.config;

import com.dcfs.smartaibank.springboot.integration.http.outbound.HttpOutboundFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowBuilder;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * 通知模块配置
 *
 * @author jiazw
 */
@Configuration
@EnableIntegration
public class MobileNotifyConfiguration {
	private static final String MOBILE_SEND_CHANNEL_NAME = "mobileNotifySendChannel";
	private static final String MOBILE_REPLY_CHANNEL_NAME = "mobileNotifyReplyChannel";

	/**
	 * 移动端通知发送消息通道
	 *
	 * @param notifyProperties 通知配置
	 * @return 队列消息通道
	 */
	@Bean(name = MOBILE_SEND_CHANNEL_NAME)
	@ConditionalOnMissingBean(name = MOBILE_SEND_CHANNEL_NAME)
	@ConditionalOnProperty(name = "monitor.notify.mobile.enabled")
	public MessageChannel mobileNotifySendChannel(NotifyProperties notifyProperties) {
		Integer queueSize = notifyProperties.getMobile().getQueueSize();
		return new QueueChannel(queueSize);
	}

	/**
	 * 移动端通知回复消息通道
	 *
	 * @param notifyProperties 通知配置
	 * @return 队列消息通道
	 */
	@Bean(name = MOBILE_REPLY_CHANNEL_NAME)
	@ConditionalOnProperty(name = "monitor.notify.mobile.enabled")
	@ConditionalOnMissingBean(name = MOBILE_REPLY_CHANNEL_NAME)
	public MessageChannel mobileNotifyReplyChannel(NotifyProperties notifyProperties) {
		Integer queueSize = notifyProperties.getMobile().getQueueSize();
		return new QueueChannel(queueSize);
	}

	/**
	 * 构建http消息发送处理器
	 *
	 * @param notifyProperties 通知配置
	 * @return 消息发送处理器
	 */
	@Bean
	@ConditionalOnMissingBean(name = "mobileNotifySendMessageHandler")
	@ConditionalOnProperty(name = "monitor.notify.mobile.enabled")
	public MessageHandler mobileNotifySendMessageHandler(NotifyProperties notifyProperties) throws Exception {
		HttpOutboundFactoryBean factory = new HttpOutboundFactoryBean(notifyProperties.getMobile());
		factory.setName("mobileNotifySendMessageHandler");
		factory.afterPropertiesSet();
		return factory.getObject();
	}

	/**
	 * 配置消息发送流程
	 * 流程：放入发送通道[->消息转换器]->发送处理器[->回复通道->回复结果处理]
	 *
	 * @param notifyProperties 通知配置
	 * @return 发送流程
	 */
	@Bean
	@ConditionalOnMissingBean(name = "mobileNotifyFlow")
	@ConditionalOnProperty(name = "monitor.notify.mobile.enabled")
	public IntegrationFlow mobileNotifyFlow(NotifyProperties notifyProperties) throws Exception {
		IntegrationFlowBuilder ifb = IntegrationFlows.from(MOBILE_SEND_CHANNEL_NAME)
			.handle(mobileNotifySendMessageHandler(notifyProperties));
		return ifb.get();
	}
}
