package com.thinkman.thinkspringboot.project.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class MainWSServer {

    @Resource
    WebSocketServerHandler webSocketServerHandler;

    @Value("${websocket.port}")
    private int port;

    static final EventExecutorGroup mExecutorGroup = new DefaultEventExecutorGroup(128);

    /**
     * 启动netty服务
     * @throws InterruptedException
     */
    @PostConstruct
    public void start() throws InterruptedException  {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EventLoopGroup bossGroup = new NioEventLoopGroup();
                EventLoopGroup workerGroup = new NioEventLoopGroup();
                try {
                    ServerBootstrap serverBootstrap = new ServerBootstrap();
                    serverBootstrap.group(bossGroup, workerGroup)
                            .channel(NioServerSocketChannel.class)
                            .handler(new LoggingHandler(LogLevel.INFO))
                            .childHandler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    ChannelPipeline pipeline = socketChannel.pipeline();
                                    pipeline.addLast(new HttpServerCodec());
                                    pipeline.addLast(new ChunkedWriteHandler());
                                    pipeline.addLast(new HttpObjectAggregator(8192));
                                    pipeline.addLast(new WebSocketServerProtocolHandler("/thinknetty"));
                                    pipeline.addLast(mExecutorGroup, webSocketServerHandler);
                                }
                            });

                    ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
                    channelFuture.channel().closeFuture().sync();
                } catch (Exception ex) {

                } finally {
                    bossGroup.shutdownGracefully();
                    workerGroup.shutdownGracefully();
                }
            }
        }).start();
    }

//    public static void main(String[] args) throws InterruptedException {
//        new MainServer().start();
//    }
}
