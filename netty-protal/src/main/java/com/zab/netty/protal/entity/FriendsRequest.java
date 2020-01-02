package com.zab.netty.protal.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

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
public class FriendsRequest extends Model<FriendsRequest> {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    private String sendUserId;

    private String acceptUserId;

    /**
     * 发送请求的事件
     */
    private Date requestDateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
