package com.jimisun.parametricdatasource;

import lombok.Data;

/**
 * @author jimisun
 * @create 2022-04-23 10:41
 **/


@Data
public class DbInfo {

    private String ip;
    private String port;
    private String dbName;
    private String driveClassName;
    private String username;
    private String password;

}
