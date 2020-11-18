package com.dcfs.smartaibank.manager.monitor.rule.impl;

import com.dcfs.smartaibank.manager.monitor.core.template.TemplateDefine;
import com.dcfs.smartaibank.manager.monitor.rule.RuleCenterAccess;
import com.dcfs.smartaibank.manager.monitor.rule.RuleCenterManager;
import com.dcfs.smartaibank.manager.monitor.rule.domain.AlarmRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.FilterRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.NotifyRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.PrepareRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.RealRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.Rule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.TimedRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.DeviceModelCode;
import com.dcfs.smartaibank.manager.monitor.rule.domain.ParamCode;
import com.dcfs.smartaibank.manager.monitor.rule.domain.TranCode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 规则中心默认实现
 *
 * @author jiazw
 */
@Component("ruleCenter")
public class RuleCenterImpl implements RuleCenterManager, RuleCenterAccess {

	/**
	 * 设备详细信息描述
	 */
	private Map<String, String> deviceDescs = new ConcurrentHashMap<>();

	/**
	 * 设备管理员信息
	 */
	private Map<String, String> deviceManagers = new ConcurrentHashMap<>();

	/**
	 * 设备管理员信息
	 */
	private Map<String, Map<String, String>> deviceParams = new ConcurrentHashMap<>();

	/**
	 * 设备管理员信息
	 */
	private Map<String, Map<String, String>> vtmDeviceParams = new ConcurrentHashMap<>();

	/**
	 * 设备业务名称
	 * KEY=MCLASSKEY+DEVMODELKEY
	 */
	private Map<String, String> deviceModelNames = new ConcurrentHashMap<>();

	/**
	 * 监控预警阈值
	 */
	private Map<String, String> paramCodes = new ConcurrentHashMap<>();

	/**
	 * 数据字典
	 */
	private Map<String, Map<String, String>> dictCodes = new ConcurrentHashMap<>();

	/**
	 * 设备状态信息
	 * KEY=DEVMODELKEY+STATUS_CODE
	 */
	private Map<String, DeviceModelCode> deviceModelCodes = new ConcurrentHashMap<>();

	/**
	 * 交易码信息
	 */
	private Map<String, TranCode> tranCodes = new ConcurrentHashMap<>();

	/**
	 * 机具监控激活状态
	 */
	private Map<String, Boolean> mClassActives = new ConcurrentHashMap<>();

	/**
	 * 过滤规则集合
	 */
	private List<FilterRule> filterRules = new CopyOnWriteArrayList<>();

	/**
	 * 实时规则集合
	 * KEY=SYSTEM_ID+SYSTEM_MODULE
	 */
	private Map<String, List<RealRule>> realRules = new ConcurrentHashMap<>();

	/**
	 * 定时规则集合
	 * KEY=SYSTEM_ID+SYSTEM_MODULE
	 */
	private List<TimedRule> timedRules = new CopyOnWriteArrayList<>();

	/**
	 * 定时规则集合
	 * KEY=SYSTEM_ID+SYSTEM_MODULE
	 */
	private Map<String, List<PrepareRule>> prepareRules = new ConcurrentHashMap<>();

	/**
	 * 预警规则集合
	 * KEY=MONITORITEM
	 */
	private Map<String, List<AlarmRule>> alarmRules = new ConcurrentHashMap<>();

	/**
	 * 通知规则集合
	 * KEY=MONITORITEM
	 */
	private Map<String, List<NotifyRule>> notifyRules = new ConcurrentHashMap<>();

	/**
	 * 工作时间集合
	 * KEY=MID
	 */
	private Map<String, List<Date[]>> workTimeRules = new ConcurrentHashMap<>();

	/**
	 * 模板定义
	 */
	private Map<String, TemplateDefine> templateDefines = new ConcurrentHashMap<>();

	@Override
	public List<FilterRule> getFilterRules() {
		return filterRules;
	}

	@Override
	public void setFilterRules(List<FilterRule> filterRules) {
		this.filterRules = filterRules;
	}

	@Override
	public List<RealRule> getRealRules(String systemId, String messageType) {
		return getRule(realRules, systemId, messageType);
	}

	@Override
	public void setRealRules(List<RealRule> rules) {
		setRule(realRules, rules);
	}

	@Override
	public List<TimedRule> getTimedRules() {
		return timedRules;
	}

	@Override
	public void setTimedRules(List<TimedRule> rules) {
		this.timedRules = rules;
	}

	@Override
	public List<PrepareRule> getPrepareRules(String systemId, String messageType) {
		return getRule(prepareRules, systemId, messageType);
	}

	@Override
	public void setPrepareRules(List<PrepareRule> rules) {
		setRule(prepareRules, rules);
	}

	@Override
	public List<AlarmRule> getAlarmRules(String monitorItemId) {
		return alarmRules.get(monitorItemId);
	}

	@Override
	public void setAlarmRules(List<AlarmRule> rules) {
		setRule(alarmRules, rules);
	}

	@Override
	public List<NotifyRule> getNotifyRules(String monitorItemId) {
		return notifyRules.get(monitorItemId);
	}

	@Override
	public void setNotifyRules(List<NotifyRule> rules) {
		setRule(notifyRules, rules);
	}

	@Override
	public Map<String, String> getParamCodes() {
		return paramCodes;
	}

	@Override
	public String getParamCode(String key) {
		return paramCodes.get(key);
	}

	@Override
	public void setParamCode(ParamCode code) {
		if (code != null) {
			paramCodes.put(code.getCodeNo(), code.getCodeValue());
		}
	}

	@Override
	public DeviceModelCode getDeviceModelCode(String devModelKey, String statusCode) {
		return deviceModelCodes.get(devModelKey + statusCode);
	}

	@Override
	public void setDeviceModelCode(DeviceModelCode code) {
		if (code != null) {
			String key = code.getDeviceModelKey() + code.getStatusCode();
			deviceModelCodes.put(key, code);
		}
	}

	@Override
	public String getDeviceModelName(String mClassKey, String devModelKey) {
		return deviceModelNames.get(mClassKey + devModelKey);
	}

	@Override
	public void setDeviceModelName(String key, String devModelName) {
		deviceModelNames.put(key, devModelName);
	}

	@Override
	public TranCode getTranCode(String tranCode, String tranRetCode) {
		return tranCodes.get(tranRetCode);
	}

	@Override
	public void setTranCode(TranCode code) {
		if (code != null) {
			String key = code.getTranRetCode();
			tranCodes.put(key, code);
		}
	}

	@Override
	public String getDeviceManager(String key) {
		return deviceManagers.get(key);
	}

	@Override
	public void setDeviceManager(String name, String desc) {
		deviceManagers.put(name, desc);

	}

	@Override
	public Map<String, String> getDeviceParams(String key) {
		return deviceParams.get(key);
	}

	@Override
	public void setDeviceParams(String key, Map<String, String> parammap) {
		deviceParams.put(key, parammap);

	}

	@Override
	public Map<String, String> getVtmDeviceParams(String key) {
		Set<String> keyset = vtmDeviceParams.keySet();
		for (String tempk : keyset) {
			if (key.indexOf(tempk) > 0) {
				return vtmDeviceParams.get(tempk);
			}
		}
		return new HashMap<>(1);
	}

	@Override
	public void setVtmDeviceParams(String key, Map<String, String> parammap) {
		vtmDeviceParams.put(key, parammap);
	}


	@Override
	public Map<String, Map<String, String>> getDictCodes() {
		return dictCodes;
	}

	@Override
	public void setDictCode(ParamCode code) {
		if (code != null) {
			String codeNo = code.getCodeNo();
			Map<String, String> dictCode = dictCodes.get(codeNo);
			if (dictCode == null) {
				dictCode = new ConcurrentHashMap<>(16);
				dictCodes.put(codeNo, dictCode);
			}
			String codeValue = code.getCodeValue();
			String codeDesc = code.getCodeDesc();
			if (codeValue != null && codeDesc != null) {
				dictCode.put(codeValue, codeDesc);
			}
		}
	}

	@Override
	public String getDictCode(String codeNo, String codeValue) {
		String result = "";
		Map<String, String> map = dictCodes.get(codeNo);
		if (map != null) {
			result = map.get(codeValue);
		}
		return result;
	}

	@Override
	public String getDeviceDesc(String key) {
		return deviceDescs.get(key);
	}

	@Override
	public void setDeviceDesc(String name, String desc) {
		deviceDescs.put(name, desc);

	}

	@Override
	public List<Date[]> getWorkTimeRules(String devKey) {
		List<Date[]> list = new ArrayList<Date[]>();
		if (workTimeRules.containsKey(devKey)) {
			list = workTimeRules.get(devKey);
		}
		return list;
	}

	@Override
	public void setWorkTimeRules(Map<String, List<Date[]>> workTimeRules) {
		this.workTimeRules = workTimeRules;
	}

	/**
	 * 获取模板定义
	 *
	 * @param templateId 模板ID
	 * @return 模板定义
	 */
	@Override
	public TemplateDefine getTemplateDefine(String templateId) {
		return this.templateDefines.get(templateId);
	}

	/**
	 * 设置模板定义集合
	 *
	 * @param templateDefines 模板定义集合
	 */
	@Override
	public void setTemplateDefine(List<TemplateDefine> templateDefines) {
		if (templateDefines != null) {
			for (TemplateDefine define : templateDefines) {
				if (define != null) {
					this.templateDefines.put(define.getId(), define);
				}
			}
		}
	}

	@Override
	public boolean isActiveForMClass(String mClassKey) {
		return mClassActives.get(mClassKey) == null ? false : mClassActives.get(mClassKey);
	}

	@Override
	public void setMClassMonitor(String mClassKey, boolean active) {
		mClassActives.put(mClassKey, active);
	}


	private <T extends Rule> List<T> getRule(Map<String, List<T>> map, String systemId, String messageType) {
		List<T> list = new ArrayList<T>();
		setList(list, map.get(systemId));
		setList(list, map.get(messageType));
		setList(list, map.get(systemId + messageType));
		return list;
	}

	private <T extends Rule> void setList(List<T> target, List<T> source) {
		if (source != null) {
			target.addAll(source);
		}
	}

	private <T extends Rule> void setRule(Map<String, List<T>> map, List<T> rules) {
		if (rules != null) {
			Map<String, List<T>> tempMap = new HashMap<>(16);
			for (T rule : rules) {
				if (rule != null) {
					String key = null;
					if (rule instanceof RealRule) {
						RealRule r = (RealRule) rule;
						key = r.getSystemId() + r.getMessageType();
					} else if (rule instanceof TimedRule) {
						TimedRule r = (TimedRule) rule;
						key = r.getSystemId() + r.getMessageType();
					} else if (rule instanceof AlarmRule) {
						key = ((AlarmRule) rule).getMonitorItem().getId();
					} else if (rule instanceof NotifyRule) {
						key = ((NotifyRule) rule).getMonitorItem().getId();
					} else if (rule instanceof PrepareRule) {
						PrepareRule r = (PrepareRule) rule;
						key = r.getSystemId() + r.getMessageType();
					}

					List<T> list = null;
					if (tempMap.containsKey(key)) {
						list = tempMap.get(key);
					} else {
						list = new ArrayList<T>();
						tempMap.put(key, list);
					}

					list.add(rule);
				}
			}

			for (Entry<String, List<T>> entry : tempMap.entrySet()) {
				String key = entry.getKey();
				List<T> value = entry.getValue();
				map.put(key, value);
			}
			tempMap = null;
		}
	}


}
