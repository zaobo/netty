package com.zab.netty.hello;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 实现客户端发送请求，服务器会返回hello netty
 */
public class HelloServer {
    public static void main(String[] args) throws Exception {
        // 主线程组
        // 定义一个线程组,用于接收客户端的连接，但是不做任何处理，跟老板一样，不做事
        EventLoopGroup mainGroup = new NioEventLoopGroup();
        // 从线程组，主线程组会把任务丢给从线程组，让从线程组去做任务
        EventLoopGroup subGroup = new NioEventLoopGroup();

        try {
            // netty服务器的创建，ServerBootstrap 是一个启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(mainGroup, subGroup)      //设置主从线程组
                    .channel(NioServerSocketChannel.class)  //设置nio双向通道
                    .childHandler(new HelloServerInitializer());                    //子处理器，用于处理subGroup

            // 启动server，并且设置8088为启动的端口号，同时启动方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();

            // 监听关闭的channel，设置为同步方式
            channelFuture.channel().closeFuture().sync();
        } finally {
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }

    }
}
