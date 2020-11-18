package com.dcfs.smartaibank.manager.monitor.core.context;

import java.util.Collection;
import java.util.Map;

/**
 * 上下文环境辅助类
 *
 * @author jiazw
 */
public final class ContextHelper implements Constants {

	private ContextHelper() {
	}

	/**
	 * 获取系统标识
	 *
	 * @param context 上下文环境
	 * @return String 系统标识
	 */
	public static String getSystem(MonitorContext context) {
		return context.getHeader(Constants.SYSTEM);
	}

	/**
	 * 获取消息类型
	 *
	 * @param context 上下文
	 * @return String 消息类型
	 */
	public static String getType(MonitorContext context) {
		return context.getHeader(Constants.TYPE);
	}

	/**
	 * 判断数据中的系统标识是否为给定的系统标识
	 *
	 * @param context 上下文
	 * @param system  系统标识
	 * @return true:上下文环境中的系统标识与给定的系统标识一致
	 */
	public static boolean isSameSystem(MonitorContext context, String system) {
		return context.getHeader(Constants.SYSTEM).equals(system);
	}

	/**
	 * 判断数据中的类型是否为给定的类型
	 *
	 * @param context 上下文
	 * @param type    消息类型
	 * @return true:上下文环境中的类型与给定的类型一致
	 */
	public static boolean isSameType(MonitorContext context, String type) {
		return context.getHeader(Constants.TYPE).equals(type);
	}

	/**
	 * 判断数据中系统标识和类型是否为给定的系统标识和类型
	 *
	 * @param context 上下文
	 * @param system  系统标识
	 * @param type    消息类型
	 * @return 上下文中的系统标识和消息类型与给定的一致
	 */
	public static boolean isSameSystemAndType(MonitorContext context, String system, String type) {
		return isSameSystem(context, system) && isSameType(context, type);
	}

	/**
	 * 判断操作是否为添加
	 *
	 * @param context 上下文
	 * @return true:是
	 */
	public static boolean isAdd(MonitorContext context) {
		return Constants.OPERATOR_ADD.equals(context.getHeader(Constants.OPERATOR));
	}

	/**
	 * 判断操作是否为更新
	 *
	 * @param context 上下文
	 * @return true:是
	 */
	public static boolean isUpdate(MonitorContext context) {
		return Constants.OPERATOR_UPDATE.equals(context.getHeader(Constants.OPERATOR));
	}

	/**
	 * 判断操作是否为添加或更新
	 *
	 * @param context 上下文
	 * @return true: 是
	 */
	public static boolean isAddOrUpdate(MonitorContext context) {
		return Constants.OPERATOR_ADDORUPDATE.equals(context.getHeader(Constants.OPERATOR));
	}

	/**
	 * 判断上下文中是否存在指定的键名并且与给定的值相等
	 *
	 * @param context 上下文
	 * @param key     键名
	 * @param value   键值
	 * @return true:存在且相等
	 */
	public static boolean isHaveAndEqualFromBody(MonitorContext context, String key, String value) {
		boolean result = false;
		if (context.getBody().containsKey(key)) {
			Object obj = context.getBody(key);
			if (obj instanceof String) {
				if (obj == value || value.equals(obj)) {
					result = true;
				}
			}
		}

		return result;
	}

	/**
	 * 使用上下文BODY里的数据填充目标
	 *
	 * @param context 上下文
	 * @param target  目标Map
	 * @param keys    需要填充的键名
	 */
	public static void fillFromBody(MonitorContext context, Map<String, Object> target, String... keys) {
		Map<String, Object> obj = context.getBody();
		fillFromMap(obj, target, keys);
	}

	/**
	 * 使用上下文BODY里的数据填充目标
	 *
	 * @param context 上下文
	 * @param target  目标Map
	 * @param keys    需要填充的键名
	 */
	public static void fillFromBody(MonitorContext context, Map<String, Object> target, Collection<String> keys) {
		Map<String, Object> obj = context.getBody();
		fillFromMap(obj, target, keys);
	}

	/**
	 * 使用上下文BODY里的数据填充目标
	 *
	 * @param context 上下文
	 * @param target  目标Map
	 */
	public static void fillFromBody(MonitorContext context, Map<String, Object> target) {
		Map<String, Object> body = context.getBody();
		fillFromMap(body, target, body.keySet());
	}

	/**
	 * 使用源Map数据填充目标Map
	 *
	 * @param source 源Map
	 * @param target  目标Map
	 * @param keys    需要填充的键名
	 */
	public static void fillFromMap(Map<String, Object> source, Map<String, Object> target, Collection<String> keys) {
		if (source != null && target != null) {
			for (String key : keys) {
				target.put(key, source.get(key));
			}
		}
	}

	/**
	 * 使用源Map数据填充目标Map
	 *
	 * @param source 源Map
	 * @param target  目标Map
	 * @param keys    需要填充的键名
	 */
	public static void fillFromMap(Map<String, Object> source, Map<String, Object> target, String... keys) {
		if (source != null && target != null) {
			for (String key : keys) {
				target.put(key, source.get(key));
			}
		}
	}
}
