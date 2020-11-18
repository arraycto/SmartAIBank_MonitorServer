package com.dcfs.smartaibank.manager.monitor.analyzer.service.impl;

import com.dcfs.smartaibank.manager.monitor.analyzer.service.ServiceManager;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.ContextHelper;
import com.dcfs.smartaibank.manager.monitor.analyzer.service.FaultRecordService;
import com.dcfs.smartaibank.manager.monitor.analyzer.dao.FaultRecordDao;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 故障历史记录保存服务类
 *
 * @author ligg
 */
@Slf4j
@Component("faultRecordService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
public class FaultRecordServiceImpl implements FaultRecordService, InitializingBean {
	@Autowired
	private FaultRecordDao faultDao;

	@Autowired
	private ServiceManager serviceManager;

	@Override
	public void insertInfo(MonitorContext context) {
	}

	@Override
	public void updateInfo(MonitorContext context) {

	}

	@Override
	public void insertOrUpdateInfo(MonitorContext context) {
		String type = ContextHelper.getType(context);
		long start = System.currentTimeMillis();
		log.info("###### 开始处理故障记录信息>>>>>>>:");
		// 构造查询条件
		Map<String, Object> queryMap = new HashMap<>(16);
		queryMap.put(Constants.BRANCH_NO, context.getBody(Constants.BRANCH_NO));
		queryMap.put(Constants.M_TYPE, type);
		queryMap.put(Constants.MAC, context.getBody(Constants.MAC));
		if (type.equals(ContextHelper.TYPE_DEVICE)) {
			queryMap.put(Constants.DEVCLASSKEY, context.getBody(Constants.DEVCLASSKEY));
			queryMap.put(Constants.DEVMODELKEY, context.getBody(Constants.DEVMODELKEY));
		}
		String reportDate = DateUtil.getStrSplitDate(new Date());
		queryMap.put("REPORT_DATE", reportDate);

		// 根据查询条件查询当天是否存在同种历史记录，且查询结果按照故障开始时间倒序排列
		List<Map<String, Object>> result = faultDao.queryInfo(queryMap);
		Integer status = -1;

		// 如果故障类型为APP，则状态从body中获取
		if (ContextHelper.TYPE_APP.equals(type)) {
			status = context.getBody(Constants.STATUS);
		} else {
			status = context.get(Constants.STATUS);
		}

		boolean alarmresult = Boolean.parseBoolean(context.get(Constants.ALARM_RESULT).toString());
		//存在预警信息，新增或者无需新增故障记录
		if (alarmresult) {
			// 如果查询故障结果为空，且当前记录状态为故障态，则记录故障信息
			boolean r = (result == null || result.isEmpty()) && status == 3;
			if (r) {
				Map<String, Object> deviceInfo = getDeviceInfo(context);
				faultDao.insertInfo(deviceInfo);
			}
		} else {
			//不存在预警信息，判断原故障记录是否可结束
			if (status == 1) {
				for (Map<String, Object> recordMap : result) {
					BigDecimal recordStatus = (BigDecimal) recordMap.get(Constants.STATUS);
					int statusNum = recordStatus.intValue();
					// 如果当前状态和查询出的故障状态不一致且查询出的故障结束时间为空，则表示故障已恢复，将故障结束时间更新为当前时间
					if (statusNum != status) {
						Map<String, Object> updateMap = new HashMap<>(16);
						updateMap.put(Constants.SEQ_NO, recordMap.get(Constants.SEQ_NO));
						Date now = context.get(Constants.RECEIVE_TIME);
						long nowL = DateUtil.getTime(now);
						updateMap.put("END_TIME", now);
						updateMap.put("END_TIME_INT", nowL);
						faultDao.updateInfo(updateMap);
					}
				}
			}
			log.info("###### 处理故障记录信息结束 ，耗时[{}]ms:", (System.currentTimeMillis() - start));
		}

	}

	@Override
	public List<Map<String, Object>> insertAlarm(MonitorContext context) {
		return null;
	}

	private Map<String, Object> getDeviceInfo(MonitorContext context) {
		Map<String, Object> map = new HashMap<>(16);
		ContextHelper.fillFromBody(context, map);
		String mType = context.getType();
		// 接收时间修改为从上下文中获取
		Date now = context.get(Constants.RECEIVE_TIME);
		long nowL = DateUtil.getTime(now);
		map.put(Constants.M_TYPE, mType);
		map.put(Constants.STATUS_CODE, context.get(Constants.STATUS_CODE));
		map.put(Constants.STATUS_DESC, context.get(Constants.STATUS_DESC));
		map.put("START_TIME", now);
		map.put("START_TIME_INT", nowL);
		map.put(Constants.M_STATUS, context.get(Constants.M_STATUS));
		if (ContextHelper.TYPE_APP.equals(mType)) {
			map.put(Constants.STATUS, context.getBody(Constants.STATUS));
		} else {
			map.put(Constants.STATUS, context.get(Constants.STATUS));
		}

		return map;
	}

	@Override
	public void afterPropertiesSet() {
		this.serviceManager.addService(Constants.TYPE_FAULT, this);
	}
}
