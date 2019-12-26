package com.zab.netty.protal.exceptions;

import com.zab.netty.protal.utils.StringEasyUtil;

/**
 * 上传文件错误
 */
public class FastDFSException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -4333591246791106914L;

    public FastDFSException(String msg) {
        super(msg);
    }

    public FastDFSException(Throwable e) {
        super(e);
    }

    public FastDFSException(String msg, Throwable e) {
        super(msg, e);
    }

    public FastDFSException(String message, Throwable e, Object... parameters) {
        super(StringEasyUtil.injectByBrackets(message, parameters), e);
    }

    public FastDFSException(String message, Object... parameters) {
        super(StringEasyUtil.injectByBrackets(message, parameters));
    }
}