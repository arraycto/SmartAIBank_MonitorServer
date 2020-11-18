package com.dcfs.smartaibank.manager.monitor.core.executor.impl;

import com.dcfs.smartaibank.core.util.Loader;
import com.dcfs.smartaibank.core.util.TypeUtil;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.executor.ExecuteDefine;
import com.dcfs.smartaibank.manager.monitor.core.executor.Executor;
import org.mvel2.DataConversion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义执行器
 *
 * @author jiazw
 */
public class CustomerExpressionExecutor implements Executor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerExpressionExecutor.class);

    @SuppressWarnings("unchecked")
    @Override
    public <T> T execute(MonitorContext context, ExecuteDefine target) {
        String uuid = context.get("UUID");
        if (target != null) {
            String className = target.getExecutor();
            if (className != null && !"".equals(className)) {
                try {
                    Class<?> clazz = Loader.loadClass(className);
                    Object obj = clazz.newInstance();
                    if (obj instanceof Executor) {
                        Object result = ((Executor) obj).execute(context, target);
                        Class<T> typeClazz = (Class<T>) TypeUtil.fromName(target.getReturnType());
                        if (typeClazz != null) {
                            return DataConversion.convert(result, typeClazz);
                        } else {
                            LOGGER.error("[{}]执行器返回类型有误，返回类型只能为基础类型。", uuid);
                        }
                    } else {
                        LOGGER.error("[{}]执行器必须实现Executor接口！", uuid);
                    }
                } catch (Exception e) {
                    LOGGER.error("[{}]执行自定义执行器失败。CLASS_NAME:{}", uuid, className, e);
                }
            }
        } else {
            LOGGER.error("[{}]执行器定义为空，无法执行！", uuid);
        }

        return null;
    }

}
