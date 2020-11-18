package com.dcfs.smartaibank.manager.monitor.agent.message;


/**
 * 消息发送器
 * @author jiazw
 */
public interface MessageSender {

    /**
     * 创建消息
     * @param system 系统ID
     * @param type 消息类型
     * @return 消息
     */
    Message createMessage(String system, String type);

    /**
     * 发送消息
     *
     * @param message 消息
     */
    void send(Message message);

    /**
     * 判断{@code MessageSender}是否激活
     *
     * @return
     */
    boolean isActive();
}
