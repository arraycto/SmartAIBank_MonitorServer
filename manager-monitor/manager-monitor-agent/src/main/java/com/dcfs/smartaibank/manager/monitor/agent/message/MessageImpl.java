package com.dcfs.smartaibank.manager.monitor.agent.message;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 监控数据
 *
 * @author jiazw
 */
public class MessageImpl implements Message {
    private Map<String, String> header = new ConcurrentHashMap<>();
    private Map<String, MessageItem> body = new ConcurrentHashMap<>();

    public MessageImpl(String system, String type) {
        this.header.put(Constants.SYSTEM, system);
        this.header.put(Constants.TYPE, type);
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.header;
    }

    @Override
    public Collection<MessageItem> getBody() {
        return body.values();
    }

    @Override
    public void destroy() {
        header.clear();
        body.clear();
    }

    @Override
    public void addBodyItem(MessageItem item) {
        body.put(item.getName(), item);

    }

    @Override
    public void addHeadItem(String name, String value) {
        header.put(name, value);
    }
}
