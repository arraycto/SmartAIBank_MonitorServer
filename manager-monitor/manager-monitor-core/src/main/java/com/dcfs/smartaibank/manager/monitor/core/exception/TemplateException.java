package com.dcfs.smartaibank.manager.monitor.core.exception;

import com.dcfs.smartaibank.core.exception.BaseException;

/**
 * 格式化异常类
 *
 * @author jiazw
 */
public class TemplateException extends BaseException {
    public TemplateException(String code) {
        super(code);
    }

    public TemplateException(String code, String message) {
        super(code, message);
    }

    public TemplateException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public TemplateException(String code, Throwable cause) {
        super(code, cause);
    }

    public TemplateException(String code,
                             String message,
                             Throwable cause,
                             boolean enableSuppression,
                             boolean writableStackTrace) {
        super(code, message, cause, enableSuppression, writableStackTrace);
    }
}
