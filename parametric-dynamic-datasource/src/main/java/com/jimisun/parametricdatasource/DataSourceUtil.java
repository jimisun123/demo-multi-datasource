package com.jimisun.parametricdatasource;

import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

/**
 * @author jimisun
 * @create 2022-04-23 10:41
 **/
public class DataSourceUtil {

    /**
     * 创建新的数据源，注意：此处只针对 MySQL 数据库
     * <p>
     * logger.info("初始化数据源："+dataSource.getName()+",url:"+dataSource.getUrl());
     * DruidDataSource druidDataSource = new DruidDataSource();
     * druidDataSource.setUrl(dataSource.getUrl());
     * druidDataSource.setDriverClassName(dataSource.getDriverclassname());
     * druidDataSource.setUsername(dataSource.getUsername());
     * druidDataSource.setPassword(dataSource.getPassword());
     * //配置初始化大小、最小、最大
     * druidDataSource.setInitialSize(dataSource.getInitialSize().intValue());
     * druidDataSource.setMinIdle(dataSource.getMinIdle().intValue());
     * druidDataSource.setMaxActive(dataSource.getMaxActive().intValue());
     * //连接泄漏监测
     * druidDataSource.setRemoveAbandoned(true);
     * druidDataSource.setRemoveAbandonedTimeout(60);
     * //配置获取连接等待超时的时间
     * druidDataSource.setMaxWait(5000);
     * //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
     * druidDataSource.setTimeBetweenEvictionRunsMillis(20000);
     * druidDataSource.setTestWhileIdle(true);
     * druidDataSource.setTestOnBorrow(true);
     * druidDataSource.setBreakAfterAcquireFailure(true);
     * druidDataSource.setConnectionErrorRetryAttempts(1);
     * return druidDataSource;
     */
    public static DataSource makeNewDataSource(DbInfo dbInfo) {
        String url = "jdbc:mysql://" + dbInfo.getIp() + ":" + dbInfo.getPort() + "/" + dbInfo.getDbName()
                + "?useSSL=false&serverTimezone=GMT%2B8&characterEncoding=UTF-8";
        String driveClassName = dbInfo.getDriveClassName() == null || dbInfo.getDriveClassName() == ""
                ? "com.mysql.cj.jdbc.Driver" : dbInfo.getDriveClassName();
        return DataSourceBuilder.create().url(url)
                .driverClassName(driveClassName)
                .username(dbInfo.getUsername())
                .password(dbInfo.getPassword())
                .build();
    }

    /**
     * 添加数据源到动态源中
     */
    public static void addDataSourceToDynamic(String key, DataSource dataSource) {
        DynamicDataSource dynamicDataSource = SpringContextHolder.getContext().getBean(DynamicDataSource.class);
        dynamicDataSource.addDataSource(key, dataSource);
    }

    /**
     * 根据数据库连接信息添加数据源到动态源中
     *
     * @param key
     * @param dbInfo
     */
    public static void addDataSourceToDynamic(String key, DbInfo dbInfo) {
        DataSource dataSource = makeNewDataSource(dbInfo);
        addDataSourceToDynamic(key, dataSource);
    }


}
