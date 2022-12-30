package com.ghc.cloud.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Date 2022/12/29 /8:48
 * @Author guohc
 * @Description  根据文件id获取文件信息传输对象
 */
@Data
public class GetFileByIdDTO {
    /**
     * 验证文件资源传入参数
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class In {
        /**
         * 文件id
         */
        private Integer id;

        /**
         * 验证文件资源传入参数包装列表类
         */
        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class WrapList {
            private List<Integer> ids;
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Out{
        /**
         * 文件id
         */
        private Integer id;

        /**
         * 文件名
         */
        private String name;

        /**
         * 文件md5
         */
        private String md5;
    }
}
