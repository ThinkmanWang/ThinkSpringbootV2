package com.thinkman.thinkspringboot.project.controller;

import com.google.gson.Gson;
import com.thinkman.thinkspringboot.Main;
import com.thinkman.thinkspringboot.common.utils.AjaxResult;
import com.thinkman.thinkspringboot.project.service.Test1Service;
import com.thinkman.thinkspringboot.project.service.TestService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    private static final Logger logger = Logger.getLogger(TestController.class);

    @Autowired
    TestService testService;

    @Autowired
    Test1Service test1Service;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("/test")
    @ResponseBody
    public String hello() {
        logger.info("FXXXXXXXK");

        int nVal1 = testService.getCount();
        int nVal2 = test1Service.getCount();

//        redisTemplate.opsForValue().set("test-2020", "2021");
//        String szVal = (String) redisTemplate.opsForValue().get("test-2020");

        return "Hello World";
    }

    @RequestMapping("/test_json")
    @ResponseBody
    public String testJson() {
        logger.info("FXXXXXXXK");
        return new Gson().toJson(AjaxResult.success());
    }
}
