package com.zab.netty.protal.service.impl;

import com.zab.netty.protal.entity.ChatMsg;
import com.zab.netty.protal.mapper.ChatMsgMapper;
import com.zab.netty.protal.service.IChatMsgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
