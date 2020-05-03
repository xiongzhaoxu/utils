//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.oozol.utils;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class HttpUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    public HttpUtils() {
    }

    public static void response(HttpServletResponse servletResponse, String msg, String code, Object data, boolean succeed, int status) {
        try {
            servletResponse.setCharacterEncoding("UTF-8");
            servletResponse.setContentType("application/json");
            servletResponse.setStatus(400);
            RestResult<Object> restResult = new RestResult<>();
            restResult.setMsg(msg);
            restResult.setSucceed(succeed);
            restResult.setCode(code);
            restResult.setData(data);
            servletResponse.getWriter().write(JSONObject.toJSONString(restResult));
        } catch (IOException var7) {
            log.error("writer response stream error ", var7);
            servletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

    }

    public static void response400(HttpServletResponse servletResponse, String msg, String code, Object data) {
        response(servletResponse, msg, code, data, false, 400);
    }

    public static void response500(HttpServletResponse servletResponse, String msg, String code, Object data) {
        response(servletResponse, msg, code, data, false, 500);
    }

    public static void response200(HttpServletResponse servletResponse, String msg, String code, Object data) {
        response(servletResponse, msg, code, data, true, 200);
    }
}
