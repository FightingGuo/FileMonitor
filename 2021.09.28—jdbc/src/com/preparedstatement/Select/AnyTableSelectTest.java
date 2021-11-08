package com.preparedstatement.Select;

import com.preparedstatement.JDBCutils;
import com.preparedstatement.Table.Select.Customer;
import com.preparedstatement.Table.Select.ExamStudent;
import com.preparedstatement.Table.Select.User;
import org.junit.Test;

import java.util.List;


/**
 * @author 郭昊晨
 * @version 1.0
 */
public class AnyTableSelectTest {
    @Test
    public void test(){
        String sql="select * from examstudent where FlowID=?";
        ExamStudent examStudent = JDBCutils.AnyTableSelect(ExamStudent.class, sql, 2);
        System.out.println(examStudent);
    }

    @Test
    public void test1(){
        String sql="select id,name,email,birth from customers where id=?";
        Customer customer = JDBCutils.AnyTableSelect(Customer.class, sql, 1);
        System.out.println(customer);
    }

    @Test
    //查询多行结果
    public void test3(){
        String sql="select id,name,password,address from user where id<?";
        List<User> list = JDBCutils.getListSelect(User.class, sql, 5);
        System.out.println(list);
//        list.forEach(System.out::println);
    }
}
