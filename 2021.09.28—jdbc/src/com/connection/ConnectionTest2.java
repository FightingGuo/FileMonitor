package com.connection;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @author 郭昊晨
 * @version 1.0
 */
public class ConnectionTest2 {

    /**
     *获取连接
     * */
    @Test
    public void test() throws Exception{
        //读取配置文件
        InputStream resourceAsStream = ConnectionTest2.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties=new Properties();
        properties.load(resourceAsStream);


        String url=properties.getProperty("url");
        String driver=properties.getProperty("driver");
        String password=properties.getProperty("password");
        String user=properties.getProperty("user");

        //加载类
        Class.forName(driver);

        //获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }
}
