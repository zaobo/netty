package com.zab.netty.protal.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author zab
 * @since 2019-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Users extends Model<Users> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @TableId
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户头像
     */
    private String faceImage;

    /**
     * 用户大头像
     */
    private String faceImageBig;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 二维码(主要用于添加好友)
     */
    private String qrcode;

    /**
     * 每一台设备的编号
     */
    private String cid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
