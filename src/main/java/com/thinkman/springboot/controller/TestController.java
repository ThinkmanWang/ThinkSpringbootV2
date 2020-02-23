package com.thinkman.springboot.controller;

import com.thinkman.springboot.service.Test1Service;
import com.thinkman.springboot.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Autowired
    TestService testService;

    @Autowired
    Test1Service test1Service;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        int nVal1 = testService.getCount();
        int nVal2 = test1Service.getCount();
        return "Hello World";
    }
}
