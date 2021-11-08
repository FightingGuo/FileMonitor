package com.connectionpool;

/**
 * @author 郭昊晨
 * @version 1.0
 */


import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;


public class C3p0Poll {
    //方式二：用数据库连接池 使用配置文件 .xml
    //c3p0数据库连接池
    //数据库池只需要造一个，节省资源
    private static ComboPooledDataSource cpds = new ComboPooledDataSource("c3p0");
    public static Connection getConnection() throws Exception{
        Connection con = cpds.getConnection();
        return con;
    }
}
