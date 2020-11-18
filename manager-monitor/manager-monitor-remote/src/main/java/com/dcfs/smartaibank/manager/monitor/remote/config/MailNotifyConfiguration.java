package com.dcfs.smartaibank.manager.monitor.remote.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowBuilder;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.mail.MailSendingMessageHandler;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.util.Properties;

/**
 * 通知模块配置
 *
 * @author jiazw
 */
@Configuration
@EnableIntegration
public class MailNotifyConfiguration {
	private static final String MAIL_SEND_CHANNEL_NAME = "mailNotifySendChannel";
	private static final String MAIL_REPLY_CHANNEL_NAME = "mailNotifyReplyChannel";
	private static final String HANDLER_BEAN_NAME = "mailNotifySendMessageHandler";

	/**
	 * 移动端通知发送消息通道
	 *
	 * @param notifyProperties 通知配置
	 * @return 队列消息通道
	 */
	@Bean(name = MAIL_SEND_CHANNEL_NAME)
	@ConditionalOnMissingBean(name = MAIL_SEND_CHANNEL_NAME)
	@ConditionalOnProperty(name = "monitor.notify.mail.enabled")
	public MessageChannel mailNotifySendChannel(NotifyProperties notifyProperties) {
		Integer queueSize = notifyProperties.getMail().getQueueSize();
		return new QueueChannel(queueSize);
	}

	/**
	 * 移动端通知回复消息通道
	 *
	 * @param notifyProperties 通知配置
	 * @return 队列消息通道
	 */
	@Bean(name = MAIL_REPLY_CHANNEL_NAME)
	@ConditionalOnMissingBean(name = MAIL_REPLY_CHANNEL_NAME)
	@ConditionalOnProperty(name = "monitor.notify.mail.enabled")
	public MessageChannel mailNotifyReplyChannel(NotifyProperties notifyProperties) {
		Integer queueSize = notifyProperties.getMail().getQueueSize();
		return new QueueChannel(queueSize);
	}

	/**
	 * 创建邮件发送器
	 *
	 * @param notifyProperties 参数配置
	 * @return 邮件发送器
	 */
	@Bean
	@ConditionalOnMissingBean(name = "mailSender")
	@ConditionalOnProperty(name = "monitor.notify.mail.enabled")
	public MailSender mailSender(NotifyProperties notifyProperties) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setProtocol(notifyProperties.getMail().getProcotol());
		mailSender.setHost(notifyProperties.getMail().getHost());
		mailSender.setPort(notifyProperties.getMail().getPort());
		mailSender.setDefaultEncoding(notifyProperties.getMail().getEncoding());
		mailSender.setUsername(notifyProperties.getMail().getUsername());
		mailSender.setPassword(notifyProperties.getMail().getPassword());
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.auth", notifyProperties.getMail().getAuth().toString());
		properties.setProperty("mail.smtp.timeout", notifyProperties.getMail().getTimeOut().toString());
		properties.setProperty("mail.smtp.from", notifyProperties.getMail().getFrom());
		mailSender.setJavaMailProperties(properties);
		return mailSender;
	}

	/**
	 * 构建http消息发送处理器
	 *
	 * @param notifyProperties 通知配置
	 * @param mailSender       邮件发送器
	 * @return 消息发送处理器
	 */
	@Bean
	@ConditionalOnMissingBean(name = "mailNotifySendMessageHandler")
	@ConditionalOnProperty(name = "monitor.notify.mail.enabled")
	public MessageHandler mailNotifySendMessageHandler(NotifyProperties notifyProperties, MailSender mailSender) {
		MailSendingMessageHandler handler = new MailSendingMessageHandler(mailSender);
		handler.setBeanName(HANDLER_BEAN_NAME);
		//设置度量指标是否开启
		handler.setCountsEnabled(notifyProperties.getMail().getMetricsEnabled());
		handler.setStatsEnabled(notifyProperties.getMail().getMetricsEnabled());
		return handler;
	}

	/**
	 * 配置消息发送流程
	 * 流程：流程：放入发送通道[->消息转换器]->发送处理器[->回复通道->回复结果处理]
	 *
	 * @param notifyProperties 通知配置
	 * @return 发送流程
	 */
	@Bean
	@ConditionalOnMissingBean(name = "mailNotifyFlow")
	@ConditionalOnProperty(name = "monitor.notify.mail.enabled")
	public IntegrationFlow mailNotifyFlow(NotifyProperties notifyProperties,
										  MailSender mailSender) {
		IntegrationFlowBuilder ifb = IntegrationFlows.from(MAIL_SEND_CHANNEL_NAME);
		ifb = ifb.handle(mailNotifySendMessageHandler(notifyProperties, mailSender));
		return ifb.get();
	}
}
