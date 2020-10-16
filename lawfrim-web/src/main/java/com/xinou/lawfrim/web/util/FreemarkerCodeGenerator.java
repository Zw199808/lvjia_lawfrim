package com.xinou.lawfrim.web.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.xinou.lawfrim.common.util.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class FreemarkerCodeGenerator {
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath+":路径");
        // 输出的位置
        gc.setOutputDir(projectPath + "/lawfrim-web/src/main/java");
        gc.setAuthor("Wangxin");
        gc.setIdType(IdType.AUTO);
        gc.setOpen(false);
        // 是否生成swagger2
        gc.setSwagger2(true);
        gc.setFileOverride(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        // 数据连接
        dsc.setUrl("jdbc:mysql://578d7e0e5ca35.bj.cdb.myqcloud.com:3449/lvjia_lawfrim?useUnicode=true&characterEncoding=utf8&useSSL=true");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        // 数据库用户名
        dsc.setUsername("wangxin");
        // 数据库密码
        dsc.setPassword("wangxin2020");
        //自定义类型转化 不自定义 数据库的tinyint到java 会变成 boolean
        dsc.setTypeConvert(new MySqlTypeConvert());
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        // 包的路径
        pc.setParent("com.xinou.lawfrim.web");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        String templatePath = "/templates/mapper.xml.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // .xml位置
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/lawfrim-web/src/main/resources/mappers/" +
                        tableInfo.getEntityName() + "Mapping" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 实体的公共父类
        strategy.setSuperEntityClass(BaseEntity.class);
        strategy.setEntityLombokModel(true);
        strategy.setChainModel(true);
        //是否生成实体时，生成字段注解
        strategy.setEntityTableFieldAnnotationEnable(true);
        // 是否生成controller
        strategy.setRestControllerStyle(true);
        // 实体基类
        strategy.setSuperEntityColumns("id", "gmt_create", "gmt_modified", "is_delete");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        // 要忽略的表
        strategy.setExclude(
                "rel_object_content",
                "sys_app",
                "sys_carousel",
                "sys_msg_log",
                "sys_permission",
                "sys_re_role_permission",
                "sys_re_user_app",
                "sys_re_user_role",
                "sys_resources",
                "sys_role",
                "sys_user"
        );
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
