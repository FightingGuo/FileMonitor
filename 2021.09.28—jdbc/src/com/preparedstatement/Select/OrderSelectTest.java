package com.preparedstatement.Select;

import com.preparedstatement.JDBCutils;
import com.preparedstatement.Table.Select.Order;
import org.junit.Test;

/**
 * @author 郭昊晨
 * @version 1.0
 */
/**
 *针对于表的字段名与类的属性名不相同的情况：
 * 1.必须声明sql时，使用类的属性名来命名字段的别名
 * 2.使用ResultSetMetDate时，需要使用getColumnLabel()来替换getColumnName(),获取列的别名
 *
 * 说明：如果sql中没有给字段起别名，getColumnLabel()获取的就是列名
 * */


//java.sql.SQLException: Parameter index out of range (1 > number of parameters, which is 0).
public class OrderSelectTest {
    @Test
    public void test(){
        String sql="select order_id orderID,order_name orderName,order_date orderDate from `order` WHERE order_id=?";
        Order order = JDBCutils.orderSelect(sql, 1);
        System.out.println(order);
    }
}
