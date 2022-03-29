package com.jimisun.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jimisun.dynamicdatasource.DS;
import com.jimisun.dynamicdatasource.DataSourceConstants;
import com.jimisun.entity.TestUser;
import com.jimisun.mapper.TestUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
