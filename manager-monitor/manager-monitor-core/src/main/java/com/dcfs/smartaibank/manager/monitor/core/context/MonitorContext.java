package com.dcfs.smartaibank.manager.monitor.core.context;

import com.dcfs.smartaibank.handler.context.Context;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 监控预警上下文环境
 *
 * @author jiazw
 */
public final class MonitorContext implements Context, Serializable {

	/**
	 * 监控数据头信息
	 */
	private Map<String, String> header;

	/**
	 * 监控数据体信息
	 */
	private Map<String, Object> body;

	/**
	 * 上下文内容
	 */
	private final Map<String, Object> content = new HashMap<>();

	public MonitorContext(Map<String, String> header, Map<String, Object> body) {
		this.header = header;
		this.body = body;
	}

	/**
	 * 返回监控数据头部信息，该信息不允许外部调用者修改
	 *
	 * @return 监控数据头信息
	 */
	public Map<String, String> getHeader() {
		return Collections.unmodifiableMap(header);
	}

	/**
	 * 返回监控数据体信息，该信息不允许外部调用者修改
	 *
	 * @return 监控数据体信息
	 */
	public Map<String, Object> getBody() {
		return Collections.unmodifiableMap(body);
	}

	/**
	 * 返回赏析文数据信息，该信息不允许外部调用者获取对象后修改
	 *
	 * @return 上下文数据信息
	 */
	public Map<String, Object> getContent() {
		return Collections.unmodifiableMap(content);
	}

	/**
	 * @param key   键
	 * @param value 值
	 * @return Object 上下文中存放的KEY对应的原有值
	 */
	@Override
	public Object put(String key, Object value) {
		return content.put(key, value);
	}

	/**
	 * @param key 键
	 * @return T  KEY对应的值
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key) {
		return (T) content.get(key);
	}

	/**
	 * @param key 键
	 * @return Object 上下文中存放的KEY对应的值
	 */
	@Override
	public Object remove(String key) {
		return content.remove(key);
	}

	/**
	 * @param key 消息头KEY
	 * @return String 消息头指定KEY的值
	 */
	public String getHeader(String key) {
		if (header != null) {
			return header.get(key);
		}
		return null;
	}

	/**
	 * @param key 消息体KEY
	 * @return T 消息体指定KEY的值
	 */
	@SuppressWarnings("unchecked")
	public <T> T getBody(String key) {
		if (body != null) {
			return (T) body.get(key);
		}

		return null;
	}

	@Override
	public void clear() {
		content.clear();
	}

	public String getUUID() {
		return this.get("UUID");
	}

	public String getType() {
		return this.getHeader(Constants.TYPE);
	}

	public String getMechineClassKey() {
		return this.getBody(Constants.MCLASSKEY);
	}

	@Override
	public String toString() {
		return String.format("[%s]上下文内容：\n\tHeader:%s\n\tBody:%s\n\tContent:%s",
			content.get("UUID"),
			header.toString(),
			body.toString(),
			content.toString());
	}
}
