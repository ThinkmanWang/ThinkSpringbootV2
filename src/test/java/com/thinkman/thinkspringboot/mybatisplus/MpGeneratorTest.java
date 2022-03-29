package com.thinkman.thinkspringboot.mybatisplus;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;

public class MpGeneratorTest {
    @Test
    void generateCode() {
        generate("com.thinkman.thinkspringboot.project.model"
                , "db"
                , "t_test"
        );
    }

    /**
     * 自动生成代码
     *
     * @param parentPackage 父级包名
     * @param modelName     模块名称
     * @param tableNames    要生成的表名
     */
    private void generate(String parentPackage, String modelName, String... tableNames) {
        AutoGenerator mpg = new AutoGenerator();

        // 1.1.全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("/home/thinkman/Github-Thinkman/ThinkSpringbootV2/temp"); // 文件保存位置
        gc.setAuthor("Thinkman"); // 每个类顶部注释的作者
        gc.setOpen(false); // 默认生成后会打开文件所在位置，不想打开可以设置为false，设计这个属性的初衷应该是怕用户找不到生成的文件
        // gc.setFileOverride(true); // 是否覆盖原有文件：这个属性慎用，要是哪天误点，把自己写了几天的代码覆盖，那就傻逼了
        mpg.setGlobalConfig(gc);

        // 1.2.策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel); // 表名 -> 实体类名：使用驼峰命名
        strategy.setColumnNaming(NamingStrategy.underline_to_camel); // 数据库字段 -> 属性名：驼峰命名
        strategy.setEntityLombokModel(true); // 使用 lombok
        strategy.setRestControllerStyle(true); // 使用 @RestController 注解
        strategy.setControllerMappingHyphenStyle(true); // @RequestMapping 默认驼峰命名法 -> 改为用-分隔，更好看
        strategy.setEntityTableFieldAnnotationEnable(true); // 将表上的注释映射到实体类
        strategy.setInclude(tableNames); // 需要生成的表名 可变形参传入
        mpg.setStrategy(strategy);

        // 2.1.数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://172.16.0.2:3306/db_thinkman?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("Ab123145");
        mpg.setDataSource(dsc);

        // 2.2.包配置、controller、service、mapper、entity都可以这里设置
        PackageConfig pc = new PackageConfig();
        pc.setParent(parentPackage); // 父级包路径
        pc.setModuleName(modelName); // 模块名称：生成 @RequestMapping 时的前缀
        mpg.setPackageInfo(pc);

        // 3.自动生成
        mpg.execute();
    }
}
