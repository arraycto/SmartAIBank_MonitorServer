package com.dcfs.smartaibank.manager.monitor.core.executor.impl;

import com.dcfs.smartaibank.core.util.TypeUtil;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.exception.ExecutorException;
import com.dcfs.smartaibank.manager.monitor.core.executor.ExecuteDefine;
import com.dcfs.smartaibank.manager.monitor.core.executor.Executor;
import com.dcfs.smartaibank.manager.monitor.core.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.mvel2.DataConversion;
import org.springframework.beans.BeansException;

/**
 * 自定义执行器
 *
 * @author jiazw
 */
@Slf4j
public class BeanExpressionExecutor implements Executor {
	@SuppressWarnings("unchecked")
	@Override
	public <T> T execute(MonitorContext context, ExecuteDefine target) {
		String uuid = context.get("UUID");
		if (target != null) {
			String beanName = target.getExecutor();
			if (beanName != null && !"".equals(beanName)) {
				try {
					Executor executor = SpringContextUtil.getBean(beanName, Executor.class);

					Object result = executor.execute(context, target);
					Class<T> typeClazz = (Class<T>) TypeUtil.fromName(target.getReturnType());
					if (typeClazz != null) {
						return DataConversion.convert(result, typeClazz);
					} else {
						log.error("[{}]执行器返回类型有误，返回类型只能为基础类型。", uuid);
					}
				} catch (BeansException e) {
					log.error("[{}]从spring容器中获取执行器失败，beanName:{}", uuid, beanName, e);
				} catch (ExecutorException e) {
					log.error("[{}]执行自定义执行器失败。CLASS_NAME:{}", uuid, beanName, e);
				}
			} else {
				log.error("[{}]执行器定义的beanName为空！", uuid);
			}
		} else {
			log.error("[{}]执行器定义为空，无法执行！", uuid);
		}

		return null;
	}

}
