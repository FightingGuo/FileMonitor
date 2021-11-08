package com.dao.customer.customer1;

import com.preparedstatement.JDBCutils;
import com.preparedstatement.Table.Select.Customer;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @author 郭昊晨
 * @version 1.0
 */
public class CustomerDAOImplTest {
    CustomerDAOImpl customerDAO=new CustomerDAOImpl();

    @Test
    public void testInsert() {
        Connection con = null;
        try {
            con = JDBCutils.getConnection();
            Customer cust = new Customer(1, "yhj", "1552637742", new Date(2000 - 8 - 24));
            customerDAO.insert(con, cust);
            System.out.println("添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCutils.CloseResource(con);
        }
    }

    @Test
    public void testDelete() {
        Connection con = null;
        try {
            con = JDBCutils.getConnection();
            customerDAO.delete(con,23);
            System.out.println("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCutils.CloseResource(con);
        }
    }

    @Test
    public void testUpdate() {
        Connection con = null;
        try {
            con = JDBCutils.getConnection();
            Customer customer = new Customer(29,"yhj","1552637742@qq.com",new Date(2000-8-24));
            customerDAO.update(con,customer);
            System.out.println("更新成功！");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCutils.CloseResource(con);
        }
    }

    @Test
    public void testSelectCustomer() {
        Connection con = null;
        try {
            con = JDBCutils.getConnection();
            Customer customer = customerDAO.SelectCustomer(con, 29);
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCutils.CloseResource(con);
        }
    }

    @Test
        public void testGetAll() {
        Connection con = null;
        try {
            con = JDBCutils.getConnection();
            List<Customer> list = customerDAO.getAll(con);
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCutils.CloseResource(con);
        }
    }

    @Test
    public void getCount() {
        Connection con = null;
        try {
            con = JDBCutils.getConnection();
            Long count = customerDAO.getCount(con);
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCutils.CloseResource(con);
        }
    }

}
