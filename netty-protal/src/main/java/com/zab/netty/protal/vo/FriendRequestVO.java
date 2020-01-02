package com.zab.netty.protal.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FriendRequestVO implements Serializable {

    private static final long serialVersionUID = -8039885116711465361L;

    private String sendUserId;
    private String sendUsername;
    private String sendFaceImage;
    private String sendNickname;

}
