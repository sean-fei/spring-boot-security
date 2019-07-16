package com.sean.auth.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.sean.auth.annotation.Operation;
import com.sean.auth.api.LoginAPI;
import com.sean.auth.http.HttpResult;
import com.sean.auth.service.UserService;
import com.sean.auth.utlis.pwd.PasswordUtils;
import com.sean.auth.vo.LoginBean;
import com.sean.auth.vo.UserVO;
import com.sean.auth.security.SecurityUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/4 9:48
 */
@Controller
@Api(description = "登录接口")
public class LoginController implements LoginAPI {

    @Autowired
    UserService userService;

    @Resource
    DefaultKaptcha captchaProducer;

    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * 登录验证码SessionKey
     */
    public static final String LOGIN_VALIDATE_CODE = "login_validate_code";

    /**
     * 获取验证码
     * @param response
     * @param request
     * @throws IOException
     */
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @GetMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response, HttpServletRequest request) throws IOException {
        // Set to expire far in the past.
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        // create the text for the image
        String capText = captchaProducer.createText();
        // store the text in the session
        request.getSession().setAttribute(LOGIN_VALIDATE_CODE, capText);
        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    @Override
    @GetMapping("/index")
    @ResponseBody
    public String index() {
        long id = 1;
        userService.findById(id);
        return "当前用户：" + SecurityUtils.getUsername();
    }

    @Override
    @PostMapping("/logout")
    public void logout(HttpServletResponse response, HttpServletRequest request) {
        SecurityUtils.logout(request, SecurityUtils.getUsername());
    }

    /**
     * 登录
     * @param loginBean
     * @param request
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "loginBean",  value = "用户标识", required = true, paramType = "query", dataType = "com.sean.auth.vo.LoginBean")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @Override
    @PostMapping(value = "/login")
    @ResponseBody
    public HttpResult login(LoginBean loginBean, HttpServletRequest request) throws IOException {
        String username = loginBean.getAccount();
        String password = loginBean.getPassword();
        String captcha = loginBean.getCaptcha();
//        // 从session中获取之前保存的验证码跟前台传来的验证码进行匹配
        String kaptcha = (String) request.getSession().getAttribute(LOGIN_VALIDATE_CODE);
        if(kaptcha == null){
            return HttpResult.error("验证码已失效");
        }
        if (!kaptcha.equalsIgnoreCase(captcha)) {
            return HttpResult.error("验证码输入错误！");
        }
        request.getSession().setAttribute(LOGIN_VALIDATE_CODE, "");
        // 用户信息
        UserVO user = userService.findByName(username);

        // 账号不存在、密码错误
        if (user == null) {
            return HttpResult.error("账号不存在");
        }

        if (!PasswordUtils.matches(user.getSalt(), password, user.getPassword())) {
            return HttpResult.error("密码不正确");
        }

        // 账号锁定
        if (user.getStatus() == 0) {
            return HttpResult.error("账号已被锁定,请联系管理员");
        }
//        return HttpResult.ok();
//      // JWT 系统登录认证
        return HttpResult.ok(SecurityUtils.login(request, username, password, authenticationManager));
        // Shiro 系统登录认证
//        return HttpResult.ok(tokenService.createToken(user.getId()));
    }

}
