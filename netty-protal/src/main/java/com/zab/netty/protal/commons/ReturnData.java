package com.zab.netty.protal.commons;

import com.zab.netty.protal.enums.SysCodeMsg;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 自定义响应数据结构
 * 				这个类是提供给门户，ios，安卓，微信商城用的
 * 				门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 * 				其他自行处理
 * 				200：表示成功
 * 				500：表示错误，错误信息在msg字段中
 * 				501：bean验证错误，不管多少个错误都以map形式返回
 * 				502：拦截器拦截到用户token出错
 * 				555：异常抛出信息
 */
@Data
public class ReturnData implements Serializable {

    private static final long serialVersionUID = 3766693959838514231L;
    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    private String ok;	// 不使用

    public static ReturnData build(Integer status, String msg, Object data) {
        return new ReturnData(status, msg, data);
    }

    public static ReturnData ok(Object data) {
        return new ReturnData(data);
    }

    public static ReturnData ok() {
        return new ReturnData(null);
    }

    public static ReturnData errorMsg(String msg) {
        return new ReturnData(500, msg, null);
    }

    public static ReturnData errorMap(Object data) {
        return new ReturnData(501, "error", data);
    }

    public static ReturnData errorTokenMsg(String msg) {
        return new ReturnData(502, msg, null);
    }

    public static ReturnData errorException(String msg) {
        return new ReturnData(555, msg, null);
    }

    public ReturnData() {

    }

//    public static LeeJSONResult build(Integer status, String msg) {
//        return new LeeJSONResult(status, msg, null);
//    }

    public ReturnData(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ReturnData(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

}
