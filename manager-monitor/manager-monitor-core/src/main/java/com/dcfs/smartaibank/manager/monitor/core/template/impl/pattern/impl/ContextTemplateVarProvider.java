package com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern.impl;

import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.exception.TemplateException;
import com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern.TemplateVarProvider;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * 上下文变量提供者
 * 从上下文环境中获取指定名称的变量
 * @author jiazw
 */
public class ContextTemplateVarProvider implements TemplateVarProvider {

    /**
     * 把指定的值进行处理后返回
     *
     * @param string 要进行格式化的值
     * @return 格式化好的值
     */
    @Override
    public Object format(MonitorContext context, String string) throws TemplateException {
        try {
            return PropertyUtils.getProperty(context, string);
        } catch (Exception e) {
            throw new TemplateException("template.pattern.provider.error", e);
        }
    }
}
