package com.ghc.ssm.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 郭昊晨
 * @version 1.0
 * 2022/5/1 - 17:16
 */
public class Message {
    //状态码 100-成功 200-失败
    private int code;
    //提示信息
    private String msg;
    //用户要返回给浏览器的数据
    private Map<String,Object> map=new HashMap<String,Object>();


    public static Message success(){
        Message message=new Message();
        message.setCode(100);
        message.setMsg("处理成功");
        return message;
    }

    public static Message fail(){
        Message message=new Message();
        message.setCode(200);
        message.setMsg("处理失败");
        return message;
    }

    //链式方法
    public Message add(String key,Object value){
        this.getMap().put(key,value);
        return this;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Message(int code, String msg, Map<String, Object> map) {
        this.code = code;
        this.msg = msg;
        this.map = map;
    }

    public Message() {
    }


}
