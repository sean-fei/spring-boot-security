package com.sean.auth.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/3 15:54
 */
@Data
@Entity
@Table(name = "SYS_USER")
public class SysUser extends BaseModel {

    private static final long serialVersionUID = -8365684345273200603L;

    /**
     * 用户名
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * 密码
     */
    @Column(name = "password", length = 64, nullable = false)
    private String password;

    /**
     * 盐
     */
    @Column(name = "salt", length = 40, nullable = false)
    private String salt;

    /**
     * 邮箱
     */
    @Column(name = "email", length = 100)
    private String email;

    /**
     * 手机号
     */
    @Column(name = "mobile", length = 20)
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    @Column(name = "status", length = 2, nullable = false)
    private Byte status;

    /**
     * 是否删除  -1：已删除  0：正常
     */
    @Column(name = "delFlag", length = 2, nullable = false)
    private Byte delFlag;

}
