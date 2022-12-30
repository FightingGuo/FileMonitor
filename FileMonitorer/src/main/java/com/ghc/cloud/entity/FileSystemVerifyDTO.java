package com.ghc.cloud.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Date 2022/12/29 /8:44
 * @Author guohc
 * @Description 验证文件资源传输对象
 */
public class FileSystemVerifyDTO {
    /**
     * 验证文件资源传入参数
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class In{
        /**
         * 文件名
         */
        private String name;

        /**
         * 文件md5
         */
        private String md5;

        /**
         * 是否提取
         */
        private Boolean extract;

        /**
         * 验证文件资源传入参数包装列表类
         */
        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class WrapList{
            private List<In> params;
        }
    }

    /**
     * 上传文件系统传出参数
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Out{
        /**
         * 文件是否存在
         */
        private Boolean exist;

        /**
         * 文件信息
         */
        private GetFileByIdDTO.Out fileInfo;

        /**
         * 上传文件参数包装列表
         */
        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class WrapList{
            private List<Out> params;
        }
    }
}
