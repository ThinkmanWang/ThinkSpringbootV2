package com.thinkman.thinkspringboot.project.netty.utils;

import com.thinkman.thinkspringboot.project.netty.tcpserver.MainTCPServer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HeartbeatChecker {
    private static final Logger logger = Logger.getLogger(HeartbeatChecker.class);

    private static final int HEARTBEAT_TIMEOUT = 60 * 1000;

    @PostConstruct
    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        logger.info("Start heartbeat check");

                        Thread.sleep(HEARTBEAT_TIMEOUT);
                    } catch (Exception ex) {

                    }
                }
            }
        }).start();
    }

}
