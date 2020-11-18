package com.dcfs.smartaibank.manager.monitor.core.executor;

/**
 * 执行期管理接口
 *
 * @author jiazw
 */
public interface ExecutorManager extends Executor {

    /**
     * 根据类型获取对应的执行器
     *
     * @param type 执行器类型
     * @return 执行器
     */
    Executor getExecutor(ExecutorType type);
}
