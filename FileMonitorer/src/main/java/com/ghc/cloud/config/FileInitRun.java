package com.ghc.cloud.config;

import com.ghc.cloud.listener.FileListener;
import com.ghc.cloud.service.impl.FileServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;

/**
 * @Date 2022/12/28 /16:59
 * @Author guohc
 * @Description  解决监听器无法注入spring容器里的对象
 *              CommandLineRunner的run方法：再springboot启动后会执行的 order执行权重 数越小越优先执行
 *              在监听器中把需要注入的义务类写成监听器的一个属性提供get set方法 在FileInitRun中注入义务类，通过set方法赋值监听器中的义务类对象即可使用
 */
@Configuration
@Order(1)
public class FileInitRun implements CommandLineRunner {

    @Resource
    private FileServiceImpl fileService;

    @Override
    public void run(String... args) throws Exception {
        FileListener.setFileService(fileService);
    }
}
