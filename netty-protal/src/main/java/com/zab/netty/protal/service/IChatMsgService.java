package com.zab.netty.protal.service;

import com.zab.netty.protal.entity.ChatMsg;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zab.netty.protal.netty.NettyChatMsg;

import java.util.List;

/**
 * <p>
 * 聊天记录表 服务类
 * </p>
 *
 * @author zab
 * @since 2019-12-25
 */
public interface IChatMsgService extends IService<ChatMsg> {

    String addChatMsg(NettyChatMsg nettyChatMsg);

    void updateMsgSigned(List<String> msgIdList);
}
