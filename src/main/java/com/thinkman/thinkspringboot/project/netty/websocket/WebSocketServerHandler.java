package com.thinkman.thinkspringboot.project.netty.websocket;

import com.thinkman.thinkspringboot.project.netty.utils.HeartbeatChecker;
import com.thinkman.thinkspringboot.project.service.Test1Service;
import com.thinkman.thinkspringboot.project.service.TestService;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;

@Component
@ChannelHandler.Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static final Logger logger = Logger.getLogger(WebSocketServerHandler.class);

    @Resource
    TestService testService;

    @Resource
    Test1Service test1Service;

    @Resource
    RedisTemplate redisTemplate;

    private static ConcurrentSkipListSet<Channel> m_setChannel = new ConcurrentSkipListSet<>();
    private static HeartbeatChecker mHeartbeatChecker = new HeartbeatChecker();

    @PostConstruct
    public void init() {
        mHeartbeatChecker.setListener(new HeartbeatChecker.HeartbeatCheckerListener() {
            @Override
            public void onRemove(Channel channel) {
                logger.info("NO HEARTBEAT, close connection");

                try {
                    m_setChannel.remove(channel);
                    channel.close();
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                }
            }
        });

        mHeartbeatChecker.start();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        logger.info("收到消息：" + textWebSocketFrame.text());
        logger.info("Handler 线程：" + Thread.currentThread().getId());
        mHeartbeatChecker.onChannelHeartbeat(ctx.channel());

        for (final Channel channel : m_setChannel) {
            channel.eventLoop().schedule(new Runnable() {
                @Override
                public void run() {
                    logger.info("I/O线程：" + Thread.currentThread().getId());

                    channel.writeAndFlush(new TextWebSocketFrame(textWebSocketFrame.text()));
                }
            }, 0, TimeUnit.SECONDS);
        }

        //Threads.sleep(5000);

        int nVal1 = testService.getCount();
        int nVal2 = test1Service.getCount();

        redisTemplate.opsForValue().set("test-2020", "2021");
        String szVal = (String) redisTemplate.opsForValue().get("test-2020");

        //用于执行复杂任务
//        ctx.executor().schedule(new Runnable() {
//            public void run() {
//                try {
////                    Thread.sleep(5000);
//
//                    int nVal1 = testService.getCount();
//                    int nVal2 = test1Service.getCount();
//
//                    redisTemplate.opsForValue().set("test-2020", "2021");
//                    String szVal = (String) redisTemplate.opsForValue().get("test-2020");
//
//                    logger.info("耗时线程：" + Thread.currentThread().getId());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, 0, TimeUnit.SECONDS);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded!!!");
        logger.info("Handler 线程：" + Thread.currentThread().getId());

        m_setChannel.add(ctx.channel());
        mHeartbeatChecker.onChannelConnect(ctx.channel());

        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                logger.info("I/O线程：" + Thread.currentThread().getId());
            }
        }, 0, TimeUnit.SECONDS);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        m_setChannel.remove(ctx.channel());
        mHeartbeatChecker.onChannelDisconnect(ctx.channel());
        System.out.println("handlerRemoved!!!");
    }

}
