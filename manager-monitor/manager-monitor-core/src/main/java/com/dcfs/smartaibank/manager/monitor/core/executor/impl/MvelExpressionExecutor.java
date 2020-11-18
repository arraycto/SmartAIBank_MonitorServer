package com.dcfs.smartaibank.manager.monitor.core.executor.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.dcfs.smartaibank.core.util.TypeUtil;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.exception.ExecutorException;
import com.dcfs.smartaibank.manager.monitor.core.executor.ExecuteDefine;
import com.dcfs.smartaibank.manager.monitor.core.executor.Executor;
import org.mvel2.MVEL;

/**
 * MVEL表达式执行器
 *
 * @author jiazw
 */
public class MvelExpressionExecutor implements Executor {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T execute(MonitorContext context, ExecuteDefine target) throws ExecutorException {
        if (target != null) {
            Map<String, Object> vars = new HashMap<>(16);
            Serializable expression = target.getCompiledExecutor();
            Class<?> clazz = TypeUtil.fromName(target.getReturnType());
            if (clazz == null) {
                throw new ExecutorException("执行器返回结果不正确！ReturnType=" + target.getReturnType());
            }
            try {
                if (expression instanceof String) {
                    return (T) MVEL.eval((String) expression, context, vars, clazz);
                } else {
                    return (T) MVEL.executeExpression(expression, context, vars, clazz);
                }
            } catch (Exception e) {
                throw new ExecutorException("执行MVEL表达式发生异常！ 表达式=" + target.getExecutor(), e);
            }
        }
        return null;
    }
}
