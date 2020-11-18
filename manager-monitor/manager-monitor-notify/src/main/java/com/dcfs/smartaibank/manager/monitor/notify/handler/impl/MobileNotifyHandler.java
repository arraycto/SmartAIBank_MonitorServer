package com.dcfs.smartaibank.manager.monitor.notify.handler.impl;

import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.exception.NotifyException;
import com.dcfs.smartaibank.manager.monitor.core.template.TemplateManager;
import com.dcfs.smartaibank.manager.monitor.notify.handler.AbstractMessageChannelSendNotifyHandler;
import com.dcfs.smartaibank.manager.monitor.rule.domain.NotifyRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 短信通知执行器
 *
 * @author jiazw
 */
@Slf4j
public class MobileNotifyHandler extends AbstractMessageChannelSendNotifyHandler {

	public MobileNotifyHandler(TemplateManager templateManager, MessageChannel messageChannel) {
		super(templateManager, messageChannel);
	}

	@Override
	protected Collection<Message<?>> handleMessage(MonitorContext context, NotifyRule rule) throws NotifyException {
		// 使用模板生成发送内容
		String content = getTemplateCotent(context, rule);
		if (content == null || "".equals(content)) {
			throw new NotifyException("notify.content.empty");
		}
		Message<String> message = MessageBuilder.withPayload(content).build();
		Collection<Message<?>> messages = new ArrayList<>(1);
		messages.add(message);
		return messages;
	}

}
