package com.ghc.cloud.utils;

import com.alibaba.fastjson.JSONObject;
import com.ghc.cloud.entity.FileSystemUploadDTO;
import com.ghc.cloud.entity.FileSystemVerifyDTO;
import com.ghc.cloud.entity.GetFileByIdDTO;
import com.ghc.cloud.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2022/12/28 /18:56
 * @Author guohc
 * @Description 调用system登录接口获取token，filesystem验证、上传文件接口
 */
@Slf4j
public class MyFileUtils {
    public static final String urlLogin = "";

    public static final String urlVerify = "";

    public static final String urlUpload = "";

    //一次请求用一个token
    public static String token = null;

    static RestTemplate restTemplate = new RestTemplate();

    /**
     * 登录获取token
     */
    public static Result login() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "admin");
        map.put("password", "admin");

        Result result = new Result();
        do {
            result.setResult(Boolean.TRUE);
            result.setMessage("登录成功");
            try {
                ResponseEntity<Map> mapResponseEntity = restTemplate.postForEntity(urlLogin, map, Map.class);
                if (mapResponseEntity.getStatusCode() != HttpStatus.OK) {
                    result.setResult(Boolean.FALSE);
                    result.setMessage(mapResponseEntity.getStatusCode().getReasonPhrase());
                    break;
                }
                Map body = mapResponseEntity.getBody();
                Map tokenMap = (Map) body.get("data");
                String tokenLogin = (String) tokenMap.get("token");

                token = tokenLogin;
                if (null == tokenLogin) {
                    result.setResult(Boolean.FALSE);
                    result.setMessage(mapResponseEntity.getStatusCode().getReasonPhrase());
                    break;
                }
            } catch (Exception e) {
                result.setResult(Boolean.FALSE);
                result.setMessage(e.getMessage());
                log.error("登录失败：{}",e.getMessage());
            }

        } while (false);

        return result;
    }

    /**
     * 验证文件是否存在
     */
    public static Result verify(FileSystemVerifyDTO.In.WrapList in){

        Result result = new Result();

        do {
            HttpHeaders headers = new HttpHeaders();
            headers.add("token",token);
            headers.add("content-type","application/json");

            HttpEntity<String> requestHttpEntity = new HttpEntity<>(JSONObject.toJSONString(in),headers);

            result.setResult(Boolean.TRUE);
            result.setMessage("验证成功");
            try {

                ResponseEntity<Map> mapResponseEntity = restTemplate.exchange(urlVerify, HttpMethod.POST, requestHttpEntity, Map.class);
                Map body = mapResponseEntity.getBody();
                Map bodyMap = (Map) body.get("data");
                List<FileSystemVerifyDTO.Out> list = (List<FileSystemVerifyDTO.Out>) bodyMap.get("files");
                Map map= (Map) list.get(0);
                Boolean isExist = (Boolean) map.get("exist");

                if (mapResponseEntity.getStatusCode() != HttpStatus.OK){
                    result.setResult(Boolean.FALSE);
                    result.setMessage(mapResponseEntity.getStatusCode().getReasonPhrase());
                    break;
                }
                //文件存在就设为false 不上传
                if (isExist){
                    result.setResult(Boolean.FALSE);
                    result.setMessage("文件已存在");
                    break;
                }

            } catch (Exception e) {
                result.setResult(Boolean.FALSE);
                result.setMessage(e.getMessage());
                log.error("验证失败，{}",e.getMessage());
            }

        }while (false);

        return result;
    }

    public static Result upload(File file, FileSystemUploadDTO.In in){
        Result result = new Result();

        do {
            result.setResult(Boolean.FALSE);
            result.setMessage("上传成功");
            try {
                //封装请求头
                HttpHeaders headers = new HttpHeaders();
                headers.add("token",token);
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                //封装请求体
                LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
                FileSystemResource resource = new FileSystemResource(file);
                map.put("file", Arrays.asList(resource));
                map.put("params",Arrays.asList("["+JSONObject.toJSONString(in)+"]"));

                HttpEntity<MultiValueMap<String,Object>> requestHttpEntity = new HttpEntity<>(map,headers);
                ResponseEntity<Map> responseEntity = restTemplate.exchange(urlUpload, HttpMethod.POST, requestHttpEntity, Map.class);
                Map body = responseEntity.getBody();
                Map bodyMap = (Map) body.get("data");
                List<Object> files = (List<Object>) bodyMap.get("files");

                for (Object item: files){
                    GetFileByIdDTO.Out fileInfo = JSONObject.parseObject(JSONObject.toJSONString(item), GetFileByIdDTO.Out.class);
                    result.setContent(JSONObject.toJSONString(fileInfo));
                }

                if (responseEntity.getStatusCode() != HttpStatus.OK){
                    result.setResult(Boolean.FALSE);
                    result.setMessage(responseEntity.getStatusCode().getReasonPhrase());
                    break;
                }

            } catch (Exception e) {
                result.setResult(Boolean.FALSE);
                result.setMessage(e.getMessage());
                log.error("上传失败，{}",e.getMessage());
                e.printStackTrace();
            }
        }while (false);
        return result;
    }

}
