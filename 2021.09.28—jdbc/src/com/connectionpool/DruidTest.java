package com.connectionpool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author ¹ùê»³¿
 * @version 1.0
 */
public class DruidTest {

    @Test
    public void getConnection() throws Exception{

            Properties pros=new Properties();
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            pros.load(is);

            DataSource dataSource = DruidDataSourceFactory.createDataSource(pros);
            Connection con = dataSource.getConnection();
            System.out.println(con);

        }

}
