package com.ghc.springboot;

/**
 * @author 郭昊晨
 * @version 1.0
 * 2022/11/17 - 22:44
 */

import com.ghc.springboot.entity.Result;

import java.util.Objects;

/**
 * 消息推送接口
 */
public interface Notice {

    /**
     * 获取access_token
     */
    Result getAccessToken();

    /**
     * 发送消息
     */
    Result sendWeComMsg(Object noticeIn);
}
