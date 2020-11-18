package com.dcfs.smartaibank.manager.monitor.notify.handler.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.exception.NotifyException;
import com.dcfs.smartaibank.manager.monitor.core.template.TemplateDefine;
import com.dcfs.smartaibank.manager.monitor.core.template.TemplateManager;
import com.dcfs.smartaibank.manager.monitor.core.util.JsonConvertUtil;
import com.dcfs.smartaibank.manager.monitor.notify.domain.Sms;
import com.dcfs.smartaibank.manager.monitor.notify.handler.AbstractMessageChannelSendNotifyHandler;
import com.dcfs.smartaibank.manager.monitor.rule.RuleCenterAccess;
import com.dcfs.smartaibank.manager.monitor.rule.domain.NotifyRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

/**
 * 短信通知执行器
 *
 * @author jiazw
 */
@Slf4j
public class SmsNotifyHandler extends AbstractMessageChannelSendNotifyHandler {
	@Autowired
	private RuleCenterAccess ruleCenter;

	public SmsNotifyHandler(TemplateManager templateManager, MessageChannel messageChannel) {
		super(templateManager, messageChannel);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Collection<Message<?>> handleMessage(MonitorContext context, NotifyRule rule) throws NotifyException {
		TemplateDefine define = rule.getTemplateDefine();
		if (define == null) {
			throw new NotifyException("notify.template.null");
		}

		Collection<Message<?>> messages = new ArrayList<>();

		boolean flag = shouldBeSendSms(context);
		if (!flag) {
			return messages;
		}

		//获取短信发送的用户列表
		List<User> users = getUsersList(context, rule.getUsers());
		if (users == null || users.isEmpty()) {
			throw new NotifyException("notify.users.empty");
		}

		for (User user : users) {
			context.put("USER_ID", user.getId());
			context.put("USER_NAME", user.getName());
			List<String> contents = getSmsContents(context, rule);
			for (String content : contents) {
				Message<?> message = buildMessageAndSend(context, user.getPhone(), content);
				messages.add(message);
			}
		}

		return messages;
	}

	/**
	 * 获取短信内容
	 *
	 * @param context 上下文
	 * @param rule    通知规则
	 * @return 短信内容列表
	 * @throws NotifyException 模板解析异常、字符串内容转集合异常、短信内容为空
	 */
	@SuppressWarnings("unchecked")
	private List<String> getSmsContents(MonitorContext context, NotifyRule rule) throws NotifyException {
		String contentStr = getTemplateCotent(context, rule);
		List<String> contents = new ArrayList<>();
		try {
			contents = JsonConvertUtil.fromJson(contentStr, contents.getClass());
			if (contents == null || contents.size() == 0) {
				throw new NotifyException("notify.content.empty");
			}
		} catch (Exception e) {
			throw new NotifyException("notify.template.parse.error", e);
		}
		return contents;
	}

	private Message<Sms> buildMessageAndSend(MonitorContext context, String phone, String smsContext) {
		Sms sms = new Sms();
		sms.setBranchNo(context.getBody(Constants.BRANCH_NO));
		sms.setPhone(phone);
		sms.setContext(smsContext);
		sms.setType(context.getType());
		Message<Sms> message = MessageBuilder.withPayload(sms).build();
		return message;
	}

	private String getManagerId(MonitorContext context) {
		return ruleCenter.getDeviceManager(context.getBody(Constants.MID));
	}

	private List<User> getUsersList(MonitorContext context, List<User> users) {
		List<User> notifyusers = new ArrayList<>();
		String managerid = getManagerId(context);
		if (managerid != null) {
			for (User user : users) {
				String userid = user.getId();
				if (userid.equals(managerid)) {
					notifyusers.add(user);
					break;
				}
			}
		}
		return notifyusers;
	}

	/**
	 * 判断是否应该发送短信，
	 * 主要根据监控或者预警发生的时间是否在机具工作时间内
	 *
	 * @param context 上下文
	 * @return true:应该发送，false:不应该发送
	 */
	private boolean shouldBeSendSms(MonitorContext context) {
		//判断机具是否在工作时间
		String time = context.getHeader("CREATE_TIME");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String devKey = context.getBody(Constants.BRANCH_NO) + "_" + context.getBody(Constants.MID);
		boolean flag = false;
		List<Date[]> workTimes = ruleCenter.getWorkTimeRules(devKey);
		if (workTimes.size() > 0) {
			for (Date[] workTime : workTimes) {
				try {
					Date dateTime = sdf.parse(time);
					if (workTime[0].before(dateTime) && workTime[1].after(dateTime)) {
						flag = true;
						break;
					}
				} catch (ParseException e) {
					throw new NotifyException("notify.handler.error", e);
				}
			}
		}
		if (!flag) {
			log.warn("监控或预警消息发生时间不在机具的工作时间内。");
		}
		return flag;
	}
}
