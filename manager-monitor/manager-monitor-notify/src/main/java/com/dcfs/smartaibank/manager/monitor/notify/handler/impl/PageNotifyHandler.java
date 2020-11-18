package com.dcfs.smartaibank.manager.monitor.notify.handler.impl;

import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.exception.NotifyException;
import com.dcfs.smartaibank.manager.monitor.core.template.TemplateManager;
import com.dcfs.smartaibank.manager.monitor.notify.handler.AbstractNotifyHandler;
import com.dcfs.smartaibank.manager.monitor.notify.service.BranchManager;
import com.dcfs.smartaibank.manager.monitor.rule.domain.NotifyRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面通知执行器
 *
 * @author jiazw
 */
@Slf4j
public class PageNotifyHandler extends AbstractNotifyHandler {

	private BranchManager branchManager;

	private SimpMessagingTemplate template;

	public PageNotifyHandler(BranchManager branchManager,
							 TemplateManager templateManager,
							 SimpMessagingTemplate template) {
		super(templateManager);
		Assert.notNull(branchManager, "branchManager must be not null.");
		Assert.notNull(template, "template must be not null.");
		this.branchManager = branchManager;
		this.template = template;
	}


	@Override
	protected void doHandle(MonitorContext context, NotifyRule rule) throws NotifyException {
		String contentType = rule.getContentType().getCode();
		String branchNo = context.getBody(Constants.BRANCH_NO);
		List<String> users = getUserList(rule.getUsers(), branchNo);
		if (users == null || users.isEmpty()) {
			throw new NotifyException("notify.users.empty");
		}

		// 使用模板生成发送内容
		String content = getTemplateCotent(context, rule);

		if (content == null || "".equals(content)) {
			throw new NotifyException("notify.content.empty");
		}
		//发送渠道
		String type = context.getHeader(Constants.TYPE);
		//发送目的地
		String destination = getDestination(type, contentType);

		for (String user : users) {
			log.debug("给用户:" + user.toString() + ",发送websocket地址:" + destination);
			this.template.convertAndSendToUser(user, destination, content);
		}
	}

	private String getDestination(String type, String contentType) {
		return "/queue/" + contentType.toLowerCase() + "/" + type.toLowerCase();
	}

	private List<String> getUserList(List<User> users, String branchNo) {
		List<String> list = new ArrayList<>();
		//如果是上级机构，则加入到发送列表中
		String superior = branchManager.getSuperior(branchNo);
		for (User user : users) {

			//如果是本行用户，则加入到发送列表中
			String userBranch = user.getBranchNo();
			if (userBranch != null && userBranch.equals(branchNo)) {
				list.add(user.getId());
				continue;
			}

			if (userBranch != null && userBranch.equals(superior)) {
				list.add(user.getId());
			}

			//如果是总行用户，则加入到发送列表中
			if (user.isHead()) {
				list.add(user.getId());
				continue;
			}
		}

		return list;
	}
}
