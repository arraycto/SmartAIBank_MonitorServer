/**
 * @Title: ChannelException.java
 * @Package com.dcfs.managerplatform.monitor.api.exception
 * @Description: 分析器异常类
 * @author jiazw
 * @date 2016年5月19日 下午1:57:20
 * @version V1.0
 * 变更记录：
 *   jiazw 2016年5月19日 下午1:57:20 新建
 */
package com.dcfs.smartaibank.manager.monitor.core.exception;

import com.dcfs.smartaibank.core.exception.BaseException;

/**
 * 通道异常类
 *
 * @author jiazw
 */
@SuppressWarnings("serial")
public class ChannelException extends BaseException {
    public ChannelException(String code) {
        super(code);
    }

    public ChannelException(String code, String message) {
        super(code, message);
    }

    public ChannelException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public ChannelException(String code, Throwable cause) {
        super(code, cause);
    }

    public ChannelException(String code,
                            String message,
                            Throwable cause,
                            boolean enableSuppression,
                            boolean writableStackTrace) {
        super(code, message, cause, enableSuppression, writableStackTrace);
    }
}
