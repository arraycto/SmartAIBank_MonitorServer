package com.dcfs.smartaibank.manager.monitor.core.template.impl;

import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.exception.TemplateException;
import com.dcfs.smartaibank.manager.monitor.core.template.Template;
import com.dcfs.smartaibank.manager.monitor.core.template.TemplateDefine;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;
import org.apache.velocity.tools.generic.DateTool;
import org.springframework.stereotype.Component;

import java.io.StringWriter;

/**
 * Velocity模板
 *
 * @author jiazw
 */
@Component
public class VelocityTemplateImpl implements Template {

	private VelocityEngine engine;

	public VelocityTemplateImpl() {
		engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, new NullLogChute());
		engine.init();
	}

	@Override
	public String render(MonitorContext context, TemplateDefine define) throws TemplateException {
		String template = define.getContent();
		VelocityContext ctx = new VelocityContext();
		ctx.put("header", context.getHeader());
		ctx.put("body", context.getBody());
		ctx.put("content", context.getContent());
		//添加日期格式化工具类
		//使用方法：$!dateTool.format('yyyy-MM-dd HH:mm:ss ',$!context.RECEVIE_TIME)
		ctx.put("dateTool", new DateTool());
		StringWriter writer = new StringWriter();
		engine.evaluate(ctx, writer, "", template);
		return writer.toString();
	}

}
