package com.zab.netty.protal.netty;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataContent implements Serializable {
    private static final long serialVersionUID = -841025369848283984L;

    /**
     * 动作类型
     */
    private Integer action;
    /**
     * 用户的聊天内容实体
     */
    private NettyChatMsg nettyChatMsg;
    /**
     *  扩展字段
     */
    private String extand;

}
