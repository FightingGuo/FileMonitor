package com.ghc.cloud.entity;

import lombok.Data;

/**
 * @Date 2022/12/28 /18:57
 * @Author guohc
 * @Description  返回实体
 */
@Data
public class Result {
    private Boolean result;

    private Object content;

    private String message;
}
