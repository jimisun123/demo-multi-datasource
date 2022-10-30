package com.jimisun.dynamicdatasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源切换类
 *
 * @author jimisun
 * @create 2022-03-29 2:37 PM
 **/

public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 路由切换规则
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getContextKey();
    }
}
