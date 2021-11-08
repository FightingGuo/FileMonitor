package com.connectionpool;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author ��껳�
 * @version 1.0
 */
public class C3p0Test {
    //��ʽһ��
    @Test
    public void testGetConnection1() throws Exception{
        //��ȡc3p0���ӳ�
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver"); //loads the jdbc driver
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        cpds.setUser("root");
        cpds.setPassword("442520");

        //ͨ��������ز����������ݿ����ӳؽ��й���
        //���ó�ʼʱ���ݿ����ӳص�������
        cpds.setInitialPoolSize(10);
        Connection connection = cpds.getConnection();
        System.out.println(connection);
    }



}
