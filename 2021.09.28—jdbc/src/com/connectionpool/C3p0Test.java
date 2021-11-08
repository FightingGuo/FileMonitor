package com.connectionpool;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author 郭昊晨
 * @version 1.0
 */
public class C3p0Test {
    //方式一：
    @Test
    public void testGetConnection1() throws Exception{
        //获取c3p0连接池
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver"); //loads the jdbc driver
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        cpds.setUser("root");
        cpds.setPassword("442520");

        //通过设置相关参数，对数据库连接池进行管理
        //设置初始时数据库连接池的连接数
        cpds.setInitialPoolSize(10);
        Connection connection = cpds.getConnection();
        System.out.println(connection);
    }



}
