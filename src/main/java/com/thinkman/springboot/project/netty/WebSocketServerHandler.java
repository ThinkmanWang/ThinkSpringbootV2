package com.thinkman.springboot.project.netty;

import com.thinkman.springboot.Main;
import com.thinkman.springboot.project.service.Test1Service;
import com.thinkman.springboot.project.service.TestService;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

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

    private static Set<Channel> setChannel = new HashSet<Channel>();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        logger.info("收到消息：" + textWebSocketFrame.text());
//        channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame("服务器随机数：" + Math.random()));
        logger.info("Handler 线程：" + Thread.currentThread().getId());

        //用于执行复杂任务
        channelHandlerContext.executor().submit(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                    int nVal1 = testService.getCount();
                    int nVal2 = test1Service.getCount();

                    redisTemplate.opsForValue().set("test-2020", "2021");
                    String szVal = (String) redisTemplate.opsForValue().get("test-2020");

                    logger.info("耗时线程：" + Thread.currentThread().getId());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        double dRet = Math.random();
        for (Channel channel : setChannel) {
            channel.writeAndFlush(new TextWebSocketFrame("服务器随机数：" + dRet)).addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isDone()) {

                    }
                }
            });
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded!!!");
        setChannel.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved!!!");
    }

}
