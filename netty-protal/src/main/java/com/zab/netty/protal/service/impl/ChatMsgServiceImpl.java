package com.zab.netty.protal.service.impl;

import com.zab.netty.protal.entity.ChatMsg;
import com.zab.netty.protal.enums.MsgSignFlagEnum;
import com.zab.netty.protal.idwork.Sid;
import com.zab.netty.protal.mapper.ChatMsgMapper;
import com.zab.netty.protal.netty.NettyChatMsg;
import com.zab.netty.protal.service.IChatMsgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 聊天记录表 服务实现类
 * </p>
 *
 * @author zab
 * @since 2019-12-25
 */
@Service
public class ChatMsgServiceImpl extends ServiceImpl<ChatMsgMapper, ChatMsg> implements IChatMsgService {

    @Resource
    private Sid sid;
    @Resource
    private ChatMsgMapper chatMsgMapper;

    @Override
    @Transactional
    public String addChatMsg(NettyChatMsg nettyChatMsg) {
        String id = sid.nextShort();
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setSendUserId(nettyChatMsg.getSenderId());
        chatMsg.setAcceptUserId(nettyChatMsg.getReceiveId());
        chatMsg.setMsg(nettyChatMsg.getMsg());
        chatMsg.setId(id);
        chatMsg.setCreateTime(new Date());
        chatMsg.setSignFlag(MsgSignFlagEnum.unsign.type);
        save(chatMsg);
        return id;
    }

    @Override
    @Transactional
    public void updateMsgSigned(List<String> msgIdList) {
        chatMsgMapper.batchUpdateMsgSigned(msgIdList);
    }
}
