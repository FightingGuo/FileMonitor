package com.dbutils;

/**
 * @author 郭昊晨
 * @version 1.0
 */

import com.connectionpool.JDBCUtils;
import com.preparedstatement.JDBCutils;
import com.preparedstatement.Table.Select.Customer;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

/**
 * dbutils是Apache组织提供的一个开源JDBC工具类库，针对数据库的增删改查
 * */

public class QueryRunnerTest {

    //增删改
    @Test
    public void test1()  {
        Connection con = null;
        try {
            QueryRunner queryRunner=new QueryRunner();
            con = JDBCUtils.getConnection3();
            String sql="insert into customers(name,email,birth)values(?,?,?)";
            int insertCount = queryRunner.update(con, sql, "男明星", "au123456@qq.com", "2000-3-21");
            System.out.println("添加了"+insertCount+"条记录");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCutils.CloseResource(con);
        }

    }


    //测试查询
    @Test
    public void test2()  {
        //
        Connection con=null;
        try {
            QueryRunner runner=new QueryRunner();
             con = JDBCUtils.getConnection3();
            String sql="select id,name,email,birth from customers where id=? ";
            //BeanHandler：是ResultSetHandler接口的实现类，用于封装表中的数据
            BeanHandler<Customer> beanHandler=new BeanHandler<>(Customer.class);
            Customer customer = runner.query(con, sql, beanHandler, 28);
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCutils.CloseResource(con);
        }

    }

    //查询多条记录
    @Test
    public void test3() {
        Connection con = null;
        try {
            con = null;
            QueryRunner runner = new QueryRunner();
            con = JDBCUtils.getConnection3();
            String sql = "select id,name,email,birth from customers where id<? ";

            BeanListHandler<Customer> beanHandler = new BeanListHandler<>(Customer.class);
            List<Customer> list = runner.query(con, sql, beanHandler, 10);
            list.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCutils.CloseResource(con);
        }

    }
    //SclarHandler：用于存特殊值


}
