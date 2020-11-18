package com.dcfs.smartaibank.manager.monitor.analyzer.spi;

import com.dcfs.smartaibank.handler.context.Context;
import com.dcfs.smartaibank.handler.exception.HandlerException;
import com.dcfs.smartaibank.handler.AbstractHandler;
import com.dcfs.smartaibank.handler.Status;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;

/**
 * 监控处理器抽象类
 *
 * @author jiazw
 */
public abstract class AbstractMonitorHandler extends AbstractHandler {

	/**
	 * 监控处理器执行
	 *
	 * @param context 处理器执行上下文环境
	 * @return 执行结果
	 * @throws HandlerException 执行异常
	 */
	@Override
	protected Status doHandle(Context context) throws HandlerException {

		if (!(context instanceof MonitorContext)) {
			throw new HandlerException("");
		}

		return doHandle((MonitorContext) context);
	}

	/**
	 * 监控处理器执行
	 *
	 * @param context 处理器执行上下文环境
	 * @return 执行结果
	 * @throws HandlerException 执行异常
	 */
	protected abstract Status doHandle(MonitorContext context) throws HandlerException;
}
