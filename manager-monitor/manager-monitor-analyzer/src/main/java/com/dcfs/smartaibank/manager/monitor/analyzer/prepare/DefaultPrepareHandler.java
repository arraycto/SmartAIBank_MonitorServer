package com.dcfs.smartaibank.manager.monitor.analyzer.prepare;

import com.dcfs.smartaibank.handler.exception.HandlerException;
import com.dcfs.smartaibank.handler.Status;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.AbstractMonitorHandler;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.PrepareHandler;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.executor.ExecuteDefine;
import com.dcfs.smartaibank.manager.monitor.core.executor.ExecutorManager;
import com.dcfs.smartaibank.manager.monitor.rule.domain.PrepareRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 默认预处理器
 *
 * @author jiazw
 */
@Slf4j
public class DefaultPrepareHandler extends AbstractMonitorHandler implements PrepareHandler, Constants {

	private ExecutorManager manager;

	@SuppressWarnings("unchecked")
	@Override
	protected Status doHandle(MonitorContext context) throws HandlerException {
		//获取当前规则
		List<PrepareRule> rules = context.get(CURRENT_PREPARE_RULES);
		if (rules != null) {
			for (PrepareRule rule : rules) {
				if (rule.isActive()) {
					ExecuteDefine target = rule.getExecutor();
					if (target != null) {
						try {
							Object result = manager.execute(context, target);
							String key = target.getKey();
							if (key != null && !"".equals(key)) {
								context.put(key, result);
								log.debug("[{}]预处理器[ID={}，DESC={}]，新增字段[{}={}]",
									context.getUUID(), rule.getId(), rule.getDescription(), key, result);
							}
						} catch (Exception e) {
							throw new HandlerException("executor.error", e);
						}
					}
				}
			}
		}
		return Status.CONTINUE;
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
	protected void doStart() {
		Assert.notNull(this.manager, "manager must be not null");
	}
}
