package com.zab.netty.protal.jpush;

import java.util.List;
import java.util.Map;

public interface IJPushAttach {

    static final String ALL_PUSH = "jpall";

    /**
     * 极光推送
     *
     * @param title
     * @param msg
     * @param alias
     */
    void push(String title, String msg, Integer pushType, Map<String, String> extras, String... alias);

    void push(String title, String msg, Integer pushType, Map<String, String> extras, List<String> alias);

    void push(Boolean ios, String title, String msg, Integer pushType, Map<String, String> extras, String... alias);

    /**
     * @param ios
     * @param title
     * @param msg
     * @param pushType 1状态栏推送，2弹框推送
     * @param extras
     * @param obj
     * @param alias
     */
    void push(Boolean ios, String title, String msg, Integer pushType, Map<String, String> extras, List<String> alias);

}
