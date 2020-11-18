package com.dcfs.smartaibank.manager.monitor.core.template;

import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.exception.TemplateException;

/**
 * 模板引擎接口
 *
 * @author jiazw
 */
public interface Template {
	/**
	 * 模板渲染
	 *
	 * @param context 上下文
	 * @param string  模板
	 * @return 渲染后结果
	 * @throws TemplateException 渲染异常
	 */
	String render(MonitorContext context, TemplateDefine string) throws TemplateException;
}
