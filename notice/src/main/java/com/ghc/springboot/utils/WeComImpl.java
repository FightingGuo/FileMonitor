package com.ghc.springboot.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ghc.springboot.Notice;
import com.ghc.springboot.entity.GetAccessToken;
import com.ghc.springboot.entity.Result;
import com.ghc.springboot.entity.SendMsgDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author 郭昊晨
 * @version 1.0
 * 2022/11/17 - 22:48
 */
public class WeComImpl implements Notice {


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Resource
    public RestTemplate restTemplate;

    /**
     * 发送消息的类型
     */
    @Value("${notice.dingTalk.qywx.msgType:}")
    private String msgType;

    /**
     * 获取企业微信的企业号
     */
    @Value("${notice.dingTalk.qywx.corpId:}")
    private static final String corpId = "xxx";

    /**
     * 获取企业应用的密钥
     */
    @Value("${notice.dingTalk.qywx.corpSecret:}")
    private static final String corpSecret="xxx" ;
    /**
     * 获取访问权限码URL
     */
    private static final  String ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";


    /**
     * 企业id
     */
    @Value("${notice.dingTalk.qywx.agentId:}")
    private String agentId;

    /**
     * 创建会话请求URL
     */
    private final static String CREATE_SESSION_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";


    /**
     * 获取access_token
     * @return
     */
    @Override
    public Result getAccessToken(){
        //访问微信服务器
        String url = ACCESS_TOKEN_URL + "?corpid=" + corpId + "&corpsecret=" + corpSecret;

        Result result=new Result();
        try {
            GetAccessToken.In token=new GetAccessToken.In();
            token = restTemplate.getForObject(url,GetAccessToken.In.class);
            result.setContent(token.getAccessToken());
        }catch (Exception e){
            result.setErrMsg(e.getMessage());
        }
        //返回access_token码
        return result;
    }

    @Override
    public Result sendWeComMsg(Object noticeIn) {
        SendMsgDTO.In in = JSONObject.parseObject(String.valueOf(noticeIn), SendMsgDTO.In.class);

        //从对象中提取凭证
        String ACCESS_TOKEN = (String) getAccessToken().getContent();

        //把拿到的授权token拼接到请求串
        String url = CREATE_SESSION_URL + ACCESS_TOKEN;

        HashMap<String,Object> map=new HashMap<>();
        HashMap<String,String> contentMap=new HashMap<>();

//        try {
        map.put("touser",in.getToUser());
        map.put("toparty",in.getToParty());
        map.put("totag",in.getToTag());
        map.put("msgtype",msgType);
        map.put("text",contentMap);
        contentMap.put("content",in.getContent());
        map.put("safe",in.getSafe());
        map.put("agentid",agentId);


//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type","application/json");
//        HttpEntity<String> stringHttpEntity = new HttpEntity<>(JSON.toJSONString(map), headers);
//        ResponseEntity<Result> resultEntity = restTemplate.exchange(url, HttpMethod.POST, stringHttpEntity, Result.class);

        Result result=new Result();

        try {
            ResponseEntity<SendMsgDTO.Out> resultEntity = restTemplate.postForEntity(url, map,SendMsgDTO.Out.class);
            SendMsgDTO.Out body = resultEntity.getBody();
            if (null!=body.getErrCode()){
                //错误信息放入
                result.setContent(body.getErrMsg());
                result.setResult(Boolean.FALSE);
            }
            //如果没错，就返回true
            result.setResult(Boolean.TRUE);
        }catch (Exception e){
            result.setResult(Boolean.FALSE);
            result.setErrMsg(e.getMessage());
        }
     return result;
    }
}
