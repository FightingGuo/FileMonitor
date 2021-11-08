package com.StatementTest;

import com.preparedstatement.Table.Select.ExamStudent;
import org.junit.Test;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author 郭昊晨
 * @version 1.0
 */
public class SelectionTest {
    public Connection Connection() {
        Connection con=null;
        try {
            InputStream rs = SelectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
            Properties ps = new Properties();
            ps.load(rs);

            //读取配置文件
            String user = ps.getProperty("user");
            String password = ps.getProperty("password");
            String url = ps.getProperty("url");
            String driver = ps.getProperty("driver");
            //加载类
            Class.forName(driver);
            //连接
             con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    //从控制台查询表ExamStudent的数据
    public ExamStudent select(String sql,Object...args) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Connection();
            ps = con.prepareStatement(sql);

            //填充操作符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();//获取元数据
            int columnCount = metaData.getColumnCount();//获取数据的列数
            while (rs.next()) {
                ExamStudent examStudent = new ExamStudent();
                for (int i = 0; i < columnCount; i++) {
                    Object value = rs.getObject(i + 1); //拿到数据库中每个字段的值
                    String columnLabel = metaData.getColumnLabel(i + 1);//拿到数据库中的字段名或别名

                    //通过反射相匹配
                    Field field = ExamStudent.class.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(examStudent, value);
                }
                return examStudent;
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }finally {
            CloseResource(con,ps,rs);
        }
        return null;
    }

    public  void CloseResource(Connection con, PreparedStatement ps, ResultSet rs) {

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

    @Test
    public void test(){
        System.out.println("请选择您要输入的类型：");
        System.out.println("a.准考证号：");
        System.out.println("b.身份证号：");
        Scanner scanner = new Scanner(System.in);
        String selection = scanner.next();
        if ("a".equalsIgnoreCase(selection)){
            System.out.print("请输入准考证号：");
            String examcard=scanner.next();
            String sql="select * from examstudent where examcard=?";
            ExamStudent examStudent = select(sql, examcard);
            if (examStudent!=null){
                System.out.println(examStudent);
            }else{
                System.out.println("您输入的准考证号有误！");
            }

        } else if ("b".equalsIgnoreCase(selection)) {
            System.out.print("请输入身份证号：");
            String sql="";
        }


    }
}
