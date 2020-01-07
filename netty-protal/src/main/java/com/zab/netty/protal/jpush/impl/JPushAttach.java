package com.zab.netty.protal.jpush.impl;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.zab.netty.protal.jpush.IJPushAttach;
import com.zab.netty.protal.utils.JudgeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JPushAttach implements IJPushAttach {


    @Value("${protal.attach.jpush.appkey}")
    private String appKey;
    @Value("${protal.attach.jpush.masterSecret}")
    private String masterSecret;
    @Value("${protal.attach.jpush.tag}")
    private String tag;

    @Override
    public void push(String title, String msg, Integer pushType, Map<String, String> extras, List<String> alias) {
        this.push(null, title, msg, pushType, extras, alias);
    }

    @Override
    public void push(String title, String msg, Integer pushType, Map<String, String> extras, String... alias) {
        this.push(null, title, msg, pushType, extras, JudgeUtil.isEmpty(alias) ? null : Arrays.asList(alias));
    }

    @Override
    public void push(Boolean ios, String title, String msg, Integer pushType, Map<String, String> extras,  String... alias) {
        this.push(ios, title, msg, pushType, extras, JudgeUtil.isEmpty(alias) ? null : Arrays.asList(alias));
    }

    @Override
    public void push(Boolean ios, String title, String msg, Integer pushType, Map<String, String> extras, List<String> alias) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        // 创建JPushClient(极光推送的实例)
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        IosAlert alert = IosAlert.newBuilder().setTitleAndBody(title, null, msg).build();
        Platform platform = null == ios ? Platform.android_ios() : ios ? Platform.ios() : Platform.android();

        PushPayload pushPayload = JudgeUtil.isDBEq(2, pushType) ?
                PushPayload.newBuilder().setPlatform(platform)// 推送平台
                        .setAudience(JudgeUtil.isEmpty(alias) ? Audience.tag(tag)
                                : Audience.newBuilder().addAudienceTarget(AudienceTarget.tag(tag))
                                .addAudienceTarget(AudienceTarget.alias(alias)).build())// 推送目标
                        .setMessage(Message.newBuilder().setMsgContent(msg).setTitle(title).addExtras(extras).build())// 自定义消息
                        .setOptions(Options.newBuilder().setApnsProduction(!StringUtils.equalsIgnoreCase(tag, "test")).setTimeToLive(86400).build()).build()

                : PushPayload.newBuilder().setPlatform(platform)// 推送平台
                .setAudience(JudgeUtil.isEmpty(alias) ? Audience.tag(tag)
                        : Audience.newBuilder().addAudienceTarget(AudienceTarget.tag(tag))
                        .addAudienceTarget(AudienceTarget.alias(alias)).build())// 推送目标
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder().setAlert(msg).setTitle(title)
                                .addExtras(extras).build())
                        .addPlatformNotification(IosNotification.newBuilder().setAlert(alert)
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                .setSound("default").addExtras(extras).build())
                        .build())// 通知消息（标题，内容）
                .setOptions(Options.newBuilder().setApnsProduction(!StringUtils.equalsIgnoreCase(tag, "test")).setTimeToLive(86400).build()).build();

        try {
            jpushClient.sendPush(pushPayload);
        } catch (Exception e) {
            log.error("推送失败", e);
            throw new RuntimeException(e);
        }
    }

}
