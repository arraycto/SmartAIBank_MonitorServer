package com.dcfs.smartaibank.manager.monitor.agent.sink;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import com.dcfs.smartaibank.manager.monitor.agent.message.ValueType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.flume.Channel;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.sink.AbstractSink;

/**
 * sink处理抽象类，采用模板模式，预留处理给子类实现
 *
 * @author jiazw
 */
@Slf4j
public abstract class AbstractProcessSink extends AbstractSink {
	private static final String NAME = "name";
	private static final String VALUE = "value";
	private static final String VALUE_TYPE = "valueType";
	private static final Charset UTF_8 = Charset.forName("UTF-8");
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final String SOURCE_HTTP = "HTTP";
	private static final String SOURCE_AVRO = "AVRO";

	@Override
	public Status process() throws EventDeliveryException {
		Status status = Status.READY;
		Transaction transaction = null;
		try {
			Channel channel = getChannel();
			transaction = channel.getTransaction();
			transaction.begin();
			Event event = channel.take();
			if (event == null) {
				status = Status.BACKOFF;
			} else {
				ArrayNode bodyNode;
				try {
					String source = event.getHeaders().get("SOURCE");
					byte[] body = needUnGzip(source) ? unGzip(event.getBody()) : event.getBody();
					bodyNode = (ArrayNode) OBJECT_MAPPER.readTree(new String(body, UTF_8));
				} catch (Exception e) {
					throw new IllegalStateException("Unable to read event body.", e);
				}

				Map<String, Object> messageBody = new HashMap<>(16);
				parseBody(bodyNode, messageBody);

				process(event.getHeaders(), messageBody);
			}

			transaction.commit();
		} catch (Throwable t) {
			log.error("Error occured while consuming flume event with [" + getClass().getSimpleName() + "].");
			if (transaction != null) {
				try {
					transaction.commit();
				} catch (Exception ignore) {
				}
			}

			throw new EventDeliveryException(t);
		} finally {
			if (transaction != null) {
				try {
					transaction.close();
				} catch (Exception ignore) {
				}
			}
		}

		return status;
	}

	/**
	 * 子类实现的处理方法
	 *
	 * @param header 报文头
	 * @param body   报文体
	 */
	protected abstract void process(Map<String, String> header, Map<String, Object> body);

	private void parseBody(ArrayNode arrayNode, Map<String, Object> map) {
		for (JsonNode jsonNode : arrayNode) {
			JsonNode nameNode = jsonNode.get(NAME);
			JsonNode valueNode = jsonNode.get(VALUE);
			JsonNode valueTypeNode = jsonNode.get(VALUE_TYPE);

			Object value = getValue(valueNode, valueTypeNode.asInt());
			if (value instanceof ArrayNode) {
				Map<String, Object> nestedMap = new HashMap<>(32);
				map.put(nameNode.asText(), nestedMap);
				parseBody((ArrayNode) value, nestedMap);
			} else {
				map.put(nameNode.asText(), value);
			}
		}
	}

	private Object getValue(JsonNode valueNode, int valueType) {
		switch (valueType) {
			case ValueType.BYTE:
				return (valueNode.isNull() || valueNode instanceof NullNode) ? null : (byte) valueNode.asInt();
			case ValueType.SHORT:
				return (valueNode.isNull() || valueNode instanceof NullNode) ? null : (short) valueNode.asInt();
			case ValueType.INTEGER:
				return (valueNode.isNull() || valueNode instanceof NullNode) ? null : valueNode.asInt();
			case ValueType.LONG:
				return (valueNode.isNull() || valueNode instanceof NullNode) ? null : valueNode.asLong();
			case ValueType.DOUBLE:
				return (valueNode.isNull() || valueNode instanceof NullNode) ? null : valueNode.asDouble();
			case ValueType.FLOAT:
				return (valueNode.isNull() || valueNode instanceof NullNode) ? null : (float) valueNode.asDouble();
			case ValueType.CHARACTER:
				return (valueNode.isNull() || valueNode instanceof NullNode) ? null : valueNode.asText().charAt(0);
			case ValueType.BOOLEAN:
				return (valueNode.isNull() || valueNode instanceof NullNode) ? null : valueNode.asBoolean();
			case ValueType.STRING:
				return (valueNode.isNull() || valueNode instanceof NullNode) ? null : valueNode.asText();
			case ValueType.DATE:
				return (valueNode.isNull() || valueNode instanceof NullNode) ? null : new Date(valueNode.asLong());
			case ValueType.BIG_INTEGER:
				return (valueNode.isNull() || valueNode instanceof NullNode)
					? null
					: new BigInteger(valueNode.asText());
			case ValueType.BIG_DECIMAL:
				return (valueNode.isNull() || valueNode instanceof NullNode)
					? null
					: new BigDecimal(valueNode.asText());
			case ValueType.MSG_ITEMS:
				return (valueNode.isNull() || valueNode instanceof NullNode) ? null : valueNode;
			default:
				throw new IllegalStateException("Unknown value type.");
		}
	}

	private boolean needUnGzip(String source) {
		return source == null || !SOURCE_HTTP.equals(source);
	}

	private byte[] unGzip(byte[] body) {
		if (null != body) {
			ByteArrayInputStream input = new ByteArrayInputStream(body);
			GZIPInputStream stream = null;
			try {
				stream = new GZIPInputStream(input);
				byte[] result = IOUtils.toByteArray(stream);
				return result;
			} catch (IOException e) {
				log.error("采集数据信息体，二进制流压缩异常:{},{}", e.getMessage(), e.getClass().getName());
			} finally {
				try {
					if (input != null) {
						input.close();
					}
					if (null != stream) {
						stream.close();
					}
				} catch (IOException e) {
					log.error("采集数据信息体，关闭io流异常:{},{}", e.getMessage(), e.getClass().getName());
				}

			}

		}

		return null;
	}

}
