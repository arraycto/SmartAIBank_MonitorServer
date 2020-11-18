package com.dcfs.smartaibank.manager.monitor.rule;

import com.dcfs.smartaibank.manager.monitor.core.template.TemplateDefine;
import com.dcfs.smartaibank.manager.monitor.rule.domain.AlarmRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.FilterRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.NotifyRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.PrepareRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.RealRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.TimedRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.DeviceModelCode;
import com.dcfs.smartaibank.manager.monitor.rule.domain.ParamCode;
import com.dcfs.smartaibank.manager.monitor.rule.domain.TranCode;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 规则中心管理接口
 *
 * @author jiazw
 */
public interface RuleCenterManager {
	/**
	 * 设置活驴规则
	 *
	 * @param filterRules 过滤规则集合
	 */
	void setFilterRules(List<FilterRule> filterRules);

	/**
	 * 设置实时规则
	 *
	 * @param rules 实时规则集合
	 */
	void setRealRules(List<RealRule> rules);

	/**
	 * 设置定时规则
	 *
	 * @param rules 定时规则集合
	 */
	void setTimedRules(List<TimedRule> rules);

	/**
	 * 设置预警规则
	 *
	 * @param rules 预警规则集合
	 */
	void setAlarmRules(List<AlarmRule> rules);

	/**
	 * 设置通知规则
	 *
	 * @param rules 通知规则集合
	 */
	void setNotifyRules(List<NotifyRule> rules);

	/**
	 * 设置过滤规则
	 *
	 * @param rules 过滤规则集合
	 */
	void setPrepareRules(List<PrepareRule> rules);

	/**
	 * 设置参数
	 *
	 * @param code 参数
	 */
	void setParamCode(ParamCode code);

	/**
	 * 设置数据字典
	 *
	 * @param code 数据字典
	 */
	void setDictCode(ParamCode code);

	/**
	 * 设置设备类型代码
	 *
	 * @param code 设备类型代码
	 */
	void setDeviceModelCode(DeviceModelCode code);

	/**
	 * 设置设备类型名称
	 *
	 * @param key          设备类型
	 * @param devModelName 设备类型名称
	 */
	void setDeviceModelName(String key, String devModelName);

	/**
	 * 设置机具是否激活
	 *
	 * @param mClassKey 机具类型
	 * @param active    是否激活
	 */
	void setMClassMonitor(String mClassKey, boolean active);

	/**
	 * 设置交易代码
	 *
	 * @param code 交易代码
	 */
	void setTranCode(TranCode code);

	/**
	 * 设置设备描述
	 *
	 * @param name 设备名称
	 * @param desc 设备描述
	 */
	void setDeviceDesc(String name, String desc);

	/**
	 * 设置设备管理员
	 *
	 * @param key   设备编号
	 * @param value 管理员ID
	 */
	void setDeviceManager(String key, String value);

	/**
	 * 设置设备参数
	 *
	 * @param key      设备类型
	 * @param parammap 设备参数
	 */
	void setDeviceParams(String key, Map<String, String> parammap);

	/**
	 * 设置vtm参数
	 *
	 * @param key      事件编号
	 * @param parammap 事件参数
	 */
	void setVtmDeviceParams(String key, Map<String, String> parammap);

	/**
	 * 设置工作时间
	 *
	 * @param workTime 工作时间
	 */
	void setWorkTimeRules(Map<String, List<Date[]>> workTime);

	/**
	 * 设置模板定义集合
	 *
	 * @param templateDefines 模板定义集合
	 */
	void setTemplateDefine(List<TemplateDefine> templateDefines);
}
