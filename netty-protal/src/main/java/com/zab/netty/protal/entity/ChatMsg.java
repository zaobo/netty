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
public class ChatMsg extends Model<ChatMsg> {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    private String sendUserId;

    private String acceptUserId;

    private String msg;

    /**
     * 消息是否签收状态
     *  1：签收
     *  0：未签收

     */
    private Integer signFlag;

    /**
     * 发送请求的事件
     */
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
