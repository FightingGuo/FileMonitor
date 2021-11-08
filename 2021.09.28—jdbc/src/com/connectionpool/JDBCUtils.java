package com.connectionpool;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;


import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author ��껳�
 * @version 1.0
 */

/**
 * ʹ�����ӳػ�ȡ����  �� com.preparedstatement.JDBCUtils���Ż�
 * */
@SuppressWarnings("all")
public class JDBCUtils {
        //c3p0���ݿ����ӳ�   ʹ�������ļ� .xml
        //���ݿ��ֻ��Ҫ��һ������ʡ��Դ
        private static ComboPooledDataSource cpds = new ComboPooledDataSource("c3p0");
        public static Connection getConnection1() throws Exception{
            Connection con = cpds.getConnection();
            return con;
        }


        /**
         * ʹ��DBCP���ݿ����ӳ�
         * ����д�������ļ�
         * */
        //����һ��DBCP���ݿ����ӳ�
        private static DataSource source;
         static {
             try {
                 Properties pros = new Properties();
                 //ʹ�� ������� ������ ����dbcp.properties�ļ�
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
     * ʹ��druid���ݿ����ӳ�
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
