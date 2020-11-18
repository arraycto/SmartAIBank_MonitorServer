package com.dcfs.smartaibank.manager.monitor.notify;

import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.exception.ExecutorException;
import com.dcfs.smartaibank.manager.monitor.core.exception.NotifyException;
import com.dcfs.smartaibank.manager.monitor.core.executor.ExecuteDefine;
import com.dcfs.smartaibank.manager.monitor.core.executor.ExecutorManager;
import com.dcfs.smartaibank.manager.monitor.notify.handler.NotifyHandler;
import com.dcfs.smartaibank.manager.monitor.rule.domain.NotifyRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通知管理器实现类
 *
 * @author jiazw
 */
@Slf4j
public class NotifyManagerImpl implements NotifyManager {
	private final Map<String, NotifyHandler> handlers = new HashMap<>(16);

	private ExecutorManager executorManager;

	public NotifyManagerImpl(ExecutorManager executorManager) {
		Assert.notNull(executorManager, "executorManager must be not null.");
		this.executorManager = executorManager;
	}

	/**
	 * 通知
	 *
	 * @param context 上下文
	 * @throws NotifyException 通知异常
	 */
	@Override
	public void notify(MonitorContext context) throws NotifyException {
		List<NotifyRule> rules = context.get("CURRENT_NOTIFY_RULES");
		if (rules != null) {
			for (NotifyRule rule : rules) {
				if (rule != null && rule.isActive()) {
					notifySingle(context, rule);
				}
			}
		} else {
			log.info("不存在通知规则");
		}
	}

	/**
	 * 添加通知执行器
	 *
	 * @param name     执行器名称
	 * @param executor 执行器
	 */
	@Override
	public void addNotifyHandler(@NotNull String name, @NotNull NotifyHandler executor) {
		if (this.handlers.containsKey(name)) {
			log.warn("[{}]执行器已存在，进行覆盖处理", name);
		}
		this.handlers.put(name, executor);
	}

	private void notifySingle(MonitorContext context, NotifyRule rule) throws NotifyException {
		ExecuteDefine define = rule.getExecutor();
		try {
			Object obj = executorManager.execute(context, define);
			if (obj instanceof Boolean && (Boolean) obj) {
				NotifyHandler notifyHandler = this.handlers.get(rule.getNotifyType());
				if (notifyHandler != null) {
					notifyHandler.handle(context, rule);
				} else {
					log.error("[{}]没有找到指定的通知执行器，NOTIFY_TYPE={}", context.getUUID(), rule.getNotifyType());
				}
			} else {
				log.error("[{}]执行器执行结果应为Boolean类型，实际类型为：{}",
					context.getUUID(), obj.getClass().getSimpleName());
			}
		} catch (ExecutorException e) {
			throw new NotifyException("notify.handler.error", e);
		}
	}
}
