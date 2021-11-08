package com.StatementTest;

import com.preparedstatement.JDBCutils;
import com.preparedstatement.Table.Select.User;
import org.junit.Test;

import java.util.Scanner;

/**
 * @author 郭昊晨
 * @version 1.0
 */

/**prepareStatement除了解决Statement拼串、sql注入问题之外还有那些好处
 * 1.PrepareStatement操作bolb的数据，Statement不行
 * 2.PrepareStatement可以实现更高效的批量操作
 */

public class PrepareStatementTest {
    //使用prepareStatement对数据表操作
    @Test
   public void test(){
       Scanner scanner=new Scanner(System.in);

       System.out.println("请输入用户名:");
       String user=scanner.nextLine();
       System.out.println("请输入用户名:");
       String password=scanner.nextLine();

       // SELECT user,password FROM user_table WHERE USER = '1' or ' AND PASSWORD = '
       // ='1' or '1' = '1';

       //对User表查询
       String sql="select * from user where user=? and password=?";

       User user1 = JDBCutils.userSelect(sql, user, password);
       if (user1!=null) {
           System.out.println("登录成功");
       }else {
           System.out.println("用户名或密码错误");
       }
   }

}
