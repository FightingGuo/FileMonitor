package com.dbutils;

/**
 * @author ��껳�
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
 * dbutils��Apache��֯�ṩ��һ����ԴJDBC������⣬������ݿ����ɾ�Ĳ�
 * */

public class QueryRunnerTest {

    //��ɾ��
    @Test
    public void test1()  {
        Connection con = null;
        try {
            QueryRunner queryRunner=new QueryRunner();
            con = JDBCUtils.getConnection3();
            String sql="insert into customers(name,email,birth)values(?,?,?)";
            int insertCount = queryRunner.update(con, sql, "������", "au123456@qq.com", "2000-3-21");
            System.out.println("�����"+insertCount+"����¼");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCutils.CloseResource(con);
        }

    }


    //���Բ�ѯ
    @Test
    public void test2()  {
        //
        Connection con=null;
        try {
            QueryRunner runner=new QueryRunner();
             con = JDBCUtils.getConnection3();
            String sql="select id,name,email,birth from customers where id=? ";
            //BeanHandler����ResultSetHandler�ӿڵ�ʵ���࣬���ڷ�װ���е�����
            BeanHandler<Customer> beanHandler=new BeanHandler<>(Customer.class);
            Customer customer = runner.query(con, sql, beanHandler, 28);
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCutils.CloseResource(con);
        }

    }

    //��ѯ������¼
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
    //SclarHandler�����ڴ�����ֵ


}
