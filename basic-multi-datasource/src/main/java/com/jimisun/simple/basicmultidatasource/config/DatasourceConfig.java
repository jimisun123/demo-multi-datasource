package com.jimisun.simple.basicmultidatasource.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * 多数据源配置类
 *
 * @author jimisun
 * @create 2022-03-25 5:47 PM
 **/
@Configuration
@PropertySource("classpath:config/jdbc.properties")
public class DatasourceConfig {

    @Bean("master")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("slave")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource(){
        return DataSourceBuilder.create().build();
    }
}
