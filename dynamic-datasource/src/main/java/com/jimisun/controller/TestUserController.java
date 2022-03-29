package com.jimisun.controller;

import com.jimisun.service.TestUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

}
