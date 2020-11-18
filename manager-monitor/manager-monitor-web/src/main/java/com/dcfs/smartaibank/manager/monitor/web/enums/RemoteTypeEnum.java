package com.dcfs.smartaibank.manager.monitor.web.enums;


/**
 * 设备详情列表排序规则枚举类
 * 远程调用的码表
 *
 * @author liangchenglong
 * @data 2019/07/25 15.01
 * @since 1.0.0
 */

public enum RemoteTypeEnum {

    /**
     * 暂停服务
     */
    RC001("1500", "0001", "REMOTE_CONTROL_SERVICE"),
    /**
     * 恢复服务
     */
    RC002("1500", "0002", "REMOTE_CONTROL_SERVICE"),
    /**
     * 重启服务
     */
    RC003("1500", "0003", "REMOTE_CONTROL_SERVICE"),
    /**
     * 设备重启
     */
    RC004("1500", "0004", "REMOTE_CONTROL_SERVICE"),
    /**
     * 设备关机
     */
    RC005("1500", "0005", "REMOTE_CONTROL_SERVICE"),
    /**
     * 模块复位
     */
    RC006("1500", "0006", "REMOTE_CONTROL_SERVICE"),
    /**
     * 驱动更新
     */
    RC007("1500", "0007", "REMOTE_CONTROL_SERVICE"),
    /**
     * 设备截屏
     */
    RC008("1500", "0008", "REMOTE_CONTROL_SERVICE"),
    /**
     * 日志获取
     */
    RC009("1500", "0009", "REMOTE_CONTROL_SERVICE"),
    /**
     * 查看版本信息
     */
    RC010("1500", "0010", "REMOTE_CONTROL_SERVICE"),
    /**
     * 远程部署
     */
    RC011("1500", "0011", "REMOTE_CONTROL_SERVICE"),
    /**
     * 故障报修
     */
    RC012("1500", "0012", "REMOTE_CONTROL_SERVICE"),
    /**
     * 获取预警信息
     */
    RC014("1500", "0014", "REMOTE_CONTROL_SERVICE"),

    /**
     * 手工对账
     */
    RC015("1500", "0015", "REMOTE_CONTROL_SERVICE"),

    /**
     * 历史对账记录查询
     */
    RC016("1500", "0016", "REMOTE_CONTROL_SERVICE"),

    /**
     * 默认数值为空
     */
    RC000("", "", "");


    private String mt;

    private String mc;

    private String sc;

    public String getMt() {
        return mt;
    }

    public void setMt(String mt) {
        this.mt = mt;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }


    RemoteTypeEnum(String mt, String mc, String sc) {
        this.mt = mt;
        this.mc = mc;
        this.sc = sc;
    }


    /**
     * 根据码表名称，获取远程调用码表枚举
     *
     * @param name 码表名称,由前端传过来，比如：RC001,RC002等
     * @return
     */
    public static RemoteTypeEnum getRemoteTypeByName(String name) {
        RemoteTypeEnum[] arrays = RemoteTypeEnum.values();
        for (int i = 0; i < arrays.length; i++) {
            if (name.equals(arrays[i].name())) {
                return arrays[i];
            }
        }
        return RC000;
    }
}
