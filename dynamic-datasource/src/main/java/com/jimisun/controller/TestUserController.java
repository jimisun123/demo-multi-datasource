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
     * 注意 ： 如果在一个方法内调用另外一个本类标记有切换数据源的方法并且通过this.function（）则切换无效！！！！
     * 原因 ： 切换数据源是通过 SpringAOP 对对象增强，如果直接使用this调用说明就没有增强对象！！！
     * 解决 ： 通过本类注入再次注入自己，通过再次注入的对象调用方法，达到生成增强对象。
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
     * GOOD ： 使用编程方式切换数据源不会有上面this问题
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


    /**
     * 测试事务
     * 原因 ： @Transactional 会先于@DS （切换数据源） 执行，所以本类所有方法都会使用默认数据源的Connection和事务！！！！
     * 结论 ：使用AOP切换数据源后，标记@Transactional开启事务后，该事务内的方法仅会使用默认数据源的Connection管理事务
     *  1. 无法使用非默认数据源事务
     *  2. 无法在事务方法内切换数据源
     *
     *  使用注意事项 ： 事务管理器应当管理动态数据源，而非具体主/从数据源。否则事务无效。
     *  原因 ： @Transactional 的数据源是经过包装的，和主/从数据源不是同一个对象，所以事务无效！！！
     */
    @GetMapping("/testTransactional")
    public Object testTransactional() {
        testUserService.testTransactional();
        return true;
    }


}
