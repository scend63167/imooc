package com.imooc.demo.config;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
/**
 * @author zhaChengwei
 * @date 2019-02-25 14:11
 */
public class MyBatisGenerator {
    public static void main(String[] args) {
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("D:\\dev\\stsSpace\\springboot-mybatisplus\\src\\main\\java");
        gc.setFileOverride(true);//文件覆盖
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        gc.setAuthor("zhachengwei");
        gc.setServiceName("%sService");//文件命名规则
        gc.setControllerName("%sController");
        gc.setMapperName("%sDao");
        gc.setXmlName("%sMapper");
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("whispere");
        dsc.setUrl("jdbc:mysql://39.96.195.193/imooc?characterEncoding=utf8");

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);//下划线转驼峰命名
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.imooc.demo");
        pc.setMapper("dao");
        pc.setEntity("entity");
        pc.setXml("mapper.xml");
        pc.setService("service");
        pc.setController("controller");


        //整合配置
        AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(gc);
        mpg.setDataSource(dsc);
        mpg.setStrategy(strategy);
        mpg.setPackageInfo(pc);

        // 执行生成
        mpg.execute();
        System.out.println("生成完毕");
    }

}
