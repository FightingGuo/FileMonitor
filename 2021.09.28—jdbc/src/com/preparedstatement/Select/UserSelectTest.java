package com.preparedstatement.Select;

import com.preparedstatement.JDBCutils;
import com.preparedstatement.Table.Select.ExamStudent;
import com.preparedstatement.Table.Select.User;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author 郭昊晨
 * @version 1.0
 */
public class UserSelectTest {
    @Test
    public void test(){
        String sql="select * from user where id=?";
        User user = JDBCutils.userSelect(sql, 3);
        System.out.println(user);
    }
}
