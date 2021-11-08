package com.StatementTest;

import com.preparedstatement.JDBCutils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author 郭昊晨
 * @version 1.0
 */
public class Test {
    @org.junit.Test
    public void test1(){
        Scanner scanner=new Scanner(System.in);

        System.out.println("请输入用户名:");
        String name=scanner.next();
        System.out.println("请输入邮箱:");
        String email=scanner.next();
        System.out.println("请输入生日:");
        String birth=scanner.next();

        String sql="insert into customers(name,email,birth) values(?,?,?)";
        int insertcount = updateall(sql, name, email, birth);
        if (insertcount>0){
            System.out.println("添加成功");
        }else {
            System.out.println("添加失败");
        }

    }

    public  int updateall(String sql, Object... args) {
        //获取数据库连接
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = JDBCutils.getConnection();
            //预编译sql语句,返回PrepareStatement
            ps = con.prepareStatement(sql);
            //填充占位符  长度args.length
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            return ps.executeUpdate();//返回一个int型，如果是增删改返回1，查询返回0

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCutils.CloseResource(con, ps);
        }

        return 0;
    }
}
