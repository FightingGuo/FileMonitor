package com.connectionpool;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;


import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author 郭昊晨
 * @version 1.0
 */

/**
 * 使用连接池获取连接  对 com.preparedstatement.JDBCUtils的优化
 * */
@SuppressWarnings("all")
public class JDBCUtils {
        //c3p0数据库连接池   使用配置文件 .xml
        //数据库池只需要造一个，节省资源
        private static ComboPooledDataSource cpds = new ComboPooledDataSource("c3p0");
        public static Connection getConnection1() throws Exception{
            Connection con = cpds.getConnection();
            return con;
        }


        /**
         * 使用DBCP数据库连接池
         * 首先写好配置文件
         * */
        //创建一个DBCP数据库连接池
        private static DataSource source;
         static {
             try {
                 Properties pros = new Properties();
                 //使用 类加载器 生成流 加载dbcp.properties文件
                 InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
                 pros.load(is);
                 source = BasicDataSourceFactory.createDataSource(pros);
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
    public static Connection getConncetion2() throws Exception {

        Connection con = source.getConnection();
        return con;
    }

    /**
     * 使用druid数据库连接池
     * */
    private static DataSource dataSource;
    static {
        try {
            Properties pros=new Properties();
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            pros.load(is);
            dataSource = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public  static Connection getConnection3() throws Exception{

        Connection con = dataSource.getConnection();
        return con;

    }

}
