package com.connection;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author 郭昊晨
 * @version 1.0
 */
@SuppressWarnings("all")
public class ConnectionTest {
    //方式一
    @Test
    public void testConnection1() throws SQLException {
        //获取driver的实现类对象
        Driver driver=new com.mysql.jdbc.Driver(); //通过sql包里的接口调驱动包里的Driver实现类对象
        //jdbc:mysql:协议
        //localhost:ip地址
        //test是数据库
        String url = "jdbc:mysql://localhost:3306/test";
        //将用户名和密码封装在Properties中
        Properties properties = new Properties();
        properties.setProperty("user","root");
        properties.setProperty("passward","442520");

        Connection connect = driver.connect(url, properties);

        System.out.println(connect);
    }

    //方式二：对方式一的迭代
    @Test
    public void testConnection2() throws Exception{
        //1.获取实现类对象:用反射来实现
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");

        Driver driver = (Driver) aClass.newInstance();
        //2.提供要连接的数据库
        String url="jdbc:mysql://localhost:3306/test";

        //提供需要连接的用户名和密码
        Properties properties=new Properties();
        properties.setProperty("user","root");
        properties.setProperty("passward","442520");
        //4.获取连接
        Connection connection = driver.connect(url,properties);
        System.out.println(connection);
    }

    //方式三:使用DriverManger替换Driver
    @Test
    public void testConnection3() throws Exception{
        //获取实现类对象
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver =(Driver)aClass.newInstance();

        String url="jdbc:mysql://localhost:3306/test";
        String user="root";
        String password="442520";

        //注册驱动
        DriverManager.registerDriver(driver);

        //获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

    //方式四：可以只是加载驱动，不用注册驱动
    @Test
    public void testConnection4() throws Exception{
        //1.提供3个基本信息
        String url="jdbc:mysql://localhost:3306/test";
        String user="root";
        String password="442520";

        //2.获取实现类对象
         Class.forName("com.mysql.jdbc.Driver");
         //优化：Driver的静态代码块在类加载的时候就创建了对象并实现注册
//        Driver driver =(Driver)aClass.newInstance();
//        //注册驱动
//        DriverManager.registerDriver(driver);

        //3.获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

    //方式五：将数据库连接需要的4个基本信息声明在配置文件中通过读取配置文件的方式获取连接
    @Test
    public void testConnection5() throws Exception{
        //实现了数据和代码的分离。实现了解耦
        //读取配置文件中的4个基本信息
        InputStream resourceAsStream = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties=new Properties();
        properties.load(resourceAsStream);

        String user=properties.getProperty("user");
        String password=properties.getProperty("password");
        String url=properties.getProperty("url");
        String driver=properties.getProperty("driver");

        //加载驱动
        Class.forName(driver);

        //获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);

    }
}
