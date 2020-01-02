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
public class MyFriends extends Model<MyFriends> {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    /**
     * 用户id
     */
    private String myUserId;

    /**
     * 用户的好友id
     */
    private String myFriendUserId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
