package com.zab.netty.protal.netty;

import com.alibaba.fastjson.JSON;
import com.zab.netty.protal.entity.ChatMsg;
import com.zab.netty.protal.enums.MsgActionEnum;
import com.zab.netty.protal.jpush.IJPushAttach;
import com.zab.netty.protal.jpush.impl.JPushAttach;
import com.zab.netty.protal.service.IChatMsgService;
import com.zab.netty.protal.service.impl.ChatMsgServiceImpl;
import com.zab.netty.protal.utils.JudgeUtil;
import com.zab.netty.protal.utils.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 处理消息的handler
 * TextWebSocketFrame：在netty中，是用于为websocket专门处理文本的对象，frame是消息的载体
 */
@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 用于记录和管理所有客户端的channel
    public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 获取客户端传输过来的消息
        String content = msg.text();

        Channel currentChannel = ctx.channel();

        // 1.获取客户端发来的消息
        DataContent dataContent = JSON.parseObject(content, DataContent.class);
        Integer action = dataContent.getAction();
        // 2.判断消息类型，根据不同的类型来处理不同的业务
        if (JudgeUtil.isDBEq(action, MsgActionEnum.CONNECT.type)) {
            //  2.1 当websocket第一次open的时候，初始化channel，把用户的channel和userid关联起来
            String senderId = dataContent.getNettyChatMsg().getSenderId();
            UserChannelRel.put(senderId, currentChannel);
        } else if (JudgeUtil.isDBEq(action, MsgActionEnum.CHAT.type)) {
            //  2.2 聊天类型的消息，把聊天记录保存到数据库，同时标记消息的签收状态[未签收]
            NettyChatMsg nettyChatMsg = dataContent.getNettyChatMsg();
            // 保存消息到数据库,并且标记为未签收
            IChatMsgService chatMsgService = SpringUtil.getBean(ChatMsgServiceImpl.class);
            String msgId = chatMsgService.addChatMsg(nettyChatMsg);
            nettyChatMsg.setMsgId(msgId);

            DataContent dataContentMsg = new DataContent();
            dataContentMsg.setNettyChatMsg(nettyChatMsg);

            // 发送消息
            // 从全局用户channel关系中获取接收方的channel
            Channel receiverChannel = UserChannelRel.get(nettyChatMsg.getReceiveId());

            if (receiverChannel == null) {
                // TODO channel为空代表用户离线，推送消息(JPush，个推，小米推送)
//                pushOffLineMSG(nettyChatMsg.getMsg(), nettyChatMsg.getMsg(), nettyChatMsg.getReceiveId(), new HashMap<>());
            } else {
                // 当receiverChannel不为空时，从ChannelGroup去查找对应的channel是否存在
                Channel findChannel = users.find(receiverChannel.id());
                if (findChannel != null) {
                    // 用户在线
                    receiverChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(dataContentMsg)));
                } else {
                    // 用户离线 todo 推送离线消息
//                    pushOffLineMSG(nettyChatMsg.getMsg(), nettyChatMsg.getMsg(), nettyChatMsg.getReceiveId(), new HashMap<>());
                }
            }

        } else if (JudgeUtil.isDBEq(action, MsgActionEnum.SIGNED.type)) {
            //  2.3 签收消息类型，针对具体的消息进行签收，修改数据库中对应消息的签收状态[已签收]
            IChatMsgService chatMsgService = SpringUtil.getBean(ChatMsgServiceImpl.class);
            // 扩展字段在signed类型的消息中，代表需要去签收的消息id，逗号隔离
            String msgIdsStr = dataContent.getExtand();
            if (StringUtils.isNotBlank(msgIdsStr)) {
                String[] msgIds = msgIdsStr.split(",");
                List<String> msgIdList = new LinkedList<>();
                for (String msgId : msgIds) {
                    if (StringUtils.isNotBlank(msgId)) ;
                    msgIdList.add(msgId);
                }

                if (!JudgeUtil.isEmpty(msgIdList)) {
                    // 批量签收
                    chatMsgService.updateMsgSigned(msgIdList);
                }

            }


        } else if (JudgeUtil.isDBEq(action, MsgActionEnum.KEEPALIVE.type)) {
            //  2.4 心跳类型的消息
            System.err.println("收到来自channel为[" + currentChannel + "]的心跳包");
        }

    }

    /**
     * 当客户端连接服务端之后(打开连接)
     * 获取客户端的channel，并且放到ChannelGroup中去进行管理
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        String channelId = ctx.channel().id().asShortText();
        System.err.println("客户端被移除，channelId为：" + channelId);
        // 当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
        users.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 发生异常之后关闭链接（关闭channel），随后从ChannelGroup中移除
        ctx.channel().close();
        users.remove(ctx.channel());
    }

    private void pushOffLineMSG(String title, String msg, String acceptUserId, Map<String, String> extras) {
        // 获取JPush的api实例
        IJPushAttach jPushAttach = SpringUtil.getBean(JPushAttach.class);
        jPushAttach.push(title, msg, 1, extras, acceptUserId);
    }

}
