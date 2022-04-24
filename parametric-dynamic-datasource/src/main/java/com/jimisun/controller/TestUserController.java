package com.jimisun.controller;

import com.jimisun.parametricdatasource.DataSourceUtil;
import com.jimisun.parametricdatasource.DbInfo;
import com.jimisun.parametricdatasource.DynamicDataSourceContextHolder;
import com.jimisun.service.TestUserService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试控制器
 *
 * @author jimisun
 * @create 2022-03-29 4:15 PM
 **/

@RestController
@RequestMapping("/user")
public class TestUserController {

    @Resource
    private TestUserService testUserService;


    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 查询全部
     */
    @GetMapping("/listall")
    public Object listAll() {
        System.out.println(testUserService.getSlaveUserList());
        System.out.println(testUserService.getMasterUserList());
        System.out.println(testUserService.getDefaultUserList());
        return true;
    }


    /**
     * 查询全部
     */
    @GetMapping("/list1")
    public Object list1() {
        DynamicDataSourceContextHolder.setContextKey("master");
        SqlSessionTemplate bean = applicationContext.getBean(SqlSessionTemplate.class);
        List<Object> objects = bean.selectList("com.jimisun.mapper.TestUserMapper.selectList", null);
        System.out.println(objects);
        DynamicDataSourceContextHolder.removeContextKey();

        DynamicDataSourceContextHolder.setContextKey("slave");
        SqlSessionTemplate bean1 = applicationContext.getBean(SqlSessionTemplate.class);
        List<Object> bean1List = bean1.selectList("com.jimisun.mapper.TestUserMapper.selectList", null);
        System.out.println(bean1List);
        DynamicDataSourceContextHolder.removeContextKey();


        return true;
    }


    /**
     * 根据数据库连接信息获取表信息
     * http://localhost:8080/user/table?ip=1.15.229.65&port=3307&dbName=test&username=root&password=root
     */
    @GetMapping("table")
    public Object findWithDbInfo(DbInfo dbInfo) throws Exception {
        //数据源key
        String newDsKey = System.currentTimeMillis() + "";
        //添加数据源
        DataSourceUtil.addDataSourceToDynamic(newDsKey, dbInfo);
        DynamicDataSourceContextHolder.setContextKey(newDsKey);
        //查询表信息
        SqlSessionTemplate bean1 = applicationContext.getBean(SqlSessionTemplate.class);
        List<Object> bean1List = bean1.selectList("com.jimisun.mapper.TestUserMapper.selectList",
                null);
        System.out.println(bean1List);
        DynamicDataSourceContextHolder.removeContextKey();
        return "true";
    }


    /**
     * 测试事务
     *
     * @return
     */
    @RequestMapping("/testtm")
    public Object testtm() {
        testUserService.testTm();
        return "true";
    }


}
