package com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern.impl;


import com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern.TemplateVarConverter;

/**
 * @author jiazw
 */
public class DefaultTemplateVarConverter implements TemplateVarConverter {
	/**
	 * 变量转换
	 *
	 * @param obj 源数据
	 * @return 转换后结果
	 */
	@Override
	public String convert(Object obj) {
		return obj != null ? obj.toString() : "";
	}
}
