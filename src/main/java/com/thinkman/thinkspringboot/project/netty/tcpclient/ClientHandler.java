package com.thinkman.thinkspringboot.project.netty.tcpclient;

import com.thinkman.thinkspringboot.project.netty.tcpserver.TCPServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;

public class ClientHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger logger = Logger.getLogger(TCPServerHandler.class);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        logger.info("FXXK");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        logger.info("FXXK");
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("client接收到服务器返回的消息:"+msg);
    }
}
