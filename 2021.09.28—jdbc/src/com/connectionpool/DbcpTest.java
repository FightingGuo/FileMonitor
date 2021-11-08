package com.connectionpool;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author ��껳�
 * @version 1.0
 */

/**
 * ***********���ӷ�ʽһ***********
 * */
public class DbcpTest {

    @Test
    public void getConnection() throws Exception{
        //������DBCP���ݿ����ӳ�
        BasicDataSource source = new BasicDataSource();

        //���û�����Ϣ
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/test");
        source.setPassword("442520");
        source.setUsername("root");

        //���������漰���ݿ����ӳع�����������
        source.setInitialSize(10);
        source.setMaxIdle(10);


        Connection connection = source.getConnection();
        System.out.println(source);
    }


    //ʹ�������ļ�
    @Test
    public void testGetConncction(){

    }
}
