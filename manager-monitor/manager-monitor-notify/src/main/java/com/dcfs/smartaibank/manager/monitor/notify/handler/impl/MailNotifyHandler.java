package com.dcfs.smartaibank.manager.monitor.notify.handler.impl;

import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.exception.NotifyException;
import com.dcfs.smartaibank.manager.monitor.core.template.TemplateDefine;
import com.dcfs.smartaibank.manager.monitor.core.template.TemplateManager;
import com.dcfs.smartaibank.manager.monitor.core.util.JsonConvertUtil;
import com.dcfs.smartaibank.manager.monitor.notify.handler.AbstractMessageChannelSendNotifyHandler;
import com.dcfs.smartaibank.manager.monitor.remote.config.NotifyProperties;
import com.dcfs.smartaibank.manager.monitor.rule.domain.NotifyRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 邮件通知执行器
 *
 * @author jiazw
 */
public class MailNotifyHandler extends AbstractMessageChannelSendNotifyHandler {
	@Autowired
	NotifyProperties notifyProperties;

	public MailNotifyHandler(TemplateManager templateManager, MessageChannel messageChannel) {
		super(templateManager, messageChannel);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Message<?>> handleMessage(MonitorContext context, NotifyRule rule) throws NotifyException {
		TemplateDefine define = rule.getTemplateDefine();
		if (define == null) {
			throw new NotifyException("notify.template.null");
		}

		if (rule.getUsers().size() == 0) {
			throw new NotifyException("notify.users.empty");
		}

		// 使用模板生成发送内容
		String content = getTemplateCotent(context, rule);
		Map<String, String> map;
		try {
			map = JsonConvertUtil.fromJson(content, Map.class);
			if (map == null || map.isEmpty()) {
				throw new NotifyException("notify.content.empty");
			}
		} catch (Exception e) {
			throw new NotifyException("notify.template.parse.error", e);
		}
		Collection<Message<?>> messages = new ArrayList<>(1);

		String subject = map.containsKey("SUBJECT") ? map.get("SUBJECT")
			: rule.getTemplateDefine().getName();
		Message<String> message = MessageBuilder.withPayload(map.get("CONTENT"))
			.setHeader("mail_subject", subject)
			.setHeader("mail_to", getTo(rule.getUsers()))
			.setHeader("mail_from", notifyProperties.getMail().getFrom())
			.build();
		messages.add(message);
		return messages;
	}

	private String[] getTo(List<User> users) {
		List<String> tos = new ArrayList<>(users.size());
		for (User user : users) {
			if (user.getEmail() != null && !"".equals(user.getEmail())) {
				tos.add(user.getEmail());
			}
		}
		if (tos.size() == 0) {
			throw new NotifyException("notify.mailto.empty");
		}
		return tos.toArray(new String[tos.size()]);
	}
}
