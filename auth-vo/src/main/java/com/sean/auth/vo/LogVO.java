package com.sean.auth.vo;

import lombok.Data;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/3 15:58
 */
@Data
public class LogVO extends BaseVO {

    private static final long serialVersionUID = -3496474810130895100L;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户操作
     */
    private String operation;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 执行时长(毫秒)
     */
    private Long time;

    /**
     * 操作IP地址
     */
    private String ip;

}
