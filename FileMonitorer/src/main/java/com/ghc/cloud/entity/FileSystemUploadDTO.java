package com.ghc.cloud.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Date 2022/12/29 /8:37
 * @Author guohc
 * @Description
 */
@Data
public class FileSystemUploadDTO {
    /**
     * 上传文件系统传入参数
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
         * 上传文件参数列表
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
         * 文件md5
         */
        private String md5;

        /**
         * 上传文件参数包装列表
         */
        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class WrapList{
            private List<GetFileByIdDTO.Out> params;
        }
    }
}
