package com.dcfs.smartaibank.manager.monitor.web.enums;

/**
 * 新客户端名称
 * 远程调用的码表
 *
 * @author liangchenglong
 * @data 2019/07/25 15.01
 * @since 1.0.0
 */
public enum ClientCodeEnum {

    /**
     * 暂停服务
     */
    RC001("0001", "PAUSESERVICE"),
    /**
     * 恢复服务
     */
    RC002("0002", "RECOVERSERVICE"),
    /**
     * 重启服务
     */
    RC003("0003", "RESTART"),
    /**
     * 设备重启
     */
    RC004("0004", "REBOOT"),
    /**
     * 设备关机
     */
    RC005("0005", "SHUTDOWN"),
    /**
     * 模块复位
     */
    RC006("0006", "RESETDEVICE"),
    /**
     * 设备截屏
     */
    RC008("0008", "GETPIC"),
    /**
     * 日志获取
     */
    RC009("0009", "GETLOG"),
    /**
     * 手工对账
     */
    RC015("0015", "ACCOUNTCHECK"),
    /**
     * 默认数值为空
     */
    RC000("", "");

    ClientCodeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * code码，跟RemoteTypeEnum中的mc对应
     */
    private String code;

    /**
     * 新系统远程调用名称
     */
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


    /**
     * 通过code获取name
     *
     * @param name
     * @return
     */
    public static ClientCodeEnum getClientCodeEnumByCode(String name) {
        ClientCodeEnum[] arrays = ClientCodeEnum.values();
        for (int i = 0; i < arrays.length; i++) {
            if (name.equals(arrays[i].name())) {
                return arrays[i];
            }
        }
        return RC000;
    }

}
