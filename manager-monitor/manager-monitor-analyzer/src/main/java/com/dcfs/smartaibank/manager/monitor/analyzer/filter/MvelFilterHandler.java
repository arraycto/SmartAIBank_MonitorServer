package com.dcfs.smartaibank.manager.monitor.analyzer.filter;

import com.dcfs.smartaibank.handler.exception.HandlerException;
import com.dcfs.smartaibank.handler.Status;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.AbstractMonitorHandler;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.FilterHandler;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.executor.ExecutorManager;
import com.dcfs.smartaibank.manager.monitor.rule.domain.FilterRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 默认MVEL表达式过滤器
 *
 * @author jiazw
 */
@Slf4j
public class MvelFilterHandler extends AbstractMonitorHandler implements FilterHandler, Constants {
	private ExecutorManager manager;

	@Override
	protected Status doHandle(MonitorContext context) throws HandlerException {
		List<FilterRule> filterRules = context.get(CURRENT_FILTER_RULES);
		Boolean result = false;
		if (filterRules != null) {
			for (FilterRule filterRule : filterRules) {
				if (filterRule != null && filterRule.isActive()) {
					try {
						result = manager.execute(context, filterRule.getExecutor());
					} catch (Exception e) {
						throw new HandlerException("executor.error", e);
					}
					if (log.isInfoEnabled()) {
						log.info("[{}]过滤器[ID={},DESC={}]执行结果为:{}！",
							context.get("UUID"), filterRule.getId(), filterRule.getDescription(), result);
					}

					if (result) {
						break;
					}
				} else {
					log.info("[{}]过滤器[ID={},DESC={}]未激活！",
						context.get("UUID"), filterRule.getId(), filterRule.getDescription());
				}
			}
		}

		return result != null && result ? Status.CONTINUE : Status.BREAK;
	}

	/**
	 * 设置执行器管理器
	 *
	 * @param manager 执行器管理器
	 */
	public void setExecutorManager(ExecutorManager manager) {
		this.manager = manager;
	}

	@Override
	public void doStart() {
		Assert.notNull(this.manager, "executorManager must be not null");
	}
}
