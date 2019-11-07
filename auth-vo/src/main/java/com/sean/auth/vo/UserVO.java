package com.sean.auth.vo;

import lombok.Data;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/3 16:00
 */
@Data
public class UserVO extends PageVO {

    private static final long serialVersionUID = -8533172089000189291L;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    private Byte status;

    /**
     * 是否删除  -1：已删除  0：正常
     */
    private Byte delFlag;

}
