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
        // 此处暂时返回固定 master 数据源, 后面按动态策略修改
        return DynamicDataSourceContextHolder.getContextKey();
    }
}
