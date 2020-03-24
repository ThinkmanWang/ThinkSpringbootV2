package com.thinkman.thinkspringboot.framework.datasource;

import com.thinkman.thinkspringboot.common.utils.StringUtils;
import com.thinkman.thinkspringboot.framework.aspecjt.lang.annotation.DataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 多数据源处理
 * 
 * @author ruoyi
 */
@Aspect
@Order(1)
@Component
public class DataSourceAspect
{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.thinkman.thinkspringboot.framework.aspecjt.lang.annotation.DataSource)"
            + "|| @within(com.thinkman.thinkspringboot.framework.aspecjt.lang.annotation.DataSource)")
    public void dsPointCut()
    {

    }

    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable
    {
        DataSource dataSource = getDataSource(point);

        if (StringUtils.isNotNull(dataSource))
        {
            DynamicDataSourceContextHolder.setDataSourceType(dataSource.value().name());
        }

        try
        {
            return point.proceed();
        }
        finally
        {
            // 销毁数据源 在执行方法之后
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }

    /**
     * 获取需要切换的数据源
     */
    public DataSource getDataSource(ProceedingJoinPoint point)
    {
        MethodSignature signature = (MethodSignature) point.getSignature();

        DataSource dataSource = signature.getMethod().getAnnotation(DataSource.class);
        if (StringUtils.isNotNull(dataSource)) {
            return dataSource;
        }

        return getDataSource(point.getTarget().getClass(), DataSource.class);
    }

    private DataSource getDataSource(Class<?> _class, Class<DataSource> annotationClass) {
        DataSource targetDataSource = null;
        while (_class != null) {
            targetDataSource = _class.getAnnotation(annotationClass);
            if (StringUtils.isNotNull(targetDataSource)) {
                return targetDataSource;
            }

            for (int i = 0; i < _class.getInterfaces().length; ++i) {
                Class<?> _interface = _class.getInterfaces()[i];
                targetDataSource = _interface.getAnnotation(annotationClass);
                if (StringUtils.isNotNull(targetDataSource)) {
                    return targetDataSource;
                }

//                for (int j = 0; j < _interface.getInterfaces().length; ++j) {
//                    targetDataSource = _interface.getInterfaces()[j].getAnnotation(annotationClass);
//                    if (StringUtils.isNotNull(targetDataSource)) {
//                        return targetDataSource;
//                    }
//                }
            }

            _class = _class.getSuperclass();
        }

        return null;
    }

}
