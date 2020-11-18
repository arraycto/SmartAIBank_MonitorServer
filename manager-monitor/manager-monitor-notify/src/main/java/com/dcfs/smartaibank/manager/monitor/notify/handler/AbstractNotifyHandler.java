package com.dcfs.smartaibank.manager.monitor.notify.handler;

import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.exception.NotifyException;
import com.dcfs.smartaibank.manager.monitor.core.exception.TemplateException;
import com.dcfs.smartaibank.manager.monitor.core.template.TemplateDefine;
import com.dcfs.smartaibank.manager.monitor.core.template.TemplateManager;
import com.dcfs.smartaibank.manager.monitor.rule.domain.NotifyRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

/**
 * 通知执行器抽象类
 *
 * @author jiazw
 */
@Slf4j
public abstract class AbstractNotifyHandler implements NotifyHandler {

	protected TemplateManager templateManager;

	public AbstractNotifyHandler(TemplateManager templateManager) {
		Assert.notNull(templateManager, "templateManager must be not null.");
		this.templateManager = templateManager;
	}

	@Override
	public void handle(MonitorContext context, NotifyRule rule) throws NotifyException {
		doHandle(context, rule);
	}

	/**
	 * 子类需要实现的通知方法
	 *
	 * @param context 上下文
	 * @param rule    通知规则
	 * @throws NotifyException 通知异常
	 */
	protected abstract void doHandle(MonitorContext context, NotifyRule rule) throws NotifyException;

	protected String getTemplateCotent(MonitorContext context, NotifyRule rule) {
		try {
			TemplateDefine templateDefine = rule.getTemplateDefine();
			return this.templateManager.render(context, templateDefine);
		} catch (TemplateException e) {
			throw new NotifyException("notify.template.error",
				"[" + context.getUUID() + "]使用模板生成通知内容发时生异常！", e);
		}
	}
}
