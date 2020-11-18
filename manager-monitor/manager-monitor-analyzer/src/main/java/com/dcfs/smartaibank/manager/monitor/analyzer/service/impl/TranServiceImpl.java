package com.dcfs.smartaibank.manager.monitor.analyzer.service.impl;

import com.dcfs.smartaibank.manager.monitor.analyzer.service.ServiceManager;
import com.dcfs.smartaibank.manager.monitor.core.context.ContextHelper;
import com.dcfs.smartaibank.manager.monitor.analyzer.service.ServiceHelper;
import com.dcfs.smartaibank.manager.monitor.core.util.DateUtil;
import com.dcfs.smartaibank.manager.monitor.analyzer.dao.DeviceSummaryDao;
import com.dcfs.smartaibank.manager.monitor.analyzer.dao.TranDao;
import com.dcfs.smartaibank.manager.monitor.analyzer.service.TranService;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.rule.domain.AlarmLevel;
import com.dcfs.smartaibank.manager.monitor.rule.domain.MonitorItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 交易监控预警服务实现类
 *
 * @author jiazw
 */
@Slf4j
@Component("tranService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
public class TranServiceImpl implements TranService, InitializingBean {

	private static final int DATE_LENGTH = 19;
	@Autowired
	private TranDao tranDao;
	@Autowired
	private DeviceSummaryDao deviceSummaryDao;
	@Autowired
	private ServiceManager serviceManager;

	/**
	 * 保存交易信息
	 *
	 * @param context 上下文
	 */
	@Override
	public void insertInfo(MonitorContext context) {
		Map<String, Object> map = getTranInfo(context);
		tranDao.insert(map);
	}

	/**
	 * 更新交易信息
	 *
	 * @param context 上下文
	 */
	@Override
	public void updateInfo(MonitorContext context) {
		Map<String, Object> map = getTranInfo(context);
		tranDao.update(map);
	}

	@Override
	public void insertOrUpdateInfo(MonitorContext context) {
		Map<String, Object> map = getTranInfo(context);
		int i = tranDao.update(map);
		if (i <= 0) {
			tranDao.insert(map);
		}
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> insertAlarm(MonitorContext context) {
		Map<String, Object> param = getTranAlarmInfo(context);
		List<Map<String, Object>> list = context.get(Constants.ALARM_LEVEL_LIST);

		List<Map<String, Object>> result = new ArrayList<>();

		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				int alarmLevel = ((AlarmLevel) map.get(Constants.ALARM_LEVEL)).getCode();
				String alarmDesc = (String) map.get(Constants.ALARM_DESC);
				String alarmRuleId = (String) map.get(Constants.ALARM_RULE_ID);
				param.put(Constants.ALARM_LEVEL, alarmLevel);
				param.put(Constants.ALARM_DESC, alarmDesc);
				param.put(Constants.ALARM_RULE_ID, alarmRuleId);
				int c = tranDao.insertAlarm(param);
				// 插入成功
				if (c > 0) {
					setAlarmData(map, param);
					result.add(map);
				} else {
					map.put(Constants.ALARM_RESULT, false);
				}
			}
		}
		return result;
	}

	@SuppressWarnings("unused")
	private void insertOrUpdateSum(Map<String, Object> map) {
		Date date = (Date) map.get("TRAN_DATE");
		String dateStr = DateUtil.getStrDate(date);
		map.put("TRAN_DATE_STR", dateStr);
		int count = tranDao.updateSum(map);
		if (count <= 0) {
			tranDao.insertSum(map);
		}
	}

	private void setAlarmData(Map<String, Object> target, Map<String, Object> source) {
		ServiceHelper.setAlarmData(source, target);
	}

	private Map<String, Object> getTranInfo(MonitorContext context) {
		Map<String, Object> branchInfo = setAreaInfo(context);
		Map<String, Object> param = new HashMap<>(16);
		ContextHelper.fillFromBody(context, param);
		// 获得交易日期
		Date date = getTranDate(context.getBody("TRAN_DATE"));
		//交易时间
		param.put("TRAN_DATE", date);

		if (context.get(Constants.TRAN_RET_DESC) != null) {
			//交易返回码描述
			param.put(Constants.TRAN_RET_DESC, context.get(Constants.TRAN_RET_DESC));
		}

		param.putAll(branchInfo);
		return param;
	}

	/**
	 * 获取交易日期
	 *
	 * @return
	 */
	private Date getTranDate(Object tranDateObj) {
		Date date = null;
		if (tranDateObj != null && !"".equals(tranDateObj)) {
			if (tranDateObj instanceof Date) {
				date = (Date) tranDateObj;
			} else if (tranDateObj instanceof String) {
				String dateStr = String.valueOf(tranDateObj);
				int len = dateStr.length();
				if (len != DATE_LENGTH) {
					log.info("原始报文交易日期错误[{}]", tranDateObj);
				} else {
					String year = dateStr.substring(0, 4);
					String month = dateStr.substring(5, 7);
					String day = dateStr.substring(8, 10);
					String hour = dateStr.substring(11, 13);
					String minute = dateStr.substring(14, 16);
					String second = dateStr.substring(17);
					GregorianCalendar calendar = new GregorianCalendar();
					calendar.set(Integer.parseInt(year),
						Integer.parseInt(month) - 1, Integer.parseInt(day),
						Integer.parseInt(hour), Integer.parseInt(minute),
						Integer.parseInt(second));
					date = calendar.getTime();
				}
			}
		} else {
			log.info("原始报文交易日期错误[{}]", tranDateObj);
		}
		if (date == null) {
			date = new Date();
		}
		return date;
	}

	private Map<String, Object> getTranAlarmInfo(MonitorContext context) {
		Map<String, Object> param = new HashMap<>(16);
		ContextHelper.fillFromBody(context, param);
		MonitorItem item = context.get(Constants.CURRENT_MONITOR_ITEM);
		String itemId = item.getId();
		//预警时间
		param.put(Constants.ALARM_DATE, new Date());
		//监控项ID
		param.put(Constants.MONITOR_ITEM_ID, itemId);
		//初始状态为1
		param.put(Constants.ALARM_STATUS, Constants.ALARM_NEW);
		//交易类型名称
		param.put(Constants.TRAN_TYPE_NAME, context.get(Constants.TRAN_TYPE_NAME));
		//交易卡类型名称
		param.put(Constants.TRAN_CARD_TYPE_NAME, context.get(Constants.TRAN_CARD_TYPE_NAME));
		return param;
	}

	private Map<String, Object> setAreaInfo(MonitorContext context) {
		String branchNo = context.getBody(Constants.BRANCH_NO);
		Map<String, Object> branchInfo = deviceSummaryDao.queryBranchInfo(branchNo);
		context.put(Constants.HANDLE_PROVINCE, branchInfo.get(Constants.HANDLE_PROVINCE));
		context.put(Constants.HANDLE_CITY, branchInfo.get(Constants.HANDLE_CITY));
		context.put(Constants.HANDLE_COUNTY, branchInfo.get(Constants.HANDLE_COUNTY));
		return branchInfo;
	}

	@Override
	public void afterPropertiesSet() {
		this.serviceManager.addService(Constants.TYPE_TRAN, this);
	}
}

