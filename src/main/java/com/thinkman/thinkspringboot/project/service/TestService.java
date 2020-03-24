package com.thinkman.thinkspringboot.project.service;

import com.thinkman.thinkspringboot.project.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    TestMapper testMapper;

    public int getCount() {
        Integer nVal = testMapper.getCount();

        return null == nVal ? 0 : nVal;
    }
}
