package com.thinkman.thinkspringboot.project.controller;

import com.google.gson.Gson;
import com.thinkman.thinkspringboot.Main;
import com.thinkman.thinkspringboot.common.utils.AjaxResult;
import com.thinkman.thinkspringboot.project.service.StudentService;
import com.thinkman.thinkspringboot.project.service.Test1Service;
import com.thinkman.thinkspringboot.project.service.TestService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    StudentService studentService;

    @RequestMapping("/helloworld")
    @ResponseBody
    public AjaxResult hello() {
        logger.info("FXXXXXXXK");

        return AjaxResult.success();
    }

    @RequestMapping("/test_json")
    @ResponseBody
    public String testJson() {
        logger.info("FXXXXXXXK");
        return new Gson().toJson(AjaxResult.success());
    }

    @RequestMapping("/student/get_all")
    @ResponseBody
    public String getAllStudent() {
        return new Gson().toJson(studentService.getAll());
    }

    @RequestMapping("/student/get_by_id")
    @ResponseBody
    public String getStudentById(@RequestParam(name = "sid", required = true, defaultValue = "1") int nId) {
        return new Gson().toJson(studentService.getByID(nId));
    }
}
