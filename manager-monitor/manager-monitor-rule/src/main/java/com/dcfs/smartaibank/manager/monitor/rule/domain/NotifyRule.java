package com.dcfs.smartaibank.manager.monitor.rule.domain;

import com.dcfs.smartaibank.manager.monitor.core.template.TemplateDefine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * 通知规则定义
 *
 * @author jiazw
 */
@NoArgsConstructor
@Getter
@Setter
public class NotifyRule extends Rule {

	/**
	 * 监控项id
	 */
	private MonitorItem monitorItem;

	/**
	 * 通知类型
	 */
	private String notifyType;

	/**
	 * 通知模板
	 */
	private TemplateDefine templateDefine;

	private NotifyContentType contentType;

	/**
	 * 通知用户列表
	 */
	private List<User> users;
}
