package com.preparedstatement.AddDelUpdate;

import com.preparedstatement.JDBCutils;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author 郭昊晨
 * @version 1.0
 */

//-----------终极优化版测试----------

public class MyprepareStatementTest4 {
    public static void main(String[] args) {
//     Connection con1=JDBCutils.getConnection();
//     JDBCutils.update(con1,"郭大侠",23);
//
//     JDBCutils.CloseResource(con1);

//     Connection con2=JDBCutils.getConnection();
//     JDBCutils.insert(con2,"郭郭","198005978220","1999-4-18");
//     JDBCutils.CloseResource(con2);

//     Connection con3=JDBCutils.getConnection();
//     JDBCutils.delete(con3,25);
//     JDBCutils.CloseResource(con3);

     //测试三合一
     Connection con4= JDBCutils.getConnection();
     String sql="update customers set name=?,email=?,birth=? where id=24";
     JDBCutils.updateall(sql,"郭郭G","19805978220@qq.com","1968-11-20");
     JDBCutils.CloseResource(con4);

    }

    @Test
    public void test(){
        String sql="delete from customers where id=?";
        JDBCutils.updateall(sql,29);
    }

    @Test
    public void test2() {
        Connection con5 = JDBCutils.getConnection();
        JDBCutils.insert(con5,4,"许嵩","12345678@qq.com","1981-05-15");
        JDBCutils.CloseResource(con5);
    }
}
