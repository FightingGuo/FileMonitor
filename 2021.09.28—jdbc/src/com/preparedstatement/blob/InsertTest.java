package com.preparedstatement.blob;

/**
 * @author 郭昊晨
 * @version 1.0
 */

import com.preparedstatement.JDBCutils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;


/**
 * ============使用PrepareStatement实现批量插入数据============
 * 向datas表中插入5000行数据
 * <p>
 * 方式一：
 * Statement
 * Connection con=JDBCutils.getConnection();
 * Statement st=con.CreatStatement()
 * for(int i=0;i<=5000;i++){
 * String sql="insert into datas(name)values('name+"+i+"')";
 * st.execute(sql);
 * }
 */

public class InsertTest {
    @Test
    public void test() {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = JDBCutils.getConnection();
            String sql = "insert into datas(name)values(?)";
            ps = con.prepareStatement(sql);

            long start = System.currentTimeMillis();
            for (int i = 0; i < 5000; i++) {
                ps.setString(1, "name_" + i);
                ps.execute();
            }
            long end = System.currentTimeMillis();
            System.out.println("花费的时间为" + (end - start) + "毫秒");//5000条数据花费15.256s
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCutils.CloseResource(con, ps);
        }

    }

    //优化二：批量插入方式三

    /**
     * mysql服务器默认是关闭批处理的，我们需要通过以个参数，让mysql开启批处理的支持。
     * ？rewriteBatchedStatements=true写在配置文件url后面
     */
    @Test
    public void test2() {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            con = JDBCutils.getConnection();
            String sql = "insert into datas(name)values(?)";
            ps = con.prepareStatement(sql);
            /**
             * 1.addBatch、executeBatch、clearBtach()
             * */

            for (int i = 1; i <=10000; i++) {
                ps.setString(1, "name_" + i);
                //1.攒sql
                ps.addBatch();

                if (i % 500 == 0) {
                    //2.执行batch
                    ps.executeBatch();
                    //3.清空batch
                    ps.clearBatch();
                }


            }
            long end = System.currentTimeMillis();
            System.out.println("花费的时间为" + (end - start) + "毫秒");//10000条数据花费918ms
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCutils.CloseResource(con, ps);
        }
    }

    //插入方式四:设置连接不允许自动提交
    @Test
    public void test4() {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            con = JDBCutils.getConnection();
            String sql = "insert into datas(name)values(?)";
            ps = con.prepareStatement(sql);
            /**
             * 1.addBatch、executeBatch、clearBtach()
             * */

            //设置不允许自动提交数据
            con.setAutoCommit(false);

            for (int i = 1; i <=1000000; i++) {
                ps.setString(1, "name_" + i);
                //1.攒sql
                ps.addBatch();

                if (i % 500 == 0) {
                    //2.执行batch
                    ps.executeBatch();
                    //3.清空batch
                    ps.clearBatch();
                }
            }

            //提交
            con.commit();
            long end = System.currentTimeMillis();
            System.out.println("花费的时间为" + (end - start) + "毫秒");//1000000条数据为12.375s
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCutils.CloseResource(con, ps);
        }
    }


}
