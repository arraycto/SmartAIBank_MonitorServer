/**
 * @Title: ExecutorException.java
 * @Package com.dcfs.managerplatform.monitor.api.exception
 * @author jiazw
 * @date 2016年5月19日 下午1:57:20
 * @version V1.0
 * 变更记录：
 *   jiazw 2016年5月19日 下午1:57:20 新建
 */
package com.dcfs.smartaibank.manager.monitor.core.exception;

import com.dcfs.smartaibank.core.exception.BaseException;

/**
 * 执行器异常类
 *
 * @author jiazw
 */
public class ExecutorException extends BaseException {
    public ExecutorException(String code) {
        super(code);
    }

    public ExecutorException(String code, String message) {
        super(code, message);
    }

    public ExecutorException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public ExecutorException(String code, Throwable cause) {
        super(code, cause);
    }

    public ExecutorException(String code,
                             String message,
                             Throwable cause,
                             boolean enableSuppression,
                             boolean writableStackTrace) {
        super(code, message, cause, enableSuppression, writableStackTrace);
    }
}
