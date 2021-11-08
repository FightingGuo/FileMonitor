package com.preparedstatement.AddDelUpdate;

import com.preparedstatement.JDBCutils;
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
public class MyPrepareStatementTest2 {

    //删除customer表中的一条数据
    @Test
    public void delete() {
        //ClassLoader.getSystemClassLoader()获取类加载器

        Connection con = JDBCutils.getConnection();

        String sql="delete from customers where id=22";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();

            JDBCutils.CloseResource(con,ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
