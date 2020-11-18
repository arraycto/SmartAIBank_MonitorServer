package com.dcfs.smartaibank.manager.monitor.web.util.remote;

import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 客户端请求头
 *
 * @author liangchenglong
 * @date 2019/7/29 10:46
 * @since 1.0.0
 */

@NoArgsConstructor
@ToString
public class ClientHeader {
    /**
     * 从什么系统法
     */
    private String from = "monitor";
    /**
     * 什么系统接收
     */
    private String to = "agent";
    /**
     * 名称
     */
    private String name = "";

    /**
     * 日期
     */
    private String date = "";

    /**
     * 序列
     */
    private String seq = "";

    /**
     * isreply
     */
    private Boolean isreply = true;

    private ClientRet ret;

    public String getFrom() {
        return from;
    }

    /**
     * @param from
     * @return
     */
    public ClientHeader setFrom(String from) {
        this.from = from;
        return this;
    }

    /**
     * @return
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to
     * @return
     */
    public ClientHeader setTo(String to) {
        this.to = to;
        return this;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     * @return
     */
    public ClientHeader setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date
     * @return
     */
    public ClientHeader setDate(String date) {
        this.date = date;
        return this;
    }

    /**
     * @return
     */
    public String getSeq() {
        return seq;
    }

    /**
     * @param seq
     * @return
     */
    public ClientHeader setSeq(String seq) {
        this.seq = seq;
        return this;
    }

    /**
     * @return
     */
    public Boolean getIsreply() {
        return isreply;
    }

    /**
     * @param isreply
     * @return
     */
    public ClientHeader setIsreply(Boolean isreply) {
        this.isreply = isreply;
        return this;
    }


    public ClientRet getRet() {
        return ret;
    }

    /**
     * @param ret
     * @return
     */
    public ClientHeader setRet(ClientRet ret) {
        this.ret = ret;
        return this;
    }

}
