package com.sean.auth.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/3 15:53
 */
@Data
@Entity
@Table(name = "SYS_LOG")
public class SysLog extends BaseModel {

    private static final long serialVersionUID = -2732231028654429407L;

    /**
     * 用户名
     */
    @Column(name = "user_name", length = 50, nullable = false)
    private String userName;

    /**
     * 用户操作
     */
    @Column(name = "operation", length = 50, nullable = false)
    private String operation;

    /**
     * 请求方法
     */
    @Column(name = "method", length = 50, nullable = false)
    private String method;

    /**
     * 请求参数
     */
    @Column(name = "params", length = 2000)
    private String params;

    /**
     * 执行时长(毫秒)
     */
    @Column(name = "time", nullable = false)
    private Long time;

    /**
     * 操作IP地址
     */
    @Column(name = "ip", nullable = false)
    private String ip;

}
