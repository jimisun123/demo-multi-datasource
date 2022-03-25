package com.jimisun.simple.basicmultidatasource.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jimisun.simple.basicmultidatasource.entity.master.SlaveUser;
import com.jimisun.simple.basicmultidatasource.entity.slave.MasterUser;
import com.jimisun.simple.basicmultidatasource.mapper.master.MasterMapper;
import com.jimisun.simple.basicmultidatasource.mapper.slave.SlaveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试控制器
 *
 * @author jimisun
 * @create 2022-03-25 6:13 PM
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private MasterMapper masterMapper;

    @Autowired
    private SlaveMapper slaveMapper;

    /**
     * 查询
     */
    @GetMapping("/find")
    public Object find(int id) {
        SlaveUser testUser = slaveMapper.selectOne(new QueryWrapper<SlaveUser>().eq("id" , id));
        return testUser;
    }

    /**
     * 查询全部
     */
    @GetMapping("/listall")
    public Object listAll() {
        //自定义接口查询
        QueryWrapper<MasterUser> queryWrapper = new QueryWrapper<>();
        List<MasterUser> resultData = masterMapper.selectList(queryWrapper.isNotNull("name"));
        //mp内置接口
        List<SlaveUser> resultDataSlave = slaveMapper.selectList(null);
        int initSize = 30;
        Map<String, Object> result = new HashMap<>(initSize);
        result.put("master" , resultData);
        result.put("slave" , resultDataSlave);

        return result;
    }

}
