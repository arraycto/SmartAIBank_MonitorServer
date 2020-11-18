package com.dcfs.smartaibank.manager.monitor.core.executor;

import com.dcfs.smartaibank.manager.monitor.core.exception.ExecutorException;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;

/**
 * 执行器接口
 *
 * @author jiazw
 */
public interface Executor {
	/**
	 * 执行器执行
	 *
	 * @param context 上下文环境
	 * @param target  执行器定义
	 * @param <T>     返回类型
	 * @return 执行器执行结果
	 * @throws ExecutorException 执行器执行异常
	 */
	<T> T execute(MonitorContext context, ExecuteDefine target) throws ExecutorException;
}
