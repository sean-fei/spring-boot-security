package com.sean.auth.security.jwt;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/11/6 14:09
 */
public class CustomAccessDeineHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/javascript;charset=utf-8");
//        response.getWriter().print(JSONObject.toJSONString(RestMsg.error("没有访问权限!")));
        PrintWriter printWriter = response.getWriter();
        String body = "没有权限！";
        printWriter.write(body);
        printWriter.flush();
    }

}
