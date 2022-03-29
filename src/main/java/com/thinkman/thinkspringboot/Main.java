package com.thinkman.thinkspringboot;

import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan("com.thinkman.thinkspringboot.project.mapper")
@MapperScan("com.thinkman.thinkspringboot.project.mapper.xml")
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("FXXK");
        SpringApplication.run(Main.class, args);
    }

}
