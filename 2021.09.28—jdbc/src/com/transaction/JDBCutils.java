package com.transaction;


import com.preparedstatement.Table.Select.Customer;
import com.preparedstatement.Table.Select.ExamStudent;
import com.preparedstatement.Table.Select.Order;
import com.preparedstatement.Table.Select.User;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author 郭昊晨
 * @version 1.0
 */
@SuppressWarnings("all")
public class JDBCutils {
    /**
     * 因为每次连接都要 获取Connection对象，和关闭资源操作。所以把相同的操作封装到一个类中
     * --------------以下封装的仅是对test库操作---------------
     * <p>
     * <p>
     * 发现：把增删改方法分别封装了以后发现关闭资源时只用关闭创建出来的Connection就行，不用关闭PrepareStatement
     * 因为PrepareStatement是根据Connection创建的
     */


    public static Connection getConnection() {
        Connection con = null;
        try {
            InputStream rs = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
            Properties properties = new Properties();
            properties.load(rs);
            //读取配置文件中的4个信息
            String user = properties.getProperty("user");
            String url = properties.getProperty("url");
            String password = properties.getProperty("password");
            String driver = properties.getProperty("driver");

            //加载类
            Class.forName(driver);

            //获取连接
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;

    }

    public static void delete(Connection con, int id) {
        PreparedStatement ps = null;
        String sql = "delete from customers where id=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static void insert(Connection con, int id, String s, String e, String d) {
        PreparedStatement ps = null;
        String sql = "insert into customers(id,name,email,birth)values(?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, s);
            ps.setString(3, e);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse(d);//解析具体的日期
            ps.setDate(4, new Date(date.getTime()));

            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }

    }

    public static void update(Connection con, String s, int index) {

        PreparedStatement ps = null;
        String sql = "update customers set name=? where id=?";
        //把sql语句传入preparestatement
        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, s);
            ps.setInt(2, index);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static void CloseResource(Connection con, PreparedStatement ps) {

        try {
            if (ps != null)
                ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (con != null)
                con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void CloseResource(Connection con, PreparedStatement ps, ResultSet rs) {

        try {
            if (rs != null)
                rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (con != null)
                con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static void CloseResource(Connection con) {
        try {
            if (con != null)
                con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //终极无敌三合一(增，删，改)
    public static void updateall(String sql, Object... args) { //sql语句中占位符的个数相当于参数的个数
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

    //--------针对与Customer表的通用的查询操作-------
    public static Customer customerSelect(String sql, Object... args){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            con = JDBCutils.getConnection();
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
                Customer customer = new Customer();
                for (int j = 0; j < columnCount; j++) {
                    Object columnValue = resultSet.getObject(j + 1);//拿到字段
                    //获取每个列的列名
                    String sqlcolumnName = data.getColumnName(j + 1);
                    //给customers对象指定的columnName属性，赋值为columnValue：通过反射
                    Field field = Customer.class.getDeclaredField(sqlcolumnName);
                    field.setAccessible(true); //属性可能是私有的
                    field.set(customer, columnValue);
                }
                return customer;
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCutils.CloseResource(con,ps,resultSet);

        }
        return null;

    }


    //++++++++针对与User表的通用查询操作+++++++++
    public static User userSelect(String sql,Object...args){
        //获取连接对象
        Connection con2 = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con2 = JDBCutils.getConnection();
            ps = con2.prepareStatement(sql);

            //填充占位符   需要查询的字段
            for (int i=0;i< args.length;i++){
                ps.setObject(i+1,args[i]);
            }

            //获取结果集
            rs = ps.executeQuery();

            //把一条结果集的多个字段分别存入User对象中
            ResultSetMetaData md = rs.getMetaData();//获取元数据
            int columnCount = md.getColumnCount(); //获取数据的列数
            if (rs.next()){
                User user = new User();
                for (int j=0;j<columnCount;j++){
                    //拿到每一个从数据库传入进来的具体字段的数据
                    Object columnvalue = rs.getObject(j+1);
                    //根据传入进来的字段拿到表中的字段名
                    String columnName = md.getColumnName(j+1);

                    //在user中找到和columnName字段名相同的属性，赋值为columnvalue
                    Field field = User.class.getDeclaredField(columnName);
                    field.setAccessible(true); //把属性设为可访问
                    field.set(user,columnvalue);
                }
                return user;
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            //关闭资源
            JDBCutils.CloseResource(con2,ps,rs);
        }

        return null; //没找到返回空
    }

    //通用查询每个表的数据
    public static <T> T AnyTableSelect(Class<T> clazz,String sql,Object...args){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            con = JDBCutils.getConnection();
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
                    Field field = clazz.getClass().getDeclaredField(sqlcolumnLabel);
                    field.setAccessible(true); //属性可能是私有的
                    field.set(t, columnValue);
                }
                return  t;
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCutils.CloseResource(con,ps,resultSet);

        }
        return null;
    }

    //拿不到反射的类
    public static Order orderSelect(String sql,Object...args){
        //获取连接对象
        Connection con3 = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con3 = JDBCutils.getConnection();
            ps = con3.prepareStatement(sql);

            //填充占位符
            for (int i=0;i< args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            //获取结果集
            rs = ps.executeQuery(); //结果集存储数据

            //把一条结果集的多个字段分别存入order对象中
            ResultSetMetaData md = rs.getMetaData();//获取元数据 元数据不存数据  修饰结果集的叫做元数据 相当于字段名
            int columnCount = md.getColumnCount(); //获取数据的列数
            if (rs.next()){
                Order order=new Order();
                for (int j=0;j<columnCount;j++){
                    //拿到每一个从数据库传入进来的具体字段的数据放入columnCount
                    Object columnvalue = rs.getObject(j+1);
                    //根据传入进来的字段拿到表中的字段名
                    //当数据库表名与类对象的成员变量名不一致时，这里要获取的时表的别名
//                    String columnName = md.getColumnName(); -----不推荐使用
                    String columnLabel = md.getColumnLabel(j+1);//获取字段名

                    //在order中找到和columnName字段名相同的属性，赋值为columnvalue
                    Field field = Order.class.getDeclaredField(columnLabel);
                    field.setAccessible(true); //把属性设为可访问
                    field.set(order,columnvalue);
                }
                return order;
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            //关闭资源
            JDBCutils.CloseResource(con3,ps,rs);
        }

        return null; //没找到返回空
    }

    //查询多行数据  --------拿不到反射的类
    public static <T> List<T> getListSelect(Class<T> clazz,String sql,Object...args){

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            con = JDBCutils.getConnection();
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
                    Field field = clazz.getClass().getDeclaredField(sqlcolumnLabel);
                    field.setAccessible(true); //属性可能是私有的
                    field.set(t, columnValue);
                }
                //把查到的数据装入集合中
                list.add(t);

            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCutils.CloseResource(con,ps,resultSet);

        }
        return null;
    }



    //++++++++对examStudent表的通用查询+++++++++
    public static ExamStudent examStudentSelect(String sql,Object...args){
        //获取连接对象
        Connection con2 = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con2 = JDBCutils.getConnection();
            ps = con2.prepareStatement(sql);

            //填充占位符   需要查询的字段
            for (int i=0;i< args.length;i++){
                ps.setObject(i+1,args[i]);
            }

            //获取结果集
            rs = ps.executeQuery();

            //把一条结果集的多个字段分别存入examStudent对象中
            ResultSetMetaData md = rs.getMetaData();//获取元数据
            int columnCount = md.getColumnCount(); //获取数据的列数
            if (rs.next()){
                ExamStudent examStudent = new ExamStudent();
                for (int j=0;j<columnCount;j++){
                    //拿到每一个从数据库传入进来的具体字段的数据
                    Object columnvalue = rs.getObject(j+1);
                    //根据传入进来的字段拿到表中的字段名
                    String columnName = md.getColumnName(j+1);

                    //在examStudent中找到和columnName字段名相同的属性，赋值为columnvalue
                    Field field = ExamStudent.class.getDeclaredField(columnName);
                    field.setAccessible(true); //把属性设为可访问
                    field.set(examStudent,columnvalue);
                }
                return examStudent;
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            //关闭资源
            JDBCutils.CloseResource(con2,ps,rs);
        }

        return null; //没找到返回空
    }



}
