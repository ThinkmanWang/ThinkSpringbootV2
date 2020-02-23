package com.thinkman.springboot.project.mapper;

import com.thinkman.springboot.framework.aspecjt.lang.annotation.DataSource;
import com.thinkman.springboot.framework.aspecjt.lang.enums.DataSourceType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
@DataSource(DataSourceType.SLAVE)
public interface Test1Mapper {

    @Select("SELECT count(1) from t_test")
    public Integer getCount();
}
