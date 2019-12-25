package com.zab.netty.protal.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户请求表
 * </p>
 *
 * @author zab
 * @since 2019-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FriendsRequest extends Model<FriendsRequest> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 发送用户编号
     */
    private String sendUserId;

    /**
     * 接收用户编号
     */
    private String acceptUserId;

    /**
     * 请求日期
     */
    private String requestDateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
