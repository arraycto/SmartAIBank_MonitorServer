package com.dcfs.smartaibank.manager.monitor.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json解析工具类
 *
 * @author jiazw
 */
public final class JsonConvertUtil {
	private JsonConvertUtil() {
	}

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/**
	 * 将一个对象转成json字符串
	 *
	 * @param object 带转换对象
	 * @return 转换后字符串
	 */
	public static String toJson(Object object) throws Exception {
		if (object == null) {
			return null;
		} else {
			return OBJECT_MAPPER.writeValueAsString(object);
		}
	}

	/**
	 * 将一个字符串转成一个Map
	 *
	 * @param json 待转换字符串
	 * @return 转换后的Map
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String json, Class<T> clazz) throws Exception {
		return OBJECT_MAPPER.readValue(json, clazz);
	}
}
