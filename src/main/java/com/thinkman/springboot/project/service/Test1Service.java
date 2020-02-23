package com.thinkman.springboot.project.service;

import com.thinkman.springboot.project.mapper.Test1Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Test1Service {

    @Autowired
    Test1Mapper test1Mapper;

    public int getCount() {
        Integer nVal = test1Mapper.getCount();

        return null == nVal ? 0 : nVal;
    }
}
