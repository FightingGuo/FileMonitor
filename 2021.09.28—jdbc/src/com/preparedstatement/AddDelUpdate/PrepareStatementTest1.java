package com.preparedstatement.AddDelUpdate;

/**
 * @author 郭昊晨
 * @version 1.0
 */

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * 使用preparedstatement替换statement实现对数据表增删改
 * */
@SuppressWarnings("all")
public class PrepareStatementTest1 {

    //向customers表中添加一条数据
    @Test
    public void insert() {
        //连接数据库
        //1.读取配置文件
        Connection connection1 = null;
        PreparedStatement ps = null;
        try {
            //PrepareStatementTest.class.getClassLoader()获取一个类的加载器
            InputStream resourceAsStream = PrepareStatementTest1.class.getClassLoader().getResourceAsStream("jdbc.properties");
            Properties properties=new Properties();
            properties.load(resourceAsStream);

            String user=properties.getProperty("user");
            String drriver=properties.getProperty("driver");
            String password=properties.getProperty("password");
            String url=properties.getProperty("url");

            //2.加载类
            Class.forName(drriver);

            //3.获取连接
            connection1 = DriverManager.getConnection(url,user,password);


            //4.预编译sql语句，返回PreparedStatement实例
            String sql="insert into customers(name,email,birth)values(?,?,?)";//?占位符
            ps = connection1.prepareStatement(sql);
            //填充占位符 parameterindex:索引  和数据库交互的索引都是从1开始
            ps.setString(1,"郭昊晨");
            ps.setString(2,"1078285136@qq.com");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse("2001-04-18");//解析具体的日期
            ps.setDate(3,new java.sql.Date(date.getTime()));

            //6.执行操作
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //7.资源的关闭
        finally {
            try {
                if (ps!=null) //避免出现空指针
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                if (connection1!=null) //避免出现空指针
                connection1.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


        //再把前6步的内容try-catch 扔掉throws  7资源关闭也需要try-catch
    }
}
