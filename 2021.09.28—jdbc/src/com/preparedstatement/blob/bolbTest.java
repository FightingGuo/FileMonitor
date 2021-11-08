package com.preparedstatement.blob;

import com.preparedstatement.JDBCutils;
import com.preparedstatement.Table.Select.Customer;
import org.junit.Test;

import java.io.*;
import java.sql.*;

/**
 * @author 郭昊晨
 * @version 1.0
 */
public class bolbTest {
    //向数据表中插入blob类型的字段
    @Test
    public void test1() {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = JDBCutils.getConnection();
            String sql="insert into customers(name,email,birth,photo)values(?,?,?,?)";
            ps = con.prepareStatement(sql);

            ps.setObject(1,"gh");
            ps.setObject(2,"gg@qq.com");
            ps.setObject(3,"2000-8-24");
            FileInputStream fs=new FileInputStream(new File("lib\\jdbc.insert2.jpg"));
            ps.setBlob(4,fs);

            ps.execute();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCutils.CloseResource(con,ps);
        }


    }

    @Test
    public void test2(){
        String sql="delete from customers where id=?";
        JDBCutils.updateall(sql,29);
    }

    //查询customer表中blob字段
    @Test
    public void test3(){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            con = JDBCutils.getConnection();
            String sql="select id,name,email,birth,photo from customers where id=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1,28);
            //获取结果集
            rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Date birth = rs.getDate("birth");
                String email = rs.getString("email");

                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);
            }
            //将blob类型的字段下载下来，以文件的形式保存到本地
            Blob photo = rs.getBlob("photo");
            inputStream = photo.getBinaryStream();
            outputStream = new FileOutputStream("Exception.jpg");//新建文件保存
            byte buffer[]=new byte[1024];
            int length;
            while ((length=inputStream.read())!=-1){
                outputStream.write(buffer,0,length);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (inputStream!=null)
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (outputStream!=null)
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            JDBCutils.CloseResource(con,ps,rs);

        }

    }

}
