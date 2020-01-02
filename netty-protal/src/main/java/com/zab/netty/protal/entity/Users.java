package com.zab.netty.protal.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zab
 * @since 2019-12-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Users extends Model<Users> {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    /**
     * 用户名，账号，开信号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 我的头像，如果没有默认给一张
     */
    private String faceImage;

    private String faceImageBig;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 新用户注册后默认后台生成二维码，并且上传到fastdfs
     */
    private String qrcode;

    private String cid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
