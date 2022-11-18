package com.ghc.springboot.entity;

/**
 * @author 郭昊晨
 * @version 1.0
 * 2022/11/17 - 22:18
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发送消息实体
 */
public class SendMsgDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class In{
        /**
         * 指定接收消息的成员，成员ID列表（多个接收者用‘|’分隔，最多支持1000个）。
         * 特殊情况：指定为"@all"，则向该企业应用的全部成员发送
         */
        private String toUser;

        /**
         * 指定接收消息的部门，部门ID列表，多个接收者用‘|’分隔，最多支持100个。
         * 当touser为"@all"时忽略本参数
         */
        private String toParty;

        /**
         * 指定接收消息的标签，标签ID列表，多个接收者用‘|’分隔，最多支持100个。
         * 当touser为"@all"时忽略本参数
         */
        private String toTag;

        /**
         * 消息类型
         */
        private String msgType;
        /**
         * 应用id
         */
//        private String agentid;

        /**
         * 消息内容，最长不超过2048个字节，超过将截断（支持id转译）
         */
        private String content;

        /**
         * 表示是否是保密消息，0表示可对外分享，1表示不能分享且内容显示水印，默认为0
         */
        private String safe;

        /**
         * 	表示是否开启id转译，0表示否，1表示是，默认0。仅第三方应用需要用到，企业自建应用可以忽略。
         */
        private String enableIdTrans;

        /**
         * 表示是否开启重复消息检查，0表示否，1表示是，默认0
         */
        private String enableDuplicateCheck;

        /**
         * 表示是否重复消息检查的时间间隔，默认1800s，最大不超过4小时
         */
        private String duplicateCheckInterval;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Out{
        /**
         * 返回码
         */
        private String errCode;

        /**
         * 对返回码的文本描述内容
         */
        private String errMsg;

        /**
         * 不合法的userid，不区分大小写，统一转为小写
         */
        private String invalidUser;

        /**
         * 不合法的partyid
         */
        private String invalidParty;

        /**
         * 不合法的标签id
         */
        private String invalidTag;

        /**
         * 没有基础接口许可(包含已过期)的userid
         */
        private String unLicensedUser;

        /**
         * 消息id，用于撤回应用消息
         */
        private String msgId;

        /**
         * 仅消息类型为“按钮交互型”，“投票选择型”和“多项选择型”的模板卡片消息返回，应用可使用response_code调用更新模版卡片消息接口，72小时内有效，且只能使用一次
         */
        private String responseCode;
    }
}
