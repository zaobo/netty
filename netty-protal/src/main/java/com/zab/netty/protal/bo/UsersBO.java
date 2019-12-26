package com.zab.netty.protal.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UsersBO implements Serializable {
    private static final long serialVersionUID = -3198438984155912297L;
    private String userId;
    private String nickname;
    private String faceData;

}
