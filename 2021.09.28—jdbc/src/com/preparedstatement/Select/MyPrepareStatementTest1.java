package com.preparedstatement.Select;

/**
 * @author 郭昊晨
 * @version 1.0
 */

import com.preparedstatement.Table.Select.Customer;
import com.preparedstatement.JDBCutils;
import org.junit.Test;

import java.sql.*;

/**
 * ------对Customers表的查询操作------
 * */
public class MyPrepareStatementTest1 {

    @Test
    public void test1() {
        Connection con1 = null;
        PreparedStatement ps= null;
        ResultSet resultSet = null;
        try {
            con1 = JDBCutils.getConnection();
            String sql="select * from customers where id=?";
            ps = con1.prepareStatement(sql);
            ps.setObject(1,4);

            //执行，并返回结果集
            resultSet = ps.executeQuery();
            //处理结果集
            if (resultSet.next()){//判断结果集的下一条是否有数据，如果有数据返回true，并下移指针，如果没有就返回false
                //获取当前数据的各个字段的值
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);

                //取出数据
                //方式一：
    //            Object obj[]=new Object[]{id,name,email,birth};
                //方式二：将数据封装成一个对象
                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //关闭资源
            JDBCutils.CloseResource(con1,ps,resultSet);
        }

    }



}
