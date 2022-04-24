package com.jimisun.parametricdatasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源切换类
 *
 * @author jimisun
 * @create 2022-03-29 2:37 PM
 **/

public class DynamicDataSource extends AbstractRoutingDataSource {

    private Map<Object, Object> backupTargetDataSources;


    /**
     * 自定义构造函数
     */
    public DynamicDataSource(DataSource defaultDataSource, Map<Object, Object> targetDataSource) {
        backupTargetDataSources = targetDataSource;
        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(backupTargetDataSources);
        super.afterPropertiesSet();
    }

    /**
     * 添加新数据源
     */
    public void addDataSource(String key, DataSource dataSource) {
        this.backupTargetDataSources.put(key, dataSource);
        super.setTargetDataSources(this.backupTargetDataSources);
        super.afterPropertiesSet();
    }


    /**
     * 路由切换规则
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        // 此处暂时返回固定 master 数据源, 后面按动态策略修改
        return DynamicDataSourceContextHolder.getContextKey();
    }
}
