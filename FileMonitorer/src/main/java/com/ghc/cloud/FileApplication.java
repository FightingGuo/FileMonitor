package com.ghc.cloud;

import com.ghc.cloud.constant.FileConstant;
import com.ghc.cloud.utils.Monitor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Date 2022/12/28 /19:22
 * @Author guohc
 * @Description
 */
@SpringBootApplication
public class FileApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
        //创建观察者
        Monitor monitor = new Monitor();
        //开启监听
        monitor.start(FileConstant.PATH);
    }
}
