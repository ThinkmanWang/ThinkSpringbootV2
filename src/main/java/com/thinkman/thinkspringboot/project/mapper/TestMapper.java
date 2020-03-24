package com.thinkman.thinkspringboot.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestMapper {

    @Select("SELECT count(1) from t_test")
    public Integer getCount();
}
