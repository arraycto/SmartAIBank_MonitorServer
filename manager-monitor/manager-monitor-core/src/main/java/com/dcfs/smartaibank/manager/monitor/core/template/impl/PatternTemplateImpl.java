package com.dcfs.smartaibank.manager.monitor.core.template.impl;

import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.exception.TemplateException;
import com.dcfs.smartaibank.manager.monitor.core.template.TemplateDefine;
import com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern.PatternIterator;
import com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern.PatternTemplate;
import com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern.TemplatePatternDefine;
import com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern.TemplateVarConverter;
import com.dcfs.smartaibank.manager.monitor.core.template.impl.pattern.TemplateVarProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 正则表达式匹配模板引擎
 *
 * @author jiazw
 */
public class PatternTemplateImpl implements PatternTemplate, InitializingBean {
	private static final int DEFAULT_KEY_VALUE_NUMBER = 2;
    private Map<String, TemplateVarProvider> formatProviders;
    private Map<Class, TemplateVarConverter> converters;
    private TemplatePatternDefine patternDefine;

    /**
     * 格式化找到的内容，其余内容不变，如果找不到内容，则原样保留
     */
    @Override
    public String render(MonitorContext context, TemplateDefine define) throws TemplateException {
        String source = define.getContent();
        StringBuilder buf = new StringBuilder();
        int curpos = 0;
        patternDefine.setSource(source);
        PatternIterator iter = patternDefine.iterator();
        while (iter.hasNext()) {
            String replaceStr = iter.next();
            String str = formatSingle(context, replaceStr);
            buf.append(source.substring(curpos, iter.start()));
            curpos = iter.end();
            if (str != null) {
                buf.append(str);
            } else {
                buf.append(patternDefine.getFullMatchText(replaceStr));
            }
        }
        buf.append(source.substring(curpos));
        return buf.toString();
    }

    /**
     * 格式化字符串
     *
     * @param string String
     * @return String
     */
    private String formatSingle(MonitorContext context, String string) throws TemplateException {
        String[] s = string.split(patternDefine.getSplitChar() + "");
        Object obj = null;
        if (s.length >= DEFAULT_KEY_VALUE_NUMBER) {
            TemplateVarProvider o = formatProviders.get(s[0]);
            if (o != null) {
                obj = o.format(context, s[1]);
            }
        } else {
            TemplateVarProvider o = formatProviders.get("");
            if (o != null) {
                obj = o.format(context, string);
            }
        }

        if (obj == null) {
            return null;
        } else {
            TemplateVarConverter converter = converters.get(obj.getClass());
            if (converter == null) {
                converter = converters.get(Object.class);
            }
            return converter.convert(obj);
        }

    }

    @Override
    public void setTemplateVarProviders(Map<String, TemplateVarProvider> formatProviders) {
        this.formatProviders = formatProviders;
    }

    @Override
    public void setPatternHandle(TemplatePatternDefine patternHandle) {
        this.patternDefine = patternHandle;
    }

    @Override
    public void addTemplateVarProvider(String prefix, TemplateVarProvider formatProvider) {
        if (formatProviders == null) {
            formatProviders = new HashMap<>(16);
        }
        formatProviders.put(prefix, formatProvider);
    }

    @Override
    public void setTemplateVarConverters(Map<Class, TemplateVarConverter> converters) {
        this.converters = converters;

    }

    @Override
    public void addTemplateVarConverter(Class clazz, TemplateVarConverter converter) {
        if (this.converters == null) {
            this.converters = new HashMap<>(16);
        }

        this.converters.put(clazz, converter);
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(this.patternDefine, "TemplatePatternDefine must be not null");
        Assert.notEmpty(formatProviders, "formatProviders must be not empty");
        Assert.notEmpty(converters, "converters must be not empty");
    }
}
