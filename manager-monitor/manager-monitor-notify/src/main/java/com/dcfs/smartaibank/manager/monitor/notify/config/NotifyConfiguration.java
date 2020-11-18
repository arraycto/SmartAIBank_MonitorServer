package com.dcfs.smartaibank.manager.monitor.notify.config;

import com.dcfs.smartaibank.manager.monitor.core.executor.ExecutorManager;
import com.dcfs.smartaibank.manager.monitor.core.template.TemplateManager;
import com.dcfs.smartaibank.manager.monitor.notify.NotifyManager;
import com.dcfs.smartaibank.manager.monitor.notify.NotifyManagerImpl;
import com.dcfs.smartaibank.manager.monitor.notify.handler.NotifyHandler;
import com.dcfs.smartaibank.manager.monitor.notify.handler.impl.MailNotifyHandler;
import com.dcfs.smartaibank.manager.monitor.notify.handler.impl.MobileNotifyHandler;
import com.dcfs.smartaibank.manager.monitor.notify.handler.impl.PageNotifyHandler;
import com.dcfs.smartaibank.manager.monitor.notify.handler.impl.SmsNotifyHandler;
import com.dcfs.smartaibank.manager.monitor.notify.service.BranchManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * 通知模块配置
 *
 * @author jiazw
 */
@Configuration
@EnableIntegration
public class NotifyConfiguration {

	private static final String MOBILE_SEND_CHANNEL_NAME = "mobileNotifySendChannel";
	private static final String MAIL_SEND_CHANNEL_NAME = "mailNotifySendChannel";
	private static final String SMS_SEND_CHANNEL_NAME = "smsNotifySendChannel";

	/**
	 * 移动通知处理器
	 *
	 * @return 移动通知处理器
	 */
	@Bean
	@ConditionalOnMissingBean(name = "mobileNotifyHandler")
	public NotifyHandler mobileNotifyHandler(TemplateManager templateManager,
											 @Nullable
											 @Qualifier(MOBILE_SEND_CHANNEL_NAME)
												 MessageChannel mobileNotifySendChannel,
											 NotifyManager notifyManager) {
		NotifyHandler handler = new MobileNotifyHandler(templateManager, mobileNotifySendChannel);
		notifyManager.addNotifyHandler("MOBILE", handler);
		return handler;
	}

	/**
	 * 页面通知处理器
	 *
	 * @return 页面通知处理器
	 */
	@Bean
	@ConditionalOnMissingBean(name = "pageNotifyHandler")
	public NotifyHandler pageNotifyHandler(BranchManager branchManager,
										   TemplateManager templateManager,
										   @Qualifier("clusterSimpMessagingTemplate")
											   SimpMessagingTemplate template,
										   NotifyManager notifyManager) {
		NotifyHandler handler = new PageNotifyHandler(branchManager, templateManager, template);
		notifyManager.addNotifyHandler("PAGE", handler);
		return handler;
	}

	/**
	 * 短信通知处理器
	 *
	 * @return 短信通知处理器
	 */
	@Bean
	@ConditionalOnMissingBean(name = "smsNotifyHandler")
	public NotifyHandler smsNotifyHandler(TemplateManager templateManager,
										  @Nullable
										  @Qualifier(SMS_SEND_CHANNEL_NAME)
											  MessageChannel smsNotifySendChannel,
										  NotifyManager notifyManager) {
		NotifyHandler handler = new SmsNotifyHandler(templateManager, smsNotifySendChannel);
		notifyManager.addNotifyHandler("SMS", handler);
		return handler;
	}

	/**
	 * 邮件通知处理器
	 *
	 * @return 邮件通知处理器
	 */
	@Bean
	@ConditionalOnMissingBean(name = "mailNotifyHandler")
	public NotifyHandler mailNotifyHandler(TemplateManager templateManager,
										   @Nullable
										   @Qualifier(MAIL_SEND_CHANNEL_NAME)
											   MessageChannel mailNotifySendChannel,
										   NotifyManager notifyManager) {
		NotifyHandler handler = new MailNotifyHandler(templateManager, mailNotifySendChannel);
		notifyManager.addNotifyHandler("MAIL", handler);
		return handler;
	}

	/**
	 * 注册通知管理器
	 *
	 * @return 通知管理器
	 */
	@Bean
	public NotifyManager notifyManager(ExecutorManager executorManager) {
		NotifyManager manager = new NotifyManagerImpl(executorManager);
		return manager;
	}
}
