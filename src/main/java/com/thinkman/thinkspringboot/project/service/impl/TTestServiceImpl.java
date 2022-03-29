package com.thinkman.thinkspringboot.project.service.impl;

import com.thinkman.thinkspringboot.project.model.db.TTest;
import com.thinkman.thinkspringboot.project.mapper.TTestMapper;
import com.thinkman.thinkspringboot.project.service.ITTestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 测试 服务实现类
 * </p>
 *
 * @author Thinkman
 * @since 2022-03-29
 */
@Service
public class TTestServiceImpl extends ServiceImpl<TTestMapper, TTest> implements ITTestService {

}
