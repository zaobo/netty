package com.zab.netty.protal.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MyFriendsVO implements Serializable {
    private static final long serialVersionUID = -8697694755178595068L;
    private String friendUserId;
    private String friendUsername;
    private String friendFaceImage;
    private String friendNickname;

}
