package com.dcfs.smartaibank.manager.monitor.analyzer.prepare;

import com.dcfs.smartaibank.handler.exception.HandlerException;
import com.dcfs.smartaibank.handler.Status;
import com.dcfs.smartaibank.manager.monitor.core.context.ContextHelper;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.AbstractMonitorHandler;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.PrepareHandler;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.rule.RuleCenterAccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * 日志预处理器
 *
 * @author jiazw
 */
@Slf4j
public class AddLogParamHandler extends AbstractMonitorHandler implements PrepareHandler, Constants {

	private static final String PLATFORM_DEVICE_FUNCTION_TIMECONSUME = "PLATFORM_DEVICE_FUNCTION_TIMECONSUME";
	private static final String PLATFORM_DEVICE_FUNCTION_ENDTIME = "PLATFORM_DEVICE_FUNCTION_ENDTIME";
	private static final String SERVER_UIDL_CMD = "SERVER_UIDL_CMD";
	private static final String TIME_CONSUMED = "0";
	private RuleCenterAccess ruleCenter;


	@Override
	protected Status doHandle(MonitorContext context) throws HandlerException {
		//是否为交易监控数据
		boolean cond = ContextHelper.isSameType(context, TYPE_LOG);
		if (cond) {
			String content = context.getBody(Constants.LOG_CONTENT);
			String location = context.getBody(Constants.LOG_LOCATION);
			if (content.contains(SERVER_UIDL_CMD)) {
				packTransOpen(context);
				//系统不包含的交易类型无需记录
				String tranName = ruleCenter.getDictCode(Constants.TRAN_TYPE, context.get(Constants.TRAN_CODE));
				if (tranName == null || "".equals(tranName)) {
					return Status.BREAK;
				} else {
					context.put(Constants.TRAN_NAME, tranName);
				}
				context.put(Constants.ACTION, Constants.ACTION_TRANS_OPEN);
			} else if (Constants.LOG_LOCATION_VIEW_LIFECYCLE.equals(location)) {
				packTransClose(context);
				context.put(Constants.ACTION, Constants.ACTION_TRANS_CLOSE);
			} else if (Constants.LOG_LOCATION_LOGIC_SERVER_PROXY.equals(location)) {
				//服务调用仅需记录服务调用完成
				packServiceCall(context);
				context.put(Constants.ACTION, Constants.ACTION_SERVICE_CALL);
				return Status.BREAK;
			} else if (Constants.LOG_LOCATION_LOGIC_LOCAL_SERVICE.equals(location)) {
				packServiceEnd(context);
				context.put(Constants.ACTION, Constants.ACTION_SERVICE_END);
				context.put(Constants.SERVICE_TYPE, Constants.SERVICE_TYPE_LOCAL);
			} else if (Constants.LOG_LOCATION_LOGIC_REMOTE_SERVICE.equals(location)) {
				packServiceEnd(context);
				context.put(Constants.ACTION, Constants.ACTION_SERVICE_END);
				context.put(Constants.SERVICE_TYPE, Constants.SERVICE_TYPE_REMOTE);
			} else if (Constants.LOG_LOCATION_LOGIC_COMPOSITE_SERVICE.equals(location)) {
				packServiceEnd(context);
				context.put(Constants.ACTION, Constants.ACTION_SERVICE_END);
				context.put(Constants.SERVICE_TYPE, Constants.SERVICE_TYPE_COMPOSITE);
			} else if (Constants.LOG_LOCATION_SERVICE_OVERTIME.equals(location)) {
				packServiceEnd(context);
				context.put(Constants.ACTION, Constants.ACTION_SERVICE_OVERTIME);
			} else if ((content.contains(PLATFORM_DEVICE_FUNCTION_TIMECONSUME))
				&& (content.indexOf("Status|") < 1)) {
				packDeviceResponce(context);
				if (TIME_CONSUMED.equals(context.get(Constants.TIME_CONSUMED))) {
					return Status.BREAK;
				}
				context.put(Constants.ACTION, Constants.ACTION_DEVICE_RESPONSE);
			} else if ((content.contains(PLATFORM_DEVICE_FUNCTION_ENDTIME))
				&& (content.indexOf("_STATUS_FINISH(") < 1)) {
				//系统不包含的驱动调用无需记录
				if (ruleCenter != null) {
					if (!packDeviceCall(context, ruleCenter)) {
						return Status.BREAK;
					}
				}
				context.put(Constants.ACTION, Constants.ACTION_DEVICE_CALL);
			}
		}

		return Status.CONTINUE;
	}

	private void packServiceCall(MonitorContext context) {
		String logcontent = context.getBody(Constants.LOG_CONTENT);
		String[] logs = logcontent.split("\\|");
		String[] services = logs[0].split(",");
		context.put("LOG_ACTION", "ADD");
		context.put(Constants.SERVICE_CODE, services[0]);
		context.put(Constants.MESSAGE_TYPE, services[1]);
		context.put(Constants.MESSAGE_CODE, services[2]);
		context.put(Constants.TIME_CONSUMED, "0");
	}

	private void packServiceEnd(MonitorContext context) {
		String logcontent = context.getBody(Constants.LOG_CONTENT);
		String[] logs = logcontent.split(",");
		context.put("LOG_ACTION", "UPDATE");
		context.put(Constants.TIME_CONSUMED, logs[0].split(":")[1]);
		String service = logs[1].split(":")[1];
		String[] services = service.split("\\|");
		context.put(Constants.SERVICE_CODE, services[0]);
		context.put(Constants.MESSAGE_TYPE, services[1]);
		context.put(Constants.MESSAGE_CODE, services[2]);
	}

	private void packDeviceResponce(MonitorContext context) {
		String logcontent = context.getBody(Constants.LOG_CONTENT);
		String[] logs = logcontent.split("\\|");
		context.put("LOG_ACTION", "ADD");
		context.put("DEVICE_ID", logs[0]);
		context.put("FUNCTION_NAME", logs[1]);
		context.put(Constants.TIME_CONSUMED, logs[2].split(":")[1]);
	}

	private boolean packDeviceCall(MonitorContext context, RuleCenterAccess rc) {
		boolean flag = false;
		String logcontent = context.getBody(Constants.LOG_CONTENT);
		String[] logs = logcontent.split("\\|");
		context.put("EVENT_CODE", logs[0]);
		Map<String, String> eventmap = rc.getVtmDeviceParams(logs[0]);
		if (eventmap.size() > 0) {
			flag = true;
			if (!("".equals(eventmap.get(Constants.SOURCE_EVENT_CODE)))
				&& eventmap.get(Constants.SOURCE_EVENT_CODE) != null) {
				context.put("FUNCTION_NAME", eventmap.get(Constants.SOURCE_EVENT_CODE));
				context.put("LOG_ACTION", "UPDATE");
			} else {
				context.put("FUNCTION_NAME", eventmap.get("EVENT_CODE"));
				context.put("LOG_ACTION", "ADD");
			}
			context.put("DEVICE_ID", eventmap.get("DEVICE_ID"));
		}
		context.put("TIME_FINISHED", logs[2].split(":")[1]);
		return flag;
	}

	private void packTransOpen(MonitorContext context) {
		String logcontent = context.getBody(Constants.LOG_CONTENT);
		String[] logs = logcontent.split("\\|");
		context.put("LOG_ACTION", "ADD");
		context.put(Constants.TRAN_CODE, logs[0]);
		context.put(Constants.TIME_CONSUMED, "0");
	}

	private void packTransClose(MonitorContext context) {
		String logcontent = context.getBody(Constants.LOG_CONTENT);
		String[] logs = logcontent.split(",");
		context.put("LOG_ACTION", "UPDATE");
		context.put(Constants.TIME_CONSUMED, logs[0].split(":")[1]);
	}

	/**
	 * 设置规则中心
	 *
	 * @param ruleCenter 规则中心
	 */
	public void setRuleCenter(RuleCenterAccess ruleCenter) {
		this.ruleCenter = ruleCenter;
	}

	@Override
	protected void doStart() {
		Assert.notNull(this.ruleCenter, "ruleCenter must be not null");
	}
}
