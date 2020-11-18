package com.dcfs.smartaibank.manager.monitor.analyzer.prepare;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.dcfs.smartaibank.handler.exception.HandlerException;
import com.dcfs.smartaibank.handler.Status;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.AbstractMonitorHandler;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.PrepareHandler;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.rule.RuleCenterAccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

/**
 * 添加机具是否在工作时间内的预处理器
 * Key:SMS_FLAG
 *
 * @author jiazw
 */
@Slf4j
public class WorkTimePrepareHandler extends AbstractMonitorHandler implements PrepareHandler, Constants {

	private RuleCenterAccess ruleCenter;

	@Override
	protected Status doHandle(MonitorContext context) throws HandlerException {
		//判断机具是否在工作时间
		String time = context.getHeader("CREATE_TIME");
		SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String devKey = context.getBody(Constants.BRANCH_NO) + "_" + context.getBody(Constants.MID);
		boolean flag = false;
		List<Date[]> timeList = ruleCenter.getWorkTimeRules(devKey);
		if (timeList.size() > 0) {
			for (int i = 0; i < timeList.size(); i++) {
				Date[] map = timeList.get(i);
				try {
					Date dateTime = dfs.parse(time);
					if (map[0].before(dateTime) && map[1].after(dateTime)) {
						flag = true;
						break;
					}
				} catch (ParseException e) {
					throw new HandlerException("handler.error", e);
				}
			}
		}
		context.put("SMS_FLAG", flag);

		return Status.CONTINUE;
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
