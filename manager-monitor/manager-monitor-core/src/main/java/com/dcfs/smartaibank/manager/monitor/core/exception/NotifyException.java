package com.dcfs.smartaibank.manager.monitor.core.exception;

import com.dcfs.smartaibank.core.exception.BaseException;

/**
 * 通知异常类
 *
 * @author jiazw
 */
public class NotifyException extends BaseException {
    public NotifyException(String code) {
        super(code);
    }

    public NotifyException(String code, String message) {
        super(code, message);
    }

    public NotifyException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public NotifyException(String code, Throwable cause) {
        super(code, cause);
    }

    public NotifyException(String code,
                           String message,
                           Throwable cause,
                           boolean enableSuppression,
                           boolean writableStackTrace) {
        super(code, message, cause, enableSuppression, writableStackTrace);
    }
}
