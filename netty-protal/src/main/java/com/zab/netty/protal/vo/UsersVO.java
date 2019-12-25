package com.zab.netty.protal.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UsersVO implements Serializable {
    private String id;

    private String username;

    private String faceImage;

    private String faceImageBig;

    private String nickname;

    private String qrcode;

}
