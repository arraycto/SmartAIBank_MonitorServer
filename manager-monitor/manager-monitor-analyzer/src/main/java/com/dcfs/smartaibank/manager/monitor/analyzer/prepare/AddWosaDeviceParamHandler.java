package com.dcfs.smartaibank.manager.monitor.analyzer.prepare;

import com.dcfs.smartaibank.handler.exception.HandlerException;
import com.dcfs.smartaibank.handler.Status;
import com.dcfs.smartaibank.manager.monitor.core.context.ContextHelper;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.AbstractMonitorHandler;
import com.dcfs.smartaibank.manager.monitor.analyzer.spi.PrepareHandler;
import com.dcfs.smartaibank.manager.monitor.core.context.Constants;
import com.dcfs.smartaibank.manager.monitor.core.context.MonitorContext;
import com.dcfs.smartaibank.manager.monitor.core.util.JsonConvertUtil;
import com.dcfs.smartaibank.manager.monitor.rule.RuleCenterAccess;
import com.dcfs.smartaibank.manager.monitor.rule.domain.DeviceModelCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 当TYPE=DEVICE时添加外设名称和外设状态,并且外设符合WOSA标准时
 *
 * @author jiazw
 */
@Slf4j
public class AddWosaDeviceParamHandler extends AbstractMonitorHandler implements PrepareHandler, Constants {

	private RuleCenterAccess ruleCenter;
	private static final String SPLIT = ".";

	@Override
	@SuppressWarnings("unchecked")
	protected Status doHandle(MonitorContext context) throws HandlerException {
		//是否为外设监控数据
		boolean cond = ContextHelper.isSameType(context, TYPE_DEVICE);

		//判断被监控外设是否符合WOSA标准（WOSA标准和非标准在数据格式上不同，需要区别对待）
		boolean isWosa = ContextHelper.isHaveAndEqualFromBody(context, "STANDARD", DEVICE_STANDARD_WOSA);

		if (cond && isWosa) {
			//因为wosa标准的外设状态码都一直，所有此处使用DEVCLASSKEY作为DEVMODELKEY使用
			String devModelKey = context.getBody(Constants.DEVCLASSKEY);

			//获取详情信息
			String detailStr = context.getBody(Constants.DETAIL);

			try {
				//转为map格式
				Map<String, Object> detail = JsonConvertUtil.fromJson(detailStr, Map.class);

				if (detail != null) {
					//转换为界面界面展示的描述信息
					Map<String, Object> detailDesc = convertDetail(devModelKey, devModelKey, detail);
					String detailDescStr = JsonConvertUtil.toJson(detailDesc);
					if (log.isDebugEnabled()) {
						log.debug("外设详情描述信息：{}", detailDescStr);
					}
					context.put(Constants.DETAIL_DESC, detailDescStr);
				}

				//modify by jiazw 2016-08-03
				//计算外设状态等信息
				setStatusInfo(context, detail);
			} catch (Exception e) {
				throw new HandlerException("handler.error", e);
			}
		}
		return Status.CONTINUE;
	}

	private void setStatusInfo(MonitorContext context, Map<String, Object> detail) {
		//因为wosa标准的外设状态码都一直，所有此处使用DEVCLASSKEY作为DEVMODELKEY使用
		String devModelKey = context.getBody(Constants.DEVCLASSKEY);

		//外设代码集合，用于存放在库中查找到的数据，该list会存放在context中，后续预警使用
		Map<String, DeviceModelCode> list = new HashMap<>(16);

		int status = 1;
		String statusDesc = "";
		int mStatus = 1;
		String statusCode = "";

		if (detail != null) {
			for (Entry<String, Object> entry : detail.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				String code = "";

				//钞箱数组
				if (CASH_UNIT_INFO.equals(key)) {
					//分别计算存取款钞箱的总金额，并存入上下文中
					setCashUnitInfo(context, value);

				} else {
					if (value instanceof String || value instanceof Integer) {
						code = value.toString();
					}

					//针对WOSA标准格式外设状态按照外设 模块+状态作为状态码获获取DeviceModelCode
					String statusCCode = key + SPLIT + code;
					DeviceModelCode codeObj = ruleCenter.getDeviceModelCode(devModelKey, statusCCode);

					if (codeObj != null && codeObj.isActive()) {
						list.put(key, codeObj);

						//设置当前外设状态、对应机具状态以及状态描述
						//判断规则如下：
						//1. 外设状态：该外设下所有子模块状态中取最大值
						//2. 外设状态描述：该外设所有子模块状态中状态最高的所有描述合并，中间用；号隔开
						//3. 对应机具状态：取外设状态对应的机具状态
						if (status < codeObj.getStatus().getCode()) {
							status = codeObj.getStatus().getCode();
							statusDesc = codeObj.getStatusDesc();
							mStatus = codeObj.getMStatus().getCode();
						} else if (status == codeObj.getStatus().getCode()) {
							StringBuilder sb = new StringBuilder();
							sb.append(statusDesc)
								.append(";")
								.append(codeObj.getStatusDesc());
							statusDesc = sb.toString();
						}
					}
				}
			}
		}

		if ("".equals(statusDesc)) {
			statusDesc = "正常";
		}

		//向上下文对象中添加设备状态列表数据，用于外设状态预警
		context.put(DEVICE_STATUS_LIST, list);

		//外设状态
		context.put(Constants.STATUS, status);
		//外设状态描述
		context.put(Constants.STATUS_DESC, statusDesc);
		//外设状态码
		context.put(Constants.STATUS_CODE, statusCode);
		//对应机具状态
		context.put(Constants.M_STATUS, mStatus);

		if (log.isDebugEnabled()) {
			log.debug("[{}]预处理器新增字段：DEVICE_STATUS={},DEVICE_STATUS_DESC={},M_STATUS={}",
				context.get("UUID"), status, statusDesc, mStatus);
		}
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void setCashUnitInfo(MonitorContext context, Object obj) {
		if (obj instanceof List) {
			List list = (List) obj;
			int cashCdm = 0;
			int cashCim = 0;

			//add by jiazw 2016-08-03
			//设置钞箱信息
			context.put("CASH_UNIT_INFO", obj);

			for (Object object : list) {
				if (object instanceof Map) {
					Map<String, String> map = (Map<String, String>) object;
					//钞箱类型： 0-拒钞  1-回收   2-只取   3-只存  4-循环（可存可取）  5-其他
					String type = map.get("UNIT_TYPE");
					//钞量
					String countStr = map.get("UNIT_COUNT");
					//面额
					String nameStr = map.get("UNIT_NAME");
					int count = 0;
					int name = 0;
					try {
						count = Integer.parseInt(countStr);
						name = Integer.parseInt(nameStr);
					} catch (Exception e) {
					}

					int currentCount = count * name;
					//取款钞箱
					if (type != null && "2".equals(type)) {
						cashCdm += currentCount;
					} else if (type != null && "3".equals(type)) {
						cashCim += currentCount;
					} else if (type != null && "4".equals(type)) {
						cashCdm += currentCount;
						cashCim += currentCount;
					}
				}
			}

			//把当前钞箱内存款箱总金额和取款箱总金额存入上下文中
			context.put("CASH_CDM_TOTAL", cashCdm);
			context.put("CASH_CIM_TOTAL", cashCim);
		}
	}

	private void setDefaultData(MonitorContext context) {
		//默认外设状态为正常
		context.put(Constants.STATUS, 1);
		//默认外设状态描述为空
		context.put(Constants.STATUS_DESC, "正常");
		//默认机具状态为正常
		context.put(Constants.M_STATUS, 1);
		//默认机具状态码
		context.put(Constants.STATUS_CODE, "");
	}

	private Map<String, Object> convertDetail(String devClassKey, String devModelKey, Map<String, Object> detail) {
		Map<String, Object> map = new HashMap<>(16);
		String mkey = devClassKey + "." + devModelKey;
		getDeviceDetailDesc(mkey, detail, map);
		return map;
	}

	@SuppressWarnings("unchecked")
	private void getDeviceDetailDesc(String mkey,
									 Map<String, Object> source, Map<String, Object> target) {
		for (String key : source.keySet()) {
			Object value = source.get(key);
			String skey = getKeyDesc(mkey, key);
			String keyDesc = ruleCenter.getDeviceDesc(skey);
			if (keyDesc == null || "".equals(keyDesc)) {
				continue;
			}
			if (value instanceof String || value instanceof Integer) {
				String valueDesc = ruleCenter.getDeviceDesc(getKeyDesc(mkey, key, String.valueOf(value)));
				target.put(keyDesc, valueDesc == null ? String.valueOf(value) : valueDesc);
			} else if (value instanceof List) {
				List<Object> sList = (List<Object>) value;
				List<Object> tList = new ArrayList<>();
				getDeviceDetailDesc(skey, sList, tList);
				target.put(keyDesc, tList);
			} else if (value instanceof Map) {
				Map<String, Object> sMap = (Map<String, Object>) value;
				Map<String, Object> tMap = new HashMap<>(16);
				getDeviceDetailDesc(skey, sMap, tMap);
				target.put(keyDesc, tMap);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void getDeviceDetailDesc(String mkey,
									 List<Object> source, List<Object> target) {
		for (Object obj : source) {
			if (obj instanceof String || obj instanceof Integer) {
				String skey = getKeyDesc(mkey, obj.toString());
				target.add(skey == null ? obj.toString() : skey);
			} else if (obj instanceof Map) {
				Map<String, Object> sMap = (Map<String, Object>) obj;
				Map<String, Object> tMap = new HashMap<>(16);
				getDeviceDetailDesc(mkey, sMap, tMap);
				target.add(tMap);
			}
		}
	}

	private String getKeyDesc(String... args) {
		StringBuilder sb = new StringBuilder();
		for (String string : args) {
			if (string != null && !"".equals(string)) {
				sb.append(string).append(".");
			}
		}
		if (sb.length() == 0) {
			return "";
		} else {
			return sb.substring(0, sb.length() - 1);
		}
	}

	public void setRuleCenter(RuleCenterAccess ruleCenter) {
		this.ruleCenter = ruleCenter;
	}

	@Override
	protected void doStart() {
		Assert.notNull(this.ruleCenter, "ruleCenter must be not null");
	}
}
