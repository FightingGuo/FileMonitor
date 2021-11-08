package com.connectionpool;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author 郭昊晨
 * @version 1.0
 */

/**
 * ***********连接方式一***********
 * */
public class DbcpTest {

    @Test
    public void getConnection() throws Exception{
        //创建了DBCP数据库连接池
        BasicDataSource source = new BasicDataSource();

        //设置基本信息
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/test");
        source.setPassword("442520");
        source.setUsername("root");

        //设置其他涉及数据库连接池管理的相关属性
        source.setInitialSize(10);
        source.setMaxIdle(10);


        Connection connection = source.getConnection();
        System.out.println(source);
    }


    //使用配置文件
    @Test
    public void testGetConncction(){

    }
}
