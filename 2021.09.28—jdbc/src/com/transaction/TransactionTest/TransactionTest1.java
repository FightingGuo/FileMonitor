package com.transaction.TransactionTest;

import com.preparedstatement.JDBCutils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

import static com.transaction.JDBCutils.getConnection;


/**
 * @author 郭昊晨
 * @version 1.0
 */
public class TransactionTest1 {

    //----version1.0
    //***********未考虑到事务的转账操作***********
    public  void updateall(String sql, Object... args) { //sql语句中占位符的个数相当于参数的个数
        //获取数据库连接
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = getConnection();
            //预编译sql语句,返回PrepareStatement
            ps = con.prepareStatement(sql);
            //填充占位符  长度args.length
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCutils.CloseResource(con, ps);
        }

    }

    /**
     * 针对于数据表user_table
     * AA用户给BB用户转账100
     * update user_table set balance=balance-100 where user='AA';
     * update user_table set balance=balance+100 where user='BB';
     *
     * 1、数据一旦提交，就不可回滚
     *
     * 2、那些操作会导致数据自动提交？
     *          >DDL操作一旦执行，都会自动提交
     *              >set autocommit=0/false对DDL操作是失效的
     *          >DML默认情况下，一旦执行，就会自动提交
     *              >但是可以通过set autocommit=0/false的方式取消DML操作的提交
     *          >默认在关闭连接时会自动提交数据
     * */


    //*********未考虑数据库事务的转账操作*********
    @Test
    public void testUpdateAll(){
        String sql1="update user_table set balance=balance-100 where user=?";
        updateall(sql1,"AA");

        //模拟异常
        System.out.println(10/0);

        String sql2="update user_table set balance=balance+100 where user=?";
        updateall(sql2,"BB");

        System.out.println("转账成功");
    }


    //*********考虑数据库事务的转账操作*******
    //----version2.0
    public void UpdateAllTransaction(Connection con,String sql, Object... args){
            PreparedStatement ps = null;
            try {
                //1.预编译sql语句,返回PrepareStatement
                ps = con.prepareStatement(sql);
                //2.填充占位符  长度args.length
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i]);
                }
                ps.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                JDBCutils.CloseResource(null, ps);
            }


    }

    //*************考虑事务后的测试***********
    @Test
    public void testUpdateAll1(){
        Connection con = null;
        try {
            con = JDBCutils.getConnection();
            //取消数据的自动提交功能
            con.setAutoCommit(false);

            String sql1="update user_table set balance=balance-100 where user=?";
            UpdateAllTransaction(con,sql1,"AA");

            System.out.println(10/0);

            String sql2="update user_table set balance=balance+100 where user=?";
            UpdateAllTransaction(con,sql2,"BB");

            //完成事务后，手动提交数据
            con.commit();

        } catch (Exception e) {
            e.printStackTrace();
            //如果出现异常了，就需要回滚数据
            try {
                con.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }finally {
            //修改其为自动提交数据
            //主要针对于使用数据库连接池的使用
            try {
                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            JDBCutils.CloseResource(con);
        }
        System.out.println("转账成功");
    }

}
