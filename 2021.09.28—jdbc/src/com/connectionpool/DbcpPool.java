package com.connectionpool;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author 郭昊晨
 * @version 1.0
 */
public class DbcpPool {

    @Test
    public void testGetConncetion() throws Exception{
        Properties pros = new Properties();
        //使用 类加载器 生成流 加载dbcp.properties文件
        //方式一：
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
        pros.load(is);
        /**
         * 方式二：
         *FileInputStream is=new FileInputStream(new File("dbcp.properties"));
         * */
        DataSource source = BasicDataSourceFactory.createDataSource(pros);

        Connection con = source.getConnection();
        System.out.println(con);

        /**
         * 在JDBCUtils中的优化：不再每次调用的时候都生成一个池子
         * 把DBCP数据库池在外面先声明 然后  把类加载器获取流和传入文件 放 到静态代码块中只执行一次
         * */

    }
}
