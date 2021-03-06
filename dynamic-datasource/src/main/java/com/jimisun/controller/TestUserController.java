package com.jimisun.controller;

import com.jimisun.dynamicdatasource.DynamicDataSourceContextHolder;
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
     * 在控制层使用
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
     * 在一个方法中使用
     */
    @GetMapping("/list1")
    public Object list1() {
        DynamicDataSourceContextHolder.setContextKey("master");
        SqlSessionTemplate bean = applicationContext.getBean(SqlSessionTemplate.class);
        List<Object> objects = bean.selectList("com.jimisun.mapper.TestUserMapper.selectList", null);
        System.out.println(objects);

        DynamicDataSourceContextHolder.setContextKey("slave");
        SqlSessionTemplate bean1 = applicationContext.getBean(SqlSessionTemplate.class);
        List<Object> bean1List = bean1.selectList("com.jimisun.mapper.TestUserMapper.selectList", null);
        System.out.println(bean1List);

        return true;
    }

}
