package com.zab.netty.hello;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * 初始化器，channel注册后，会执行里面相应的初始化方法
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        // 通过SocketChannel去获得对应的管道
        ChannelPipeline pipeline = channel.pipeline();
    }
}
