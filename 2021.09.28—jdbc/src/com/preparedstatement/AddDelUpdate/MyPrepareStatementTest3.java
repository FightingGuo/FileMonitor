package com.preparedstatement.AddDelUpdate;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author 郭昊晨
 * @version 1.0
 */
@SuppressWarnings("all")
public class MyPrepareStatementTest3 {

    //对customer表中的数据进行修改
    @Test
    public void update() {

        Connection con = null;
        PreparedStatement ps = null;
        try {
            //拿到实现类对象
            InputStream rs = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
            Properties properties = new Properties();
            properties.load(rs);
            //读取配置文件中的4个信息
            String user = properties.getProperty("user");
            String url = properties.getProperty("url");
            String password = properties.getProperty("password");
            String driver = properties.getProperty("driver");

            //加载类
            Class.forName(driver);

            //获取连接
            con = DriverManager.getConnection(url, user, password);


            //写sql语句
            String sql = "update customers set name=? where id=?";
            //把sql语句传入preparestatement
            ps = con.prepareStatement(sql);
            ps.setString(1, "郭昊晨");
            ps.setInt(2, 22);

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }
}
