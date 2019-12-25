package com.zab.netty.protal.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 聊天记录表
 * </p>
 *
 * @author zab
 * @since 2019-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChatMsg extends Model<ChatMsg> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 发送人编号
     */
    private String sendUserId;

    /**
     * 接收人编号
     */
    private String acceptUserId;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 签收状态
     */
    private Integer signFlag;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
