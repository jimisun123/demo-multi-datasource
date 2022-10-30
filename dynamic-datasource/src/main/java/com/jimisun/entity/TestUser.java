package com.jimisun.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 主库user表实体
 *
 * @author jimisun
 * @create 2022-03-25 6:05 PM
 **/
@Data
@TableName("user")
public class TestUser {
    /** id */
    private Long id;
    /** 姓名 */
    private String name;
    /** 手机号 */
    private String password;

}
