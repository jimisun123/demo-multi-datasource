package com.jimisun.dynamicdatasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


/**
 * 加载系统内的数据源转换为bean注入到spring容器中
 *
 * @author jimisun
 * @create 2022-03-29 2:30 PM
 **/

@Configuration
@PropertySource("classpath:config/jdbc.properties")
@MapperScan(basePackages = "com.jimisun.mapper")
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class }) //排除自动装配 否则会出现错误
public class DynamicDataSourceConfig {

    @Bean(DataSourceConstants.DS_KEY_MASTER)
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(DataSourceConstants.DS_KEY_SLAVE)
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 设置动态数据源为主数据源
     *
     * @return
     */
    @Bean
    @Primary
    public DataSource dynamicDataSource() {
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put(DataSourceConstants.DS_KEY_MASTER, masterDataSource());
        dataSourceMap.put(DataSourceConstants.DS_KEY_SLAVE, slaveDataSource());
        //设置动态数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource());
        return dynamicDataSource;
    }


    /**
     * TODO 这里一定要使用动态数据源创建事务管理器 否则事务失效！！！
     * 事务管理器AOP保存在Thread中的Connection和Mybatis获取的Connection不是同一个，所以会失效！！！
     */
    @Bean(name = "TransactionManager")
    public DataSourceTransactionManager TransactionManager(@Qualifier("dynamicDataSource") DataSource dataSource) {
        return  new DataSourceTransactionManager(dataSource);
    }


}
