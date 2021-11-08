package com.dao.customer.customer1;

/**
 * @author 郭昊晨
 * @version 1.0
 */

import com.preparedstatement.JDBCutils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.preparedstatement.JDBCutils.getConnection;
/**
 * DAO:date (base) access object
 * */

/**
 * 针对于数据表的通用操作
 */
public abstract class BaseDAO{

    //通用增删改
    public  void updateall(Connection con,String sql, Object... args) { //sql语句中占位符的个数相当于参数的个数
        //获取数据库连接
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
            JDBCutils.CloseResource(null, ps);
        }
    }

    //通用查询返回一条记录
    public <T> T AnyTableSelect(Connection con,Class<T> clazz, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = con.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            resultSet = ps.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData data = resultSet.getMetaData();
            //通过ResultSetMetaData获取结果集中的列数
            int columnCount = data.getColumnCount();
            if (resultSet.next()) {
                T t = clazz.newInstance();
                for (int j = 0; j < columnCount; j++) {
                    Object columnValue = resultSet.getObject(j + 1);//拿到数据的值
                    //获取每个列的列名
                    String sqlcolumnLabel = data.getColumnLabel(j + 1);
                    //
                    Field field = clazz.getDeclaredField(sqlcolumnLabel);
                    field.setAccessible(true); //属性可能是私有的
                    field.set(t, columnValue);
                }
                return t;
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCutils.CloseResource(null, ps, resultSet);

        }
        return null;
    }

    //通用查询返回多条数据  (考虑上事务)
    public <T> List<T> getListSelect(Connection con,Class<T> clazz,String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            ps = con.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            resultSet = ps.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData data = resultSet.getMetaData();
            //通过ResultSetMetaData获取结果集中的列数
            int columnCount = data.getColumnCount();
            //创建集合对象
            ArrayList<T> list = new ArrayList<>();

            while (resultSet.next()) {
                T t = clazz.newInstance();
                for (int j = 0; j < columnCount; j++) {
                    Object columnValue = resultSet.getObject(j + 1);//拿到数据的值
                    //获取每个列的列名
                    String sqlcolumnLabel = data.getColumnLabel(j + 1);
                    //
                    Field field = clazz.getDeclaredField(sqlcolumnLabel);
                    field.setAccessible(true); //属性可能是私有的
                    field.set(t, columnValue);
                }
                //把查到的数据装入集合中
                list.add(t);
            }
            return list;

        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCutils.CloseResource(null, ps, resultSet); //不关连接

        }
        return null;
    }
    //用于查询特殊值的通用方法
    public <E> E getValue(Connection con,String sql,Object...args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            for (int i=0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }
             rs = ps.executeQuery();
            if (rs.next()) {
                 return (E) rs.getObject(1);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCutils.CloseResource(null,ps,rs);
        }

        return null;
    }

}
