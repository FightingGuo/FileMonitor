package com.connectionpool;

/**
 * @author ��껳�
 * @version 1.0
 */


import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;


public class C3p0Poll {
    //��ʽ���������ݿ����ӳ� ʹ�������ļ� .xml
    //c3p0���ݿ����ӳ�
    //���ݿ��ֻ��Ҫ��һ������ʡ��Դ
    private static ComboPooledDataSource cpds = new ComboPooledDataSource("c3p0");
    public static Connection getConnection() throws Exception{
        Connection con = cpds.getConnection();
        return con;
    }
}
