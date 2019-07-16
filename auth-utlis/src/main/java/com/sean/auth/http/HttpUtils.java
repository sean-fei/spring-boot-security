package com.sean.auth.http;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * HTTP工具类
 * @author sean (yunfei_li@qq.com)
 * @version 1.0
 * @date 2019/7/3 16:30
 */
public class HttpUtils implements Serializable {

    private static final long serialVersionUID = 1302827815973419650L;

    /**
     * 获取HttpServletRequest对象
     * @return
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 输出信息到浏览器
     * @param response
     * @param msg
     * @throws IOException
     */
    public static void print(HttpServletResponse response, int code, String msg) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        HttpResult result = HttpResult.error(code, msg);
        String json = JSONObject.toJSONString(result);
        response.getWriter().print(json);
        response.getWriter().flush();
        response.getWriter().close();
    }

}
