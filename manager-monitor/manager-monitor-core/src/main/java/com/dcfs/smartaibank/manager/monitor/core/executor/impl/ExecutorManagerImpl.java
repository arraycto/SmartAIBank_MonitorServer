package com.dcfs.smartaibank.manager.monitor.core.executor.impl;

import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.exception.ExecutorException;
import com.dcfs.smartaibank.manager.monitor.core.executor.ExecuteDefine;
import com.dcfs.smartaibank.manager.monitor.core.executor.Executor;
import com.dcfs.smartaibank.manager.monitor.core.executor.ExecutorManager;
import com.dcfs.smartaibank.manager.monitor.core.executor.ExecutorType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 执行器管理接口实现类
 *
 * @author jiazw
 */
public class ExecutorManagerImpl implements ExecutorManager {

    private Map<ExecutorType, Executor> executors = new ConcurrentHashMap<ExecutorType, Executor>();

    /**
     * 根据类型获取对应的执行器
     *
     * @param type 执行器类型
     * @return 执行器
     */
    @Override
    public Executor getExecutor(ExecutorType type) {
        return executors.get(type);
    }

    /**
     * 执行器执行
     *
     * @param context 上下文环境
     * @param target  执行器定义
     * @param <T>     返回类型
     * @return 执行器执行结果
     * @throws ExecutorException 执行器执行异常
     */
    @Override
    public <T> T execute(MonitorContext context, ExecuteDefine target) throws ExecutorException {
        if (target != null) {
            Executor executor = getExecutor(target.getType());
            if (executor != null) {
                return executor.execute(context, target);
            }
        }

        return null;
    }

    /**
     * 添加执行器
     *
     * @param type     执行器类型
     * @param executor 执行器
     */
    public void addExecutor(ExecutorType type, Executor executor) {
        this.executors.put(type, executor);
    }
}
