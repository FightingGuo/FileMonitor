package com.preparedstatement.Select;

import com.preparedstatement.JDBCutils;
import com.preparedstatement.Table.Select.Customer;
import com.preparedstatement.Table.Select.User;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author 郭昊晨
 * @version 1.0
 */

//--------测试customers表通用查询---------
public class CustomerSelectTest {
    @Test
    public void test2(){
        String sql="select id,name,birth,email from customers where id=?";
        Customer customer = JDBCutils.customerSelect(sql, 2);
        System.out.println(customer);
    }
}
