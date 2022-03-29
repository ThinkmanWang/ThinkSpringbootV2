package com.thinkman.thinkspringboot.project.mapper;

import com.thinkman.thinkspringboot.project.entity.db.TTest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 测试 Mapper 接口
 * </p>
 *
 * @author Thinkman
 * @since 2022-03-29
 */
@Mapper
public interface TTestMapper extends BaseMapper<TTest> {

}
