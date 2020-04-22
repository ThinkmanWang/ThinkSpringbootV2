package com.thinkman.thinkspringboot.project.netty.tcpserver;

import com.thinkman.thinkspringboot.project.netty.utils.HeartbeatChecker;
import com.thinkman.thinkspringboot.project.netty.websocket.WebSocketServerHandler;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
@ChannelHandler.Sharable
public class TCPServerHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger logger = Logger.getLogger(TCPServerHandler.class);

    private static HeartbeatChecker mHeartbeatChecker = new HeartbeatChecker();

    @PostConstruct
    public void init() {
        mHeartbeatChecker.setListener(new HeartbeatChecker.HeartbeatCheckerListener() {
            @Override
            public void onRemove(Channel channel) {
                logger.info("NO HEARTBEAT, close connection");

                try {
                    channel.close();
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                }
            }
        });

        mHeartbeatChecker.start();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object objMsg) throws Exception {
//        logger.info("FXXK");
        mHeartbeatChecker.onChannelHeartbeat(ctx.channel());

        logger.info("SERVER接收到消息:"+objMsg);
        ctx.channel().writeAndFlush("yes, server is accepted you ,nice !"+objMsg);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        logger.info("FXXK");

        mHeartbeatChecker.onChannelConnect(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        mHeartbeatChecker.onChannelDisconnect(ctx.channel());
//        logger.info("FXXK");
    }
}
