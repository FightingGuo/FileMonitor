package com.ghc.cloud.utils;

import com.ghc.cloud.listener.FileListener;
import com.ghc.cloud.listener.FileMonitor;

/**
 * @Date 2022/12/28 /19:02
 * @Author guohc
 * @Description 观察者对象,监听入口
 */
public class Monitor {
    /**
     * 监听周期 每1s触发一次
     */
    public static final long interval = 1000;

    /**
     * 开启文件监听
     */
    public void start(String path){
        FileMonitor fileMonitor = new FileMonitor(interval);
        fileMonitor.monitor(path,new FileListener());
        fileMonitor.start();
    }
}
