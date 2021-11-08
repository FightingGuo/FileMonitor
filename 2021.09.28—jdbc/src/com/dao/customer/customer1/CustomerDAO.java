package com.dao.customer.customer1;

/**
 * @author 郭昊晨
 * @version 1.0
 */

import com.preparedstatement.Table.Select.Customer;

import java.sql.Connection;
import java.util.List;

/**
 *此接口用于规范针对于customer表的常用操作
 * */
public interface CustomerDAO {
    //将对象添加到customer表中
    void insert(Connection con, Customer cust);
    //根据id删除数据
    void delete(Connection con,int id);
    //更新指定id的的数据
    void update(Connection co,Customer cust);
    //根据指定id查询对应的customer对象
    Customer SelectCustomer(Connection con,int id);
    //查询表中所有记录
    List<Customer> getAll(Connection con);
    //返回表中的数据条目数
    Long getCount(Connection con);
}
