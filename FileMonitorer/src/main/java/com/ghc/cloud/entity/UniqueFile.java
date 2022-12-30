package com.ghc.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Date 2022/12/28 /17:40
 * @Author guohc
 * @Description 唯一id表
 */
@Data
@TableName("UNIQUE_FILE")
public class UniqueFile {
    //唯一id
    private String uniqueId;
    //文件上传时间
    private String createTime;
}
