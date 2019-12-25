package com.zab.netty.protal.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户和朋友的关联表
 * </p>
 *
 * @author zab
 * @since 2019-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MyFriends extends Model<MyFriends> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 自己的编号
     */
    private String myUserId;

    /**
     * 朋友的编号
     */
    private String myFriendUserId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
