package com.jimisun.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jimisun.dynamicdatasource.DS;
import com.jimisun.dynamicdatasource.DataSourceConstants;
import com.jimisun.entity.TestUser;
import com.jimisun.mapper.TestUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TestUserServiceImpl implements TestUserService{
    @Autowired
    private TestUserMapper testUserMapper;

    @Autowired
    private TestUserService testUserService;


    @DS(DataSourceConstants.DS_KEY_SLAVE)
    @Override
    @Transactional
    public void testTransactional(){
        TestUser testUser = new TestUser();
        testUser.setName("two");
        testUserMapper.update(testUser,null);
        testUserService.testTransactional2();
        //int i = 1/0;
    }

    @DS(DataSourceConstants.DS_KEY_MASTER)
    @Override
    public void testTransactional2(){
        TestUser testUser = new TestUser();
        testUser.setName("two");
        testUserMapper.update(testUser,null);
        //int i = 1/0;
    }

    @DS(DataSourceConstants.DS_KEY_SLAVE)
    @Override
    public List<TestUser> getSlaveUserList() {
        QueryWrapper<TestUser> queryWrapper = new QueryWrapper<>();
        List<TestUser> resultData = testUserMapper.selectList(queryWrapper.isNotNull("name"));
        return resultData;

    }

    @DS(DataSourceConstants.DS_KEY_MASTER)
    @Override
    public List<TestUser> getMasterUserList() {
        QueryWrapper<TestUser> queryWrapper = new QueryWrapper<>();
        List<TestUser> resultData = testUserMapper.selectList(queryWrapper.isNotNull("name"));
        return resultData;

    }

    @Override
    public List<TestUser> getDefaultUserList() {
        QueryWrapper<TestUser> queryWrapper = new QueryWrapper<>();
        List<TestUser> resultData = testUserMapper.selectList(queryWrapper.isNotNull("name"));
        return resultData;

    }
}
