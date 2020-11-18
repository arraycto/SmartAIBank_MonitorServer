package com.dcfs.smartaibank.manager.monitor.analyzer.service.impl;

import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.ContextHelper;
import com.dcfs.smartaibank.manager.monitor.core.util.DateUtil;
import com.dcfs.smartaibank.manager.monitor.analyzer.dao.DeviceSummaryDao;
import com.dcfs.smartaibank.manager.monitor.analyzer.service.DeviceSummaryService;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.rule.domain.DeviceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 外设汇总服务
 *
 * @author jiazw
 */
@Slf4j
@Component("deviceSummaryService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
public class DeviceSummaryServiceImpl implements DeviceSummaryService {
	@Autowired
	private DeviceSummaryDao deviceSummaryDao;

	@Override
	public void insertOrUpdateInfo(MonitorContext context) {
		String mac = context.getBody(Constants.MAC);
		String date = DateUtil.getStrDate(new Date());
		Map<String, Object> param = new HashMap<>(16);
		param.put(Constants.MAC, mac);
		param.put(Constants.RECEIVE_TIME, date);
		Map<String, Object> status = deviceSummaryDao.queryStatus(param);
		Map<String, Object> eachGatherStatus = new HashMap<>(6);
		try {
			eachGatherStatus = getEachGatherStatus(param);
		} catch (Exception e) {
			log.error("xssdsdsds", e);
		}
		status.putAll(eachGatherStatus);
		try {
			if (status != null) {
				int num = deviceSummaryDao.update(status);
				if (num <= 0) {
					setAreaInfo(context);
					Map<String, Object> info = getSumInfo(context);
					info.putAll(eachGatherStatus);
					deviceSummaryDao.insert(info);
					if (log.isDebugEnabled()) {
						log.debug("[{}]保存机具信息成功。MAC={}", context.get("UUID"), mac);
					}
				} else {
					if (log.isDebugEnabled()) {
						log.debug("[{}]更新机具信息成功。{}", context.get("UUID"), status);
					}
				}
			}
		} catch (Exception e) {
			log.error("[{}]外设汇总数据插入失败。", (String) context.get("UUID"), e);
			//在并发处理时可能存在判断完无数据后其他线程插入了数据，这是会抛出主键重复异常
			//在此处进行一次更新操作
			deviceSummaryDao.update(status);
		}
	}

	private void setAreaInfo(MonitorContext context) {
		String branchNo = context.getBody(Constants.BRANCH_NO);
		Map<String, Object> branchInfo = deviceSummaryDao.queryBranchInfo(branchNo);
		context.put(Constants.HANDLE_PROVINCE, branchInfo.get(Constants.HANDLE_PROVINCE));
		context.put(Constants.HANDLE_CITY, branchInfo.get(Constants.HANDLE_CITY));
		context.put(Constants.HANDLE_COUNTY, branchInfo.get(Constants.HANDLE_COUNTY));
	}

	private Map<String, Object> getSumInfo(MonitorContext context) {
		Map<String, Object> map = new HashMap<>(16);
		ContextHelper.fillFromBody(context, map);
		Date now = new Date();
		long nowL = DateUtil.getTime(now);
		map.put(Constants.RECEIVE_TIME, now);
		map.put(Constants.RECEIVE_TIME_INT, nowL);
		map.put(Constants.M_STATUS, context.get(Constants.M_STATUS));
		map.put(Constants.STATUS_DESC, context.get(Constants.STATUS_DESC));
		map.put(Constants.HANDLE_PROVINCE, context.get(Constants.HANDLE_PROVINCE));
		map.put(Constants.HANDLE_CITY, context.get(Constants.HANDLE_CITY));
		map.put(Constants.HANDLE_COUNTY, context.get(Constants.HANDLE_COUNTY));
		map.put(Constants.HANDLE_COUNTY, context.get(Constants.HANDLE_COUNTY));
		return map;
	}

	private Map<String, Object> getEachGatherStatus(Map<String, Object> params) {
		Map<String, Object> map = new HashMap<>(6);
		// 这里需要加上新加的字段,需要从数据库查询
		List<DeviceInfo> deviceInfos = deviceSummaryDao.selectSingleDeviceInfo(params);
		Optional<DeviceInfo> peripheralMax = deviceInfos.parallelStream()
			.filter(deviceInfo -> Constants.TYPE_DEVICE.equals(deviceInfo.getMonitorType()))
			.max(Comparator.comparingInt(DeviceInfo::getStatus));
		if (peripheralMax.isPresent()) {
			DeviceInfo deviceInfo = peripheralMax.get();
			map.put(Constants.PERIPHERAL_STATUS, deviceInfo.getStatus());
			map.put(Constants.PERIPHERAL_TIME, deviceInfo.getReceiveTime());
		} else {
			map.put(Constants.PERIPHERAL_STATUS, null);
			map.put(Constants.PERIPHERAL_TIME, null);
		}
		Optional<DeviceInfo> appInfo =
			deviceInfos.parallelStream().filter(deviceInfo ->
				Constants.TYPE_APP.equals(deviceInfo.getMonitorType())).findFirst();
		if (appInfo.isPresent()) {
			DeviceInfo deviceInfo = appInfo.get();
			map.put(Constants.APP_STATUS, deviceInfo.getStatus());
			map.put(Constants.APP_TIME, deviceInfo.getReceiveTime());
		} else {
			map.put(Constants.APP_STATUS, null);
			map.put(Constants.APP_TIME, null);
		}
		Optional<DeviceInfo> networkInfo =
			deviceInfos.parallelStream()
				.filter(deviceInfo -> Constants.TYPE_NETWORK.equals(deviceInfo.getMonitorType())).findFirst();
		if (networkInfo.isPresent()) {
			DeviceInfo deviceInfo = networkInfo.get();
			map.put(Constants.NETWORK_STATUS, deviceInfo.getStatus());
			map.put(Constants.NETWORK_TIME, deviceInfo.getReceiveTime());
		} else {
			map.put(Constants.NETWORK_STATUS, null);
			map.put(Constants.NETWORK_TIME, null);
		}
		return map;
	}
}
