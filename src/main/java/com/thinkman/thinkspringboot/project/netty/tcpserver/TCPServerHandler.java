package com.thinkman.thinkspringboot.project.netty.tcpserver;

import com.thinkman.thinkspringboot.project.netty.websocket.WebSocketServerHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class TCPServerHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger logger = Logger.getLogger(TCPServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object objMsg) throws Exception {
//        logger.info("FXXK");
        logger.info("SERVER接收到消息:"+objMsg);
        ctx.channel().writeAndFlush("yes, server is accepted you ,nice !"+objMsg  + "\r\n");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        logger.info("FXXK");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        logger.info("FXXK");
    }
}
