package com.zab.netty.protal.handler;

import com.zab.netty.protal.commons.ReturnData;
import com.zab.netty.protal.exceptions.FastDFSException;
import com.zab.netty.protal.exceptions.ProjectException;
import com.zab.netty.protal.exceptions.WrongArgumentException;
import com.zab.netty.protal.exceptions.WrongDataException;
import com.zab.netty.protal.utils.JudgeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.sql.SQLException;

@ControllerAdvice
public class ControllerExceptionHandler {

    //这个注解是指当controller中抛出这个指定的异常类的时候，都会转到这个方法中来处理异常
    @ExceptionHandler(RuntimeException.class)
    //将返回的值转成json格式的数据
    @ResponseBody
    //返回的状态码
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)     //服务内部错误
    public ReturnData handlerRuntimeException(Exception ex) {
        String msg;
        if (ex instanceof WrongArgumentException || StringUtils.contains(ex.getMessage(), WrongArgumentException.class.getName()) || (null != ex.getCause() && ex.getCause() instanceof WrongArgumentException)) {
            msg = "请求参数有误," + ex.getMessage();
        } else if (ex instanceof WrongDataException || StringUtils.contains(ex.getMessage(), WrongDataException.class.getName()) || (null != ex.getCause() && ex.getCause() instanceof WrongDataException)) {
            msg = "数据库数据有误," + ex.getMessage();
        } else if (ex instanceof FastDFSException || StringUtils.contains(ex.getMessage(), FastDFSException.class.getName()) || (null != ex.getCause() && ex.getCause() instanceof FastDFSException)) {
            msg = "文件上传异常," + ex.getMessage();
        }  else if (ex instanceof ProjectException || StringUtils.contains(ex.getMessage(), ProjectException.class.getName()) || (null != ex.getCause() && ex.getCause() instanceof ProjectException)) {
            msg = "开发失误," + ex.getMessage();
        } else if (ex instanceof SQLException || StringUtils.contains(ex.getMessage(), SQLException.class.getName()) || (null != ex.getCause() && ex.getCause() instanceof SQLException)) {
            msg = "数据库异常";
        } else if (ex instanceof IOException || StringUtils.contains(ex.getMessage(), IOException.class.getName()) || (null != ex.getCause() && ex.getCause() instanceof IOException)) {
            msg = "IO流异常";
        } else if (ex instanceof RuntimeException) {
            msg = null;
            // 查看是否是mapper引起的
            if (!JudgeUtil.isEmpty(ex.getStackTrace())) {
                for (StackTraceElement ste : ex.getStackTrace()) {
                    if (StringUtils.startsWith(ste.getClassName(),
                            "com.mmal.mapper")) {
                        msg = "数据库操作异常";
                    }
                }
            }

            if (null == msg) {
                msg = "运行时异常";
            }
        } else {
            msg = "系统错误";
        }
        return ReturnData.errorMsg(msg);
    }
}