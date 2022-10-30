package com.jimisun.service;

import com.jimisun.entity.TestUser;

import java.util.List;

public interface TestUserService {

    public void testTransactional();

    public void testTransactional2();

    public List<TestUser> getSlaveUserList() ;


    public List<TestUser> getMasterUserList() ;

    public List<TestUser> getDefaultUserList();
}
