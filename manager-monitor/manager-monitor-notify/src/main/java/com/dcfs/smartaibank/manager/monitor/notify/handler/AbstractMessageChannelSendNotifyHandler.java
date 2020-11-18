package com.dcfs.smartaibank.manager.monitor.notify.handler;


import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.exception.NotifyException;
import com.dcfs.smartaibank.manager.monitor.core.template.TemplateManager;
import com.dcfs.smartaibank.manager.monitor.rule.domain.NotifyRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * 使用消息通道发送通知处理器
 *
 * @author jiazw
 */
@Slf4j
public abstract class AbstractMessageChannelSendNotifyHandler extends AbstractNotifyHandler {
	protected MessageChannel channel;
	protected boolean strictMode;

	public AbstractMessageChannelSendNotifyHandler(TemplateManager templateManager,
												   MessageChannel channel,
												   boolean strinctMode) {
		super(templateManager);
		this.channel = channel;
		this.strictMode = strinctMode;
		Assert.state(!this.strictMode || this.strictMode && this.channel != null,
			"channel must be not null in strict mode");
	}

	public AbstractMessageChannelSendNotifyHandler(TemplateManager templateManager,
												   @Nullable MessageChannel channel) {
		this(templateManager, channel, false);
	}

	@Override
	protected void doHandle(MonitorContext context, NotifyRule rule) throws NotifyException {
		Collection<Message<?>> messages = handleMessage(context, rule);
		boolean result = verifMessage(messages);
		if (!result) {
			throw new NotifyException("notify.content.empty");
		}

		sendMessages(messages);

	}

	protected void sendMessages(Collection<Message<?>> messages) {
		if (this.channel != null && messages != null) {
			for (Message<?> message : messages) {
				if (message != null) {
					this.channel.send(message);
				}
			}
		}
	}

	/**
	 * 验证消息
	 *
	 * @param messages 消息集合
	 * @return true: 非严格模式或者严格模式下所有消息不能为空
	 */
	protected boolean verifMessage(Collection<Message<?>> messages) {
		return !this.strictMode || this.strictMode && messages != null && !messages.contains(null);
	}

	/**
	 * 生成要发送的消息
	 *
	 * @param context 上下文
	 * @param rule    通知规则
	 * @return 要发送的消息集合
	 * @throws NotifyException 生成消息时产生的异常
	 */
	protected abstract Collection<Message<?>> handleMessage(MonitorContext context, NotifyRule rule)
		throws NotifyException;
}
