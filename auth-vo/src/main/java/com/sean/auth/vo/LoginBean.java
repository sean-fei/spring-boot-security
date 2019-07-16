package com.sean.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/3 15:57
 */
@Data
@ApiModel(value = "登录对象", description = "登录对象")
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 6058363051829061072L;

    @ApiModelProperty(value = "账户", name = "account", example = "admin", required = true)
    private String account;

    @ApiModelProperty(value = "密码", name = "password", example = "admin", required = true)
    private String password;

    @ApiModelProperty(value = "验证码", name = "captcha", example = "captcha", required = true)
    private String captcha;

}
