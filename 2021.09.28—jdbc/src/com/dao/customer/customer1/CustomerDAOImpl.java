package com.dao.customer.customer1;

import com.preparedstatement.Table.Select.Customer;
import java.sql.Connection;
import java.util.List;

/**
 * @author 郭昊晨
 * @version 1.0
 */
public class CustomerDAOImpl extends BaseDAO implements CustomerDAO {

    @Override
    public void insert(Connection con, Customer cust) {
        String sql = "insert into customers(name,email,birth)values(?,?,?)";
        updateall(con, sql, cust.getName(),cust.getEmail(),cust.getBirth());

    }

    @Override
    public void delete(Connection con, int id) {
        String sql="delete from customers where id=?";
        updateall(con,sql,id);
    }

    @Override
    public void update(Connection con, Customer cust) {
        String sql="update customers set name=?,email=?,birth=? where id=?";
        updateall(con,sql,cust.getName(),cust.getEmail(),cust.getBirth(),cust.getId());
    }

    @Override
    public Customer SelectCustomer(Connection con, int id) {
        String sql="select id,name,email,birth from customers where id=?";
        Customer cust = AnyTableSelect(con, Customer.class, sql, id);
        return cust;
    }

    @Override
    public List<Customer> getAll(Connection con) {
        String sql="select id,name,email,birth from customers";
        List<Customer> list = getListSelect(con, Customer.class, sql);
        return list;
    }

    @Override
    public Long getCount(Connection con) {
        String sql="select count(*) from customers";
        Long count = getValue(con,sql);
        return count;
    }
}
