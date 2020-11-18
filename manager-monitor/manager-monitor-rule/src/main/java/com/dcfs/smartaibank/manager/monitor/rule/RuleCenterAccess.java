package com.dcfs.smartaibank.manager.monitor.rule;

import com.dcfs.smartaibank.manager.monitor.core.template.TemplateDefine;
import com.dcfs.smartaibank.manager.monitor.rule.domain.AlarmRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.FilterRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.NotifyRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.PrepareRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.RealRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.TimedRule;
import com.dcfs.smartaibank.manager.monitor.rule.domain.DeviceModelCode;
import com.dcfs.smartaibank.manager.monitor.rule.domain.TranCode;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 规则中心访问接口
 *
 * @author jiazw
 */
public interface RuleCenterAccess {
	/**
	 * 获取所有过滤规则
	 *
	 * @return 过滤规则集合
	 */
	default List<FilterRule> getFilterRules() {
		return null;
	}

	/**
	 * 获取实时规则集合
	 *
	 * @param systemId     系统ID
	 * @param systemModule 系统模块
	 * @return 触发的实时规则集合
	 */
	List<RealRule> getRealRules(String systemId, String systemModule);

	/**
	 * 获取所有定时规则
	 *
	 * @return 定时规则集合
	 */
	List<TimedRule> getTimedRules();

	/**
	 * 获取所有预警规则
	 *
	 * @param monitorItemId 监控项ID
	 * @return 预警规则集合
	 */
	List<AlarmRule> getAlarmRules(String monitorItemId);

	/**
	 * 获取通知规则
	 *
	 * @param monitorItemId 监控项ID
	 * @return 通知规则集合
	 */
	List<NotifyRule> getNotifyRules(String monitorItemId);

	/**
	 * 获取预处理规则
	 *
	 * @param systemId     系统ID
	 * @param systemModule 系统模块
	 * @return 预处理规则集合
	 */
	List<PrepareRule> getPrepareRules(String systemId, String systemModule);

	/**
	 * 获取参数代码
	 *
	 * @return 参数代码集合
	 */
	Map<String, String> getParamCodes();

	/**
	 * 获取数据字典
	 *
	 * @return 数据字典集合
	 */
	Map<String, Map<String, String>> getDictCodes();

	/**
	 * 获取参数键值
	 *
	 * @param key 参数键名
	 * @return 参数键值
	 */
	String getParamCode(String key);

	/**
	 * 设置数据字典值描述
	 *
	 * @param codeNo    数据字典代码
	 * @param codeValue 数据字典值
	 * @return 数据字典值描述
	 */
	String getDictCode(String codeNo, String codeValue);

	/**
	 * 获取设备类型代码信息
	 *
	 * @param devModelKey 设备类型
	 * @param statusCode  状态码
	 * @return 设备类型代码信息
	 */
	DeviceModelCode getDeviceModelCode(String devModelKey, String statusCode);

	/**
	 * 获取设备类型名称
	 *
	 * @param mClassKey   机具类型
	 * @param devModelKey 设备类型
	 * @return 设备类型名称
	 */
	String getDeviceModelName(String mClassKey, String devModelKey);

	/**
	 * 判断机具是否激活
	 *
	 * @param mClassKey 机具类型
	 * @return true:激活
	 */
	boolean isActiveForMClass(String mClassKey);

	/**
	 * 获取交易码信息
	 *
	 * @param tranCode    交易码
	 * @param tranRetCode 交易返回码
	 * @return 交易码信息
	 */
	TranCode getTranCode(String tranCode, String tranRetCode);

	/**
	 * 获取设备描述
	 *
	 * @param key 设备ID
	 * @return 设备描述
	 */
	String getDeviceDesc(String key);

	/**
	 * 获取设备管理员
	 *
	 * @param key 设备ID
	 * @return 设备管理员ID
	 */
	String getDeviceManager(String key);

	/**
	 * 获取设备参数
	 *
	 * @param key 设备ID
	 * @return 设备参数
	 */
	Map<String, String> getDeviceParams(String key);

	/**
	 * 获取vtm设备参数
	 *
	 * @param key 设备ID
	 * @return 设备参数
	 */
	Map<String, String> getVtmDeviceParams(String key);

	/**
	 * 获取机具工作时间
	 *
	 * @param devKey 设备ID
	 * @return 机具工作时间
	 */
	List<Date[]> getWorkTimeRules(String devKey);

	/**
	 * 获取模板定义
	 * @param templateId 模板ID
	 * @return 模板定义
	 */
	TemplateDefine getTemplateDefine(String templateId);
}
