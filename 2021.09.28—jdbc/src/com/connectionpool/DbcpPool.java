package com.connectionpool;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author ��껳�
 * @version 1.0
 */
public class DbcpPool {

    @Test
    public void testGetConncetion() throws Exception{
        Properties pros = new Properties();
        //ʹ�� ������� ������ ����dbcp.properties�ļ�
        //��ʽһ��
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
        pros.load(is);
        /**
         * ��ʽ����
         *FileInputStream is=new FileInputStream(new File("dbcp.properties"));
         * */
        DataSource source = BasicDataSourceFactory.createDataSource(pros);

        Connection con = source.getConnection();
        System.out.println(con);

        /**
         * ��JDBCUtils�е��Ż�������ÿ�ε��õ�ʱ������һ������
         * ��DBCP���ݿ�������������� Ȼ��  �����������ȡ���ʹ����ļ� �� ����̬�������ִֻ��һ��
         * */

    }
}
