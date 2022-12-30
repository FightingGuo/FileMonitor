package com.ghc.cloud.listener;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

/**
 * @Date 2022/12/28 /19:05
 * @Author guohc
 * @Description
 */
public class FileMonitor {
    /**
     * 观察者对象
     */
    private FileAlterationMonitor monitor;

    public FileMonitor(long interval){
        monitor = new FileAlterationMonitor(interval);
    }

    public void monitor(String path,FileAlterationListener listener){
        FileAlterationObserver observer = new FileAlterationObserver(new File(path));
        monitor.addObserver(observer);
        //观察者加入监听器
        observer.addListener(listener);
    }

    /**
     * 开启监听
     */
    public void start(){
        try {
            monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止监听
     */
    public void stop(){
        try {
            monitor.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
