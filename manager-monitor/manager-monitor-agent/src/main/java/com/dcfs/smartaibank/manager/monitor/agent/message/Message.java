package com.dcfs.smartaibank.manager.monitor.agent.message;

import java.util.Collection;
import java.util.Map;

/**
 * 消息
 *
 * @author wanglqb
 */
public interface Message {

    /**
     * 获取消息头
     *
     * @return 消息头
     */
    Map<String, String> getHeaders();

    /**
     * 获取消息体
     *
     * @return 消息体
     */
    Collection<MessageItem> getBody();

    /**
     * 销毁对象
     */
    void destroy();

    /**
     * 添加内容
     *
     * @param item 消息元素
     */
    void addBodyItem(MessageItem item);

    /**
     * 添加头信息
     *
     * @param name  消息头元素名称
     * @param value 消息头元素值
     */
    void addHeadItem(String name, String value);
}
