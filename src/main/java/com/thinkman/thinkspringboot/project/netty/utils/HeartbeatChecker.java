package com.thinkman.thinkspringboot.project.netty.utils;

import com.thinkman.thinkspringboot.project.netty.tcpserver.MainTCPServer;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class HeartbeatChecker {
    private static final Logger logger = Logger.getLogger(HeartbeatChecker.class);

    private int HEARTBEAT_TIMEOUT = 60 * 1000;
    private int HEARTBEAT_LOOP_SLEEP = 60 * 1000;

    private ConcurrentHashMap<Channel, Long> m_mapLastHeartbeat = new ConcurrentHashMap<>(); //last heartbeat timestamp

    public interface HeartbeatCheckerListener {
        public void onRemove(Channel channel);
    }
    private HeartbeatCheckerListener mListener = null;

    public void setListener(HeartbeatCheckerListener listener) {
        mListener = listener;
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        logger.info("Start heartbeat check");

                        for(Map.Entry<Channel, Long> entry: m_mapLastHeartbeat.entrySet()) {
                            if (System.currentTimeMillis() > entry.getValue() + HEARTBEAT_TIMEOUT) {

                                if (mListener != null) {
                                    mListener.onRemove(entry.getKey());
                                }
                            }
                        }

                        Thread.sleep(HEARTBEAT_LOOP_SLEEP);
                    } catch (Exception ex) {

                    }
                }
            }
        }).start();
    }

    public void onChannelConnect(Channel channel) {
        onChannelHeartbeat(channel);
    }

    public void onChannelLogin(String szUserId, Channel channel) {
        m_mapLastHeartbeat.put(channel, System.currentTimeMillis());
    }

    public void onChannelHeartbeat(Channel channel) {
        m_mapLastHeartbeat.put(channel, System.currentTimeMillis());
    }

    public void onChannelDisconnect(Channel channel) {
        m_mapLastHeartbeat.remove(channel);
    }
}
