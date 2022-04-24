package com.jimisun.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jimisun.entity.TestUser;
import com.jimisun.mapper.TestUserMapper;
import com.jimisun.parametricdatasource.DS;
import com.jimisun.parametricdatasource.DataSourceConstants;
import com.jimisun.parametricdatasource.DynamicDataSourceContextHolder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 测试用户service层
 *
 * @author jimisun
 * @create 2022-03-29 4:41 PM
 **/
@Service
public class TestUserService {
    @Autowired
    private TestUserMapper testUserMapper;

    @Autowired
    private ApplicationContext applicationContext;


    @Autowired
    private SqlSessionFactory sqlSessionFactory;


    @Transactional
    public Boolean testTm() {

        DynamicDataSourceContextHolder.setContextKey("slave");
        TestUser testUser2 = new TestUser();
        testUser2.setName("hello");
        testUser2.setId(11122L);
        testUserMapper.insert(testUser2);


        DynamicDataSourceContextHolder.setContextKey("master");
        TestUser testUser = new TestUser();
        testUser.setName("hello");
        testUser.setId(11122L);
        testUserMapper.insert(testUser);
        DynamicDataSourceContextHolder.removeContextKey();


        return true;
    }


    @DS(DataSourceConstants.DS_KEY_SLAVE)
    public List<TestUser> getSlaveUserList() {
        QueryWrapper<TestUser> queryWrapper = new QueryWrapper<>();
        List<TestUser> resultData = testUserMapper.selectList(queryWrapper.isNotNull("name"));
        return resultData;

    }

    @DS(DataSourceConstants.DS_KEY_MASTER)
    public List<TestUser> getMasterUserList() {
        QueryWrapper<TestUser> queryWrapper = new QueryWrapper<>();
        List<TestUser> resultData = testUserMapper.selectList(queryWrapper.isNotNull("name"));
        return resultData;

    }

    public List<TestUser> getDefaultUserList() {
        QueryWrapper<TestUser> queryWrapper = new QueryWrapper<>();
        List<TestUser> resultData = testUserMapper.selectList(queryWrapper.isNotNull("name"));
        return resultData;

    }
}
