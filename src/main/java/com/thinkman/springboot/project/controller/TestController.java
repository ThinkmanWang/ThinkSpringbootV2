package com.thinkman.springboot.project.controller;

import com.thinkman.springboot.project.service.Test1Service;
import com.thinkman.springboot.project.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Autowired
    TestService testService;

    @Autowired
    Test1Service test1Service;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("/test")
    @ResponseBody
    public String hello() {
        int nVal1 = testService.getCount();
        int nVal2 = test1Service.getCount();

        redisTemplate.opsForValue().set("test-2020", "2021");
        String szVal = (String) redisTemplate.opsForValue().get("test-2020");

        return "Hello World";
    }
}
