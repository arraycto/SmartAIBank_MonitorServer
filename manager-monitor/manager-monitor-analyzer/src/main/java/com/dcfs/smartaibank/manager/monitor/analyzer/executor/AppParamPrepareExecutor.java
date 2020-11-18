package com.dcfs.smartaibank.manager.monitor.analyzer.executor;

import com.dcfs.smartaibank.manager.monitor.analyzer.service.CommonService;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.executor.ExecuteDefine;
import com.dcfs.smartaibank.manager.monitor.core.executor.Executor;
import com.dcfs.smartaibank.manager.monitor.core.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 应用参数预处理执行器
 *
 * @author jiazw
 */
@Slf4j
@Component
public class AppParamPrepareExecutor implements Executor, Constants, InitializingBean {

	@Autowired
	private CommonService commonService;



	@SuppressWarnings("unchecked")
	@Override
	public Boolean execute(MonitorContext context, ExecuteDefine target) {
			//类型
			String type = context.getType();
			//MAC
			String mac = context.getBody(Constants.MAC);
			//状态
			Object statusObj = context.getBody(Constants.STATUS);
			Integer status = 1;
			if (statusObj instanceof Integer) {
				status = (Integer) statusObj;
			} else if (statusObj instanceof String) {
				status = Integer.parseInt(String.valueOf(statusObj));
			}
			Map<String, String> params = context.get(PARAMS);
			boolean flag = false;
			CommonService service = (CommonService) SpringContextUtil.getBean("commonService");
			if (params != null) {
				String countStr = params.get("APP_COUNT");
				Integer count = Integer.parseInt(countStr);
				Map<String, Object> map = new HashMap<>(16);
				map.put("M_KEY", mac);
				map.put(Constants.M_TYPE, type);
				map.put("M_VALUE", String.valueOf(status));
				map.put("CCOUNT", count);
				flag = service.isExpectCount(map);
			} else {
				log.error("[{}]监控预警参数为空！", (String) context.get("UUID"));
			}
			return flag;
	}

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(this.commonService, "commonService must be not null");
	}
}
