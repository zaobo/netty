package com.zab.netty.protal.netty;

import lombok.Data;

import java.io.Serializable;

@Data
public class NettyChatMsg implements Serializable {
    private static final long serialVersionUID = -4905668291303666302L;
    /**
     * 发送者的用户id
     */
    private String senderId;
    /**
     * 接收者的用户id
     */
    private String receiveId;
    /**
     * 聊天内容
     */
    private String msg;
    /**
     * 消息id
     */
    private String msgId;
}
