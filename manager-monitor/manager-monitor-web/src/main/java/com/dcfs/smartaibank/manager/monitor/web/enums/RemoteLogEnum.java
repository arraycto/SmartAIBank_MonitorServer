package com.dcfs.smartaibank.manager.monitor.web.enums;

/**
 * 远程调用日志码表
 *
 * @author liangchenglong
 * @data 2019/07/25 15.01
 * @since 1.0.0
 */
public enum RemoteLogEnum {

    /**
     * 已发送
     */
    SEND("1", "已发送"),
    /**
     * 已接收
     */
    RECEIVE("2", "已接收"),
    /**
     * 正在执行
     */
    EXECUTE("3", "正在执行"),
    /**
     * 执行成功
     */
    SUCCESS("4", "执行成功"),
    /**
     * 执行失败
     */
    FAIL("5", "执行失败");

    private String code;

    private String name;

    /**
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }


    RemoteLogEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }


}
