package com.ykx.seckill.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ykx.seckill.pojo.User;
import com.ykx.seckill.service.IUserService;
import com.ykx.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2023/5/6.
 *
 * @author KaiXuan Yang
 */
public class UserUtil {



    public static void main(String[] args) throws Exception {
        new UserUtil().createUser(500);
    }


    public void createUser(int count) throws Exception {
        List<User> users = new ArrayList<>(count);
        for (int i = 0; i < count; ++i) {
            User user = new User();
            user.setId(18852907990l + i);
            user.setNickname("user" + i);
            user.setPasword("a0d384a3d1c81b0c840d783b7feded41");
            user.setSlat("1a2b3c4d");
            user.setLoginCount(0);
            users.add(user);
        }

        System.out.println("创建user");
//        Connection connection = getConnect();
//        System.out.println("创建数据库连接");
//        String sql = "update  t_user set pasword = ? where id = ? ";
//        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        for(int i = 0; i < count; ++i) {
//            User user = users.get(i);
//            preparedStatement.setString(1, user.getPasword());
//            preparedStatement.setLong(2, user.getId());
//            preparedStatement.addBatch();
//        }
//        preparedStatement.executeBatch();
//        preparedStatement.close();
//        System.out.println("插入成功");
//        connection.close();
//        System.out.println("放入数据库");

        String urlString = "http://localhost:8080/login/doLogin";
        File file = new File("/Users/yangkaixuan/Desktop/未命名文件夹/config.txt");
        if (file.exists()) {
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        file.createNewFile();
        raf.seek(0);
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            URL url = new URL(urlString);
            HttpURLConnection co = (HttpURLConnection) url.openConnection();
            co.setRequestMethod("POST");
            co.setDoOutput(true);
            OutputStream out = co.getOutputStream();
            String params = "mobile=" + user.getId() + "&password=" + MD5Util.inputPassToFormPass("123456");
            out.write(params.getBytes());
            out.flush();
            InputStream inputStream = co.getInputStream();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte buff[] = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buff)) >= 0) {
                bout.write(buff, 0, len);
            }
            inputStream.close();
            bout.close();
            String response = new String(bout.toByteArray());
            ObjectMapper mapper = new ObjectMapper();
            RespBean respBean = mapper.readValue(response, RespBean.class);
            String userTicket = ((String) respBean.getObj());
            System.out.println("create userTicket : " + user.getId());

            String row = user.getId() + "," + userTicket;
            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            System.out.println("write to file : " + user.getId());
        }
        raf.close();

        System.out.println("over");

    }

    public static Connection getConnect() throws Exception {
        String url = "jdbc:mysql://192.168.200.161:3306/seckill?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "root";
        String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

}
