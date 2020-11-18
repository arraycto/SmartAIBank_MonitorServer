package com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern;

import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.exception.TemplateException;

/**
 * 模板变量提供者
 *
 * @author jiazw
 */
public interface TemplateVarProvider {
	/**
	 * 把指定的值进行处理后返回
	 *
	 * @param context 上线文
	 * @param string  要进行格式化的值
	 * @return 格式化好的值
	 * @throws TemplateException 模板异常
	 */
	Object format(MonitorContext context, String string) throws TemplateException;
}
