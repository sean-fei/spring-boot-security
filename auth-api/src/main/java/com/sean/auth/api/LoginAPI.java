package com.sean.auth.api;

import com.sean.auth.http.HttpResult;
import com.sean.auth.vo.LoginBean;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/4 9:46
 */
public interface LoginAPI {

    /**
     * 用户登录
     * @param loginBean
     * @param request
     * @return
     * @throws IOException
     */
    HttpResult login(@RequestBody LoginBean loginBean, HttpServletRequest request) throws IOException;

    /**
     * 获取验证码
     * @param response
     * @param request
     * @throws IOException
     */
    void captcha(HttpServletResponse response, HttpServletRequest request) throws IOException;

    /**
     * 测试接口访问
     * @return
     */
    String index();

    /**
     * 用户登出
     * @param response
     * @param request
     */
    void logout(HttpServletResponse response, HttpServletRequest request);

}
