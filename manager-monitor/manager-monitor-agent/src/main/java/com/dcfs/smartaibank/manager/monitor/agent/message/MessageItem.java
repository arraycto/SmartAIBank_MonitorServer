package com.dcfs.smartaibank.manager.monitor.agent.message;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 消息项
 *
 * @author wanglqb
 */
public final class MessageItem implements Serializable {
    private static final long serialVersionUID = -8576693833366554875L;
    private static final char INVALID_VALUE = '.';
    private final String name;
    private final Object value;
    private final int valueType;

    private MessageItem(String name, Object value, int valueType) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException(
                    "Item name should not be null or empty.");
        } else if (name.indexOf(INVALID_VALUE) >= 0) {
            throw new IllegalArgumentException(
                    "Item name which contains '.' is invalid.");
        }

        this.name = name;
        this.value = value;
        this.valueType = valueType;
    }

    /**
     * 创建消息元素
     *
     * @param name  元素名称
     * @param value 元素值
     * @return 消息元素
     */
    public static MessageItem create(String name, Byte value) {
        return new MessageItem(name, value, ValueType.BYTE);
    }

    /**
     * 创建消息元素
     *
     * @param name  元素名称
     * @param value 元素值
     * @return 消息元素
     */

    public static MessageItem create(String name, Short value) {
        return new MessageItem(name, value, ValueType.SHORT);
    }

    /**
     * 创建消息元素
     *
     * @param name  元素名称
     * @param value 元素值
     * @return 消息元素
     */
    public static MessageItem create(String name, Integer value) {
        return new MessageItem(name, value, ValueType.INTEGER);
    }

    /**
     * 创建消息元素
     *
     * @param name  元素名称
     * @param value 元素值
     * @return 消息元素
     */
    public static MessageItem create(String name, Long value) {
        return new MessageItem(name, value, ValueType.LONG);
    }

    /**
     * 创建消息元素
     *
     * @param name  元素名称
     * @param value 元素值
     * @return 消息元素
     */
    public static MessageItem create(String name, Double value) {
        return new MessageItem(name, value, ValueType.DOUBLE);
    }

    /**
     * 创建消息元素
     *
     * @param name  元素名称
     * @param value 元素值
     * @return 消息元素
     */
    public static MessageItem create(String name, Float value) {
        return new MessageItem(name, value, ValueType.FLOAT);
    }

    /**
     * 创建消息元素
     *
     * @param name  元素名称
     * @param value 元素值
     * @return 消息元素
     */
    public static MessageItem create(String name, Character value) {
        return new MessageItem(name, value, ValueType.CHARACTER);
    }

    /**
     * 创建消息元素
     *
     * @param name  元素名称
     * @param value 元素值
     * @return 消息元素
     */
    public static MessageItem create(String name, Boolean value) {
        return new MessageItem(name, value, ValueType.BOOLEAN);
    }

    /**
     * 创建消息元素
     *
     * @param name  元素名称
     * @param value 元素值
     * @return 消息元素
     */
    public static MessageItem create(String name, String value) {
        return new MessageItem(name, value, ValueType.STRING);
    }

    /**
     * 创建消息元素
     *
     * @param name  元素名称
     * @param value 元素值
     * @return 消息元素
     */
    public static MessageItem create(String name, Date value) {
        return new MessageItem(name, value, ValueType.DATE);
    }

    /**
     * 创建消息元素
     *
     * @param name  元素名称
     * @param value 元素值
     * @return 消息元素
     */
    public static MessageItem create(String name, BigInteger value) {
        return new MessageItem(name, value, ValueType.BIG_INTEGER);
    }

    /**
     * 创建消息元素
     *
     * @param name  元素名称
     * @param value 元素值
     * @return 消息元素
     */
    public static MessageItem create(String name, BigDecimal value) {
        return new MessageItem(name, value, ValueType.BIG_DECIMAL);
    }

    /**
     * 创建消息元素
     *
     * @param name  元素名称
     * @param value 元素值
     * @return 消息元素
     */
    public static MessageItem create(String name, Object value) {
        MessageItem msg = null;
        if (value == null || "".equals(value)) {
            msg = MessageItem.create(name, "");
        } else if (value instanceof Short) {
            msg = MessageItem.create(name, (Short) value);
        } else if (value instanceof Integer) {
            msg = MessageItem.create(name, (Integer) value);
        } else if (value instanceof Byte) {
            msg = MessageItem.create(name, (Byte) value);
        } else if (value instanceof Long) {
            msg = MessageItem.create(name, (Long) value);
        } else if (value instanceof Double) {
            msg = MessageItem.create(name, (Double) value);
        } else if (value instanceof Float) {
            msg = MessageItem.create(name, (Float) value);
        } else if (value instanceof Character) {
            msg = MessageItem.create(name, (Character) value);
        } else if (value instanceof Boolean) {
            msg = MessageItem.create(name, (Boolean) value);
        } else if (value instanceof String) {
            msg = MessageItem.create(name, (String) value);
        } else if (value instanceof Date) {
            msg = MessageItem.create(name, (Date) value);
        } else if (value instanceof BigInteger) {
            msg = MessageItem.create(name, (BigInteger) value);
        } else if (value instanceof BigDecimal) {
            msg = MessageItem.create(name, (BigDecimal) value);
        }
        return msg;
    }

    /**
     * 创建消息元素
     *
     * @param name       元素名称
     * @param eventItems 元素列表
     * @return 消息元素
     */
    public static MessageItem create(String name, MessageItem... eventItems) {
        List<MessageItem> list;
        if (eventItems != null && eventItems.length > 0) {
            list = Arrays.asList(eventItems);
        } else {
            list = Collections.emptyList();
        }

        return new MessageItem(name, list, ValueType.MSG_ITEMS);
    }

    /**
     * 获取元素名称
     *
     * @return 元素名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取元素值
     *
     * @return 元素值
     */
    public Object getValue() {
        return value;
    }

    /**
     * 获取元素类型
     *
     * @return 元素类型
     */
    public int getValueType() {
        return valueType;
    }

    @Override
    public String toString() {
        return "EventItem [name=" + name + ", value=" + value + ", valueType="
                + getValueTypeString(valueType) + "]";
    }

    private static String getValueTypeString(int valueType) {
        switch (valueType) {
            case ValueType.BYTE:
                return Byte.class.getCanonicalName();
            case ValueType.SHORT:
                return Short.class.getCanonicalName();
            case ValueType.INTEGER:
                return Integer.class.getCanonicalName();
            case ValueType.LONG:
                return Long.class.getCanonicalName();
            case ValueType.DOUBLE:
                return Double.class.getCanonicalName();
            case ValueType.FLOAT:
                return Float.class.getCanonicalName();
            case ValueType.CHARACTER:
                return Character.class.getCanonicalName();
            case ValueType.BOOLEAN:
                return Boolean.class.getCanonicalName();
            case ValueType.STRING:
                return String.class.getCanonicalName();
            case ValueType.DATE:
                return Date.class.getCanonicalName();
            case ValueType.BIG_INTEGER:
                return BigInteger.class.getCanonicalName();
            case ValueType.BIG_DECIMAL:
                return BigDecimal.class.getCanonicalName();
            case ValueType.MSG_ITEMS:
                return List.class.getCanonicalName();
            default:
                throw new InternalError();
        }
    }
}
